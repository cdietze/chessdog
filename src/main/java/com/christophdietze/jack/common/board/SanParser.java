package com.christophdietze.jack.common.board;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * SAN (Standard Algebraic Notation)
 * 
 * @see {@link http://en.wikipedia.org/wiki/Pgn#Movetext}
 */
public class SanParser {

	private Position position;
	private int curIndex;
	private String sanString;
	private char curChar;
	private Move move;
	private PieceType promotionPiece;
	private int toIndex;
	private int fromFile;
	private int fromRank;
	private PieceType movingPiece;

	public SanParser() {
	}

	private void reset() {
		move = null;
		promotionPiece = null;
		toIndex = -1;
		fromFile = -1;
		fromRank = -1;
		movingPiece = PieceType.PAWN;
	}

	public Move parse(String sanString, Position position) throws SanParsingException {
		reset();
		this.sanString = sanString;
		this.position = position;
		curIndex = sanString.length();
		nextChar();

		parseImpl();
		if (move != null) {
			return move;
		}
		move = findSpecificMove();
		move = new Move(move.getFrom(), move.getTo(), promotionPiece);
		return move;
	}

	private void parseImpl() throws SanParsingException {
		if (sanString.startsWith("O-O-O")) {
			move = position.isWhiteToMove() ? new Move(4, 2) : new Move(60, 58);
			return;
		} else if (sanString.startsWith("O-O")) {
			move = position.isWhiteToMove() ? new Move(4, 6) : new Move(60, 62);
			return;
		}

		// parse from back to front - this way we get the infos in nicer
		// order: 1. annotations, 2. promotional piece type, 3. destination
		// square, 4. source square infos, 5. moving piece type

		// annotations
		while (curChar == '+' || curChar == '#' || curChar == '!' || curChar == '?') {
			nextChar();
		}

		// promotional piece type
		promotionPiece = PieceType.getBySymbol(curChar);
		if (promotionPiece != null) {
			nextChar();
			if (curChar != '=') {
				throw new SanParsingException("Expected '=' before promotional piece, found " + curChar);
			}
			nextChar();
		}

		// destination square
		if (curChar < '1' || curChar > '8') {
			throw new SanParsingException("Expected destination rank indication (1..8), found " + curChar);
		}
		int dstRank = curChar - '1';
		nextChar();

		if (curChar < 'a' || curChar > 'h') {
			throw new SanParsingException("Expected destination file indication (a..h), found " + curChar);
		}
		int dstFile = curChar - 'a';
		nextChar();

		toIndex = dstFile + dstRank * 8;
		if (!hasMoreChars()) {
			return;
		}

		if (curChar == 'x') {
			nextChar();
			if (!hasMoreChars()) {
				return;
			}
		}

		// source square infos
		if (curChar >= '1' && curChar <= '8') {
			fromRank = curChar - '1';
			nextChar();
			if (!hasMoreChars()) {
				return;
			}
		}
		if (curChar >= 'a' && curChar <= 'h') {
			fromFile = curChar - 'a';
			nextChar();
			if (!hasMoreChars()) {
				return;
			}
		}

		// moving piece type
		movingPiece = PieceType.getBySymbol(curChar);
		if (movingPiece == null) {
			throw new SanParsingException("Expected moving piece type, found " + curChar);
		}
		nextChar();
		if (hasMoreChars()) {
			throw new SanParsingException("Expected end of move, found " + curChar);
		}
	}

	private void nextChar() {
		curIndex--;
		if (curIndex >= 0) {
			curChar = sanString.charAt(curIndex);
		} else {
			curChar = '\0';
		}
	}

	private boolean hasMoreChars() {
		return curIndex >= 0;
	}

	/**
	 * parsing is done, now find which move is meant
	 */
	private Move findSpecificMove() throws SanParsingException {
		// find the pieces that are candidates
		List<Integer> fromIndices = position
				.findPieces(Piece.getFromColorAndPieceType(position.isWhiteToMove(), movingPiece));
		if (fromIndices.size() == 0) {
			throw new SanParsingException("Found no matching piece on the board");
		}
		if (fromIndices.size() == 1) {
			return new Move(fromIndices.get(0), toIndex);
		}
		List<Move> moveCandidates = new ArrayList<Move>();
		for (int fromIndex : fromIndices) {
			moveCandidates.add(new Move(fromIndex, toIndex));
		}
		// filter the moves that are pseudo illegal
		// and the ones not fitting the move disambiguating info
		for (Iterator<Move> iterator = moveCandidates.iterator(); iterator.hasNext();) {
			Move move = iterator.next();
			if (fromFile >= 0 && fromFile != move.getFrom() % 8) {
				iterator.remove();
			} else if (fromRank >= 0 && fromRank != move.getFrom() / 8) {
				iterator.remove();
			} else if (!MoveChecker.isPseudoLegalMove(position, move).isLegal()) {
				iterator.remove();
			}
		}
		if (moveCandidates.size() == 0) {
			throw new SanParsingException("None of the pieces is on the specified file and/or rank");
		}
		if (moveCandidates.size() == 1) {
			return moveCandidates.get(0);
		}
		// filter the ones that cannot legally move
		filterIllegalMoves(moveCandidates);
		if (moveCandidates.size() == 0) {
			throw new SanParsingException("Moving any of the matching pieces would be a move into check");
		}
		if (moveCandidates.size() == 1) {
			return moveCandidates.get(0);
		}
		throw new SanParsingException("Ambiguous move, possible are " + moveCandidates);
	}

	private void filterIllegalMoves(List<Move> candidates) {
		for (Iterator<Move> iterator = candidates.iterator(); iterator.hasNext();) {
			Move move = iterator.next();
			Position trialBoard = PositionUtils.makeMove(position, move);
			if (MoveChecker.canCaptureKing(trialBoard)) {
				iterator.remove();
			}
		}
	}
}
