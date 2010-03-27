package com.christophdietze.jack.common;

import com.christophdietze.jack.common.util.SimpleToStringBuilder;

public class MatchStartedEvent extends RemoteEvent {
	private long whitePlayerId;
	private long blackPlayerId;

	@SuppressWarnings("unused")
	private MatchStartedEvent() {
	}

	public MatchStartedEvent(long whitePlayerId, long blackPlayerId) {
		this.whitePlayerId = whitePlayerId;
		this.blackPlayerId = blackPlayerId;
	}

	public long getWhitePlayerId() {
		return whitePlayerId;
	}
	public long getBlackPlayerId() {
		return blackPlayerId;
	}

	@Override
	public String toString() {
		return SimpleToStringBuilder.create(this).append("white", whitePlayerId).append("black", blackPlayerId)
				.toString();
	}
}
