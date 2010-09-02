package com.christophdietze.jack.shared;

import com.christophdietze.jack.shared.util.SimpleToStringBuilder;

public class MoveMadeCometMessage extends CometMessage {

	private String algebraicMove;

	/**
	 * For serialization
	 */
	@SuppressWarnings("unused")
	private MoveMadeCometMessage() {
	}

	public MoveMadeCometMessage(String algebraicMove) {
		this.algebraicMove = algebraicMove;
	}

	public String getAlgebraicMove() {
		return algebraicMove;
	}

	@Override
	public String toString() {
		return SimpleToStringBuilder.create(this).append(algebraicMove).toString();
	}
}
