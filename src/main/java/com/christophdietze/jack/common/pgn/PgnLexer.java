package com.christophdietze.jack.common.pgn;

import java.util.ArrayList;
import java.util.List;

import com.christophdietze.jack.common.pgn.PgnToken.CommentToken;
import com.christophdietze.jack.common.pgn.PgnToken.StringToken;
import com.christophdietze.jack.common.pgn.PgnToken.TokenType;

public class PgnLexer {

	private List<PgnToken> tokens = new ArrayList<PgnToken>();

	private int curIndex;

	private String inputString;

	private char c;

	public PgnLexer() {
	}

	public void lex(String pgnString) {
		resetLexer();
		inputString = pgnString;
		try {
			nextChar();
			while (true) {
				while (isWhitespace()) {
					nextChar();
				}
				lexToken();
			}
		} catch (EndOfStreamException ex) {
		}
	}

	public List<PgnToken> getTokens() {
		return tokens;
	}

	private void resetLexer() {
		tokens.clear();
		curIndex = -1;
		inputString = null;
		c = '\0';
	}

	private void nextChar() throws EndOfStreamException {
		curIndex++;
		if (curIndex >= inputString.length()) {
			throw new EndOfStreamException();
		}
		c = inputString.charAt(curIndex);
	}

	private boolean isWhitespace() {
		return c == ' ' || c == '\t' || c == '\n' || c == '\r';
	}

	private boolean isSymbolContinuationChar() {
		return (!isWhitespace() && c != '.');
	}

	private void addToken(PgnToken token) {
		tokens.add(token);
	}

	private void lexToken() throws EndOfStreamException {
		TokenType tokenType = TokenType.getTypeForCharacter(c);
		if (tokenType != null) {
			// we have a single char token
			addToken(new PgnToken.SimpleToken((tokenType)));
			nextChar();
		} else if (c == '{') {
			nextChar();
			lexComment();
		} else if (c == '\"') {
			nextChar();
			lexString();
			// } else if (Character.isDigit(c)) {
			// lexInteger();
		} else {
			lexSymbol();
		}
	}

	private void lexComment() throws EndOfStreamException {
		int startIndex = curIndex;
		while (c != '}') {
			nextChar();
		}
		addToken(new CommentToken(inputString.substring(startIndex, curIndex)));
		nextChar();
	}

	private void lexString() throws EndOfStreamException {
		int startIndex = curIndex;
		while (c != '\"') {
			nextChar();
		}
		addToken(new StringToken(inputString.substring(startIndex, curIndex)));
		nextChar();
	}

	// private void lexInteger() throws EndOfStreamException {
	// int startIndex = curIndex;
	// try {
	// while (!isWhitespace() && Character.isDigit(c)) {
	// nextChar();
	// }
	// addToken(new PgnToken.IntegerToken(inputString.substring(
	// startIndex,
	// curIndex)));
	// } catch (EndOfStreamException ex) {
	// addToken(new PgnToken.IntegerToken(inputString.substring(
	// startIndex,
	// curIndex)));
	// throw ex;
	// }
	// }

	private void lexSymbol() throws EndOfStreamException {
		int startIndex = curIndex;
		try {
			while (isSymbolContinuationChar()) {
				nextChar();
			}
			addToken(new PgnToken.SymbolToken(inputString.substring(startIndex, curIndex)));
		} catch (EndOfStreamException ex) {
			addToken(new PgnToken.SymbolToken(inputString.substring(startIndex, curIndex)));
			throw ex;
		}
	}

	@SuppressWarnings("serial")
	private static class EndOfStreamException extends Exception {
	}
}
