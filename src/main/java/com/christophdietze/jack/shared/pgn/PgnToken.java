package com.christophdietze.jack.shared.pgn;

import java.util.HashMap;
import java.util.Map;

public abstract class PgnToken {

	private TokenType type;

	public PgnToken(TokenType type) {
		this.type = type;
	}

	public abstract String getValue();

	public TokenType getType() {
		return type;
	}

	public static enum TokenType {

		STRING, SYMBOL, COMMENT, NAG, PERIOD('.'), ASTERISK('*'), LEFT_BRACKET('['), RIGHT_BRACKET(']'), LEFT_PARENTHESIS(
				'('), RIGHT_PARENTHESIS(')'), LEFT_ANGLE_BRACKET('<'), RIGHT_ANGLE_BRACKET('>');

		private static Map<Character, TokenType> charMap = new HashMap<Character, TokenType>();
		static {
			for (TokenType type : values()) {
				if (type.getCharacter() != null) {
					charMap.put(type.getCharacter(), type);
				}
			}
		}

		private Character character;

		private TokenType() {
		}

		private TokenType(char character) {
			this.character = character;
		}

		public Character getCharacter() {
			return character;
		}

		public static TokenType getTypeForCharacter(char c) {
			return charMap.get(c);
		}
	}

	public static class SimpleToken extends PgnToken {

		public SimpleToken(TokenType type) {
			super(type);
		}

		@Override
		public String getValue() {
			throw new AssertionError();
		}

		@Override
		public String toString() {
			return super.getType().getCharacter().toString();
		}
	}

	public static class CommentToken extends PgnToken {

		private String value;

		public CommentToken(String value) {
			super(TokenType.COMMENT);
			this.value = value;
		}

		@Override
		public String getValue() {
			return this.value;
		}

		@Override
		public String toString() {
			return "{" + value + "}";
		}
	}

	public static class StringToken extends PgnToken {

		private String value;

		public StringToken(String value) {
			super(TokenType.STRING);
			this.value = value;
		}

		@Override
		public String getValue() {
			return this.value;
		}

		@Override
		public String toString() {
			return "\"" + value + "\"";
		}
	}

	//
	// public static class IntegerToken extends PgnToken {
	// private int value;
	//
	// public IntegerToken(String intString) {
	// super(TokenType.INTEGER);
	// this.value = Integer.parseInt(intString);
	// }
	//
	// @Override
	// public String getValue() {
	// throw new AssertionError();
	// }
	//
	// @Override
	// public String toString() {
	// return Integer.toString(value);
	// }
	// }

	public static class SymbolToken extends PgnToken {

		private String value;

		public SymbolToken(String value) {
			super(TokenType.SYMBOL);
			this.value = value;
		}

		@Override
		public String getValue() {
			return this.value;
		}

		@Override
		public String toString() {
			return "<" + value + ">";
		}
	}
}
