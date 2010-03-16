package com.christophdietze.jack.common.pgn;

import java.util.HashMap;
import java.util.Map;

import com.christophdietze.jack.common.board.IllegalPositionException;
import com.christophdietze.jack.common.board.Piece;
import com.christophdietze.jack.common.board.PieceType;
import com.christophdietze.jack.common.board.Position;
import com.christophdietze.jack.common.board.PositionChecker;

/**
 * @see {@link http://en.wikipedia.org/wiki/Forsyth-Edwards_Notation}
 */
public class FenParser {

	private static Map<Piece, Character> squareToFenCharMap = new HashMap<Piece, Character>();

	private static Map<Character, Piece> fenCharToSquareMap = new HashMap<Character, Piece>();

	static {
		initMaps();
	}

	private PositionChecker positionChecker = new PositionChecker();

	private Position position;

	private String fenString;
	private int curIndex;
	private char curChar;

	public FenParser() {
	}

	public void parse(String fenString) throws FenParsingException, IllegalPositionException {
		this.fenString = fenString;
		curIndex = -1;
		nextChar();
		position = new Position();
		parsePosition();
		parseWhitespace();
		parseActiveColor();
		parseWhitespace();
		parseCastlingAvailability();
		parseWhitespace();
		parseEnPassantAvailability();
		parseWhitespace();
		parseHalfmoveClock();
		parseWhitespace();
		parseFullmoveNumber();
		if (hasMoreChars()) {
			throw new FenParsingException(fenString, curIndex, "end of input expected, found '" + curChar + "'");
		}
		positionChecker.checkForSensiblePosition(position);
	}

	public Position getPosition() {
		return position;
	}

	private void parsePosition() throws FenParsingException {
		int fenPosIndex = 0;
		while (curChar != ' ') {
			if (Character.isDigit(curChar)) {
				int skipCount = curChar - '0';
				fenPosIndex += skipCount;
			} else if (curChar == '/') {
				if (fenPosIndex % 8 != 0) {
					throw new FenParsingException(fenString, curIndex, "unexpected '/'");
				}
			} else {
				Piece square = toSquare(curChar);
				if (square == null) {
					throw new FenParsingException(fenString, curIndex, "unknown piece code '" + curChar + "'");
				}
				int boardPosIndex = (7 - fenPosIndex / 8) * 8 + fenPosIndex % 8;
				position.setPiece(boardPosIndex, toSquare(curChar));
				fenPosIndex++;
			}
			nextChar();
		}
	}

	private void parseActiveColor() throws FenParsingException {
		if (curChar == 'w') {
			position.setWhiteToMove(true);
		} else if (curChar == 'b') {
			position.setWhiteToMove(false);
		} else {
			throw new FenParsingException(fenString, curIndex, "expected 'w' or 'b' indicating the active color '");
		}
		nextChar();
	}

	private void parseCastlingAvailability() throws FenParsingException {
		position.setCanWhiteCastleKingside(false);
		position.setCanWhiteCastleQueenside(false);
		position.setCanBlackCastleKingside(false);
		position.setCanBlackCastleQueenside(false);
		if (curChar == '-') {
			nextChar();
			return;
		}
		while (curChar != ' ') {
			if (curChar == 'K') {
				position.setCanWhiteCastleKingside(true);
			} else if (curChar == 'Q') {
				position.setCanWhiteCastleQueenside(true);
			} else if (curChar == 'k') {
				position.setCanBlackCastleKingside(true);
			} else if (curChar == 'q') {
				position.setCanBlackCastleQueenside(true);
			}
			nextChar();
		}
	}

	private void parseEnPassantAvailability() throws FenParsingException {
		if (curChar == '-') {
			nextChar();
			return;
		}
		int file = curChar - 'a';
		nextChar();
		int rank = position.isWhiteToMove() ? 5 : 2;
		char expectedRank = Character.forDigit(rank + 1, 10);
		if (curChar != expectedRank) {
			throw new FenParsingException(fenString, curIndex, "expected rank " + expectedRank);
		}
		int squareIndex = file + 8 * rank;
		position.setEnPassantPawnIndex(squareIndex);
		nextChar();
	}

	private void parseHalfmoveClock() throws FenParsingException {
		int startIndex = curIndex;
		while (Character.isDigit(curChar)) {
			nextChar();
		}
		int halfmoveClock = Integer.parseInt(fenString.substring(startIndex, curIndex));
		position.setHalfmoveClock(halfmoveClock);
	}

	private void parseFullmoveNumber() throws FenParsingException {
		int startIndex = curIndex;
		while (hasMoreChars() && Character.isDigit(curChar)) {
			nextChar();
		}
		int fullmoveNumber = Integer.parseInt(fenString.substring(startIndex, curIndex));
		position.setFullmoveNumber(fullmoveNumber);
	}

	private void parseWhitespace() throws FenParsingException {
		if (curChar != ' ') {
			throw new FenParsingException(fenString, curIndex, "whitespace expected, found '" + curChar + "'");
		}
		nextChar();
	}

	private void nextChar() throws FenParsingException {
		curIndex++;
		if (curIndex > fenString.length()) {
			throw new FenParsingException(fenString, curIndex, "unexpected end of string");
		} else if (curIndex == fenString.length()) {
			curChar = '\0';
		} else {
			curChar = fenString.charAt(curIndex);
		}
	}

	private boolean hasMoreChars() {
		return curIndex < fenString.length();
	}

	private static Piece toSquare(char fenChar) {
		return fenCharToSquareMap.get(fenChar);
	}

	private static void initMaps() {
		for (PieceType piece : PieceType.values()) {
			Piece whiteSquare = Piece.getFromColorAndPiece(true, piece);
			Piece blackSquare = Piece.getFromColorAndPiece(false, piece);
			putMapping(whiteSquare, Character.toUpperCase(piece.getSymbol()));
			putMapping(blackSquare, Character.toLowerCase(piece.getSymbol()));
		}
	}

	private static void putMapping(Piece square, char c) {
		squareToFenCharMap.put(square, c);
		fenCharToSquareMap.put(c, square);
	}
}
