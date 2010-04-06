package com.christophdietze.jack.common;

import com.christophdietze.jack.common.util.SimpleToStringBuilder;

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