package com.christophdietze.jack.shared;

import com.christophdietze.jack.shared.util.SimpleToStringBuilder;

public class MoveMadeRemoteEvent extends RemoteEvent {

	private String algebraicMove;

	@SuppressWarnings("unused")
	private MoveMadeRemoteEvent() {
	}

	public MoveMadeRemoteEvent(String algebraicMove) {
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
