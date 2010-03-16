package com.christophdietze.jack.common.pgn;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.christophdietze.jack.common.board.GameResult;
import com.christophdietze.jack.common.board.IllegalPositionException;
import com.christophdietze.jack.common.board.Move;
import com.christophdietze.jack.common.board.Position;
import com.christophdietze.jack.common.board.SanParser;
import com.christophdietze.jack.common.board.SanParsingException;
import com.christophdietze.jack.common.pgn.PgnToken.TokenType;

public class PgnParser {

	private PgnLexer lexer = new PgnLexer();
	private SanParser sanParser = new SanParser();
	private Iterator<PgnToken> tokenIterator;
	private PgnToken curToken;

	private Position replayBoard = new Position();
	private Position initialPosition = new Position();
	private List<Move> moves = new ArrayList<Move>();
	private String whitePlayerName;
	private String blackPlayerName;
	private Map<String, String> additionalTags = new HashMap<String, String>();
	private GameResult gameResult;

	public PgnParser() {
	}

	public void parse(String pgnString) throws PgnParsingException {
		clear();
		lexer.lex(pgnString);
		tokenIterator = lexer.getTokens().iterator();
		nextToken();
		parsePgnDatabase();
	}

	public Position getInitialPosition() {
		return initialPosition;
	}

	public List<Move> getMoves() {
		return moves;
	}

	/**
	 * @return null if no name
	 */
	public String getWhitePlayerName() {
		return whitePlayerName;
	}

	/**
	 * @return null if no name
	 */
	public String getBlackPlayerName() {
		return blackPlayerName;
	}

	public GameResult getGameResult() {
		return gameResult;
	}

	public Map<String, String> getAdditionalTags() {
		return additionalTags;
	}

	private void clear() {
		moves.clear();
		initialPosition.setupStartingPosition();
		replayBoard.setupStartingPosition();
		whitePlayerName = null;
		blackPlayerName = null;
		gameResult = null;
		additionalTags.clear();
	}

	private void nextToken() {
		if (tokenIterator.hasNext()) {
			curToken = tokenIterator.next();
		} else {
			curToken = null;
		}
	}

	private void expectTokenType(TokenType type) throws PgnParsingException {
		if (curToken.getType() != type) {
			throw new PgnParsingException("Expected a token of type " + type + ", found " + curToken);
		}
	}

	private void parsePgnDatabase() throws PgnParsingException {
		while (curToken != null) {
			parsePgnGame();
		}
	}

	private void parsePgnGame() throws PgnParsingException {
		parseTagSection();
		parseMovetextSection();
	}

	private void parseTagSection() throws PgnParsingException {
		while (curToken != null && curToken.getType() == TokenType.LEFT_BRACKET) {
			nextToken();
			expectTokenType(TokenType.SYMBOL);
			String tagName = curToken.getValue();
			nextToken();
			expectTokenType(TokenType.STRING);
			String tagValue = curToken.getValue();
			nextToken();
			expectTokenType(TokenType.RIGHT_BRACKET);
			nextToken();
			if (tagName.equals(PgnStandardTagName.White.name())) {
				parsePlayerName(tagValue, true);
			} else if (tagName.equals(PgnStandardTagName.Black.name())) {
				parsePlayerName(tagValue, false);
			} else if (tagName.equals(PgnStandardTagName.Result.name())) {
				GameResult result = GameResult.parseSymbol(tagValue);
				if (result == null) {
					throw new PgnParsingException("Failed to parse Result tag with value '" + tagValue + "'");
				}
				this.gameResult = result;
			} else if (tagName.equals("FEN")) {
				try {
					String fenString = tagValue;
					FenParser fenParser = new FenParser();
					try {
						fenParser.parse(fenString);
					} catch (IllegalPositionException ex) {
						throw new PgnParsingException(ex);
					}
					initialPosition = fenParser.getPosition();
					replayBoard = initialPosition.copy();
				} catch (FenParsingException ex) {
					throw new PgnParsingException(ex);
				}
			} else {
				additionalTags.put(tagName, tagValue);
			}
		}
	}

	private void parsePlayerName(String name, boolean isWhite) {
		String result = (name == null || name.equals("") || name.equals("?") ? null : name);
		if (isWhite) {
			whitePlayerName = result;
		} else {
			blackPlayerName = result;
		}
	}

	private void parseMovetextSection() throws PgnParsingException {
		while (curToken != null) {
			if (curToken.getType() == TokenType.LEFT_PARENTHESIS) {
				throw new PgnParsingException("Parsing of recursive variation not supported");
			} else if (curToken.getType() == TokenType.ASTERISK) {
				this.gameResult = GameResult.UNDECIDED;
				nextToken();
				return;
			} else if (curToken.getType() == TokenType.SYMBOL) {
				GameResult result = GameResult.parseSymbol(curToken.getValue());
				if (result != null) {
					if (this.gameResult != null && this.gameResult != result) {
						throw new PgnParsingException("Conflicting specifications of game result, found " + this.gameResult
								+ " and " + result);
					}
					this.gameResult = result;
					nextToken();
					return;
				}
			}
			parseElement();
		}
	}

	private void parseElement() throws PgnParsingException {
		if (curToken.getType() == TokenType.COMMENT) {
			// TODO save comments
			nextToken();
			return;
		}
		expectTokenType(TokenType.SYMBOL);
		String value = curToken.getValue();
		if (Character.isDigit(value.charAt(0))) {
			// move number
			parseMoveNumberIndication();
			return;
		}
		if (value.charAt(0) == '$') {
			throw new PgnParsingException("Parsing of NAG not supported");
		} else {
			// SAN-move
			String sanString = curToken.getValue();
			try {
				Move move = sanParser.parse(sanString, replayBoard);
				replayBoard.makeMove(move);
				moves.add(move);
			} catch (SanParsingException ex) {
				throw new PgnParsingException("Parsing of move '" + sanString + "' failed", ex);
			}

			nextToken();
		}
	}

	private void parseMoveNumberIndication() throws PgnParsingException {
		expectTokenType(TokenType.SYMBOL);
		Integer.parseInt(curToken.getValue());
		nextToken();
		while (curToken.getType() == TokenType.PERIOD) {
			nextToken();
		}
	}
}
