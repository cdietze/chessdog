package com.christophdietze.jack.client.presenter;

public class MatchInfo {

	private final long whitePlayerId;
	private final long blackPlayerId;

	public MatchInfo(long whitePlayerId, long blackPlayerId) {
		this.whitePlayerId = whitePlayerId;
		this.blackPlayerId = blackPlayerId;
	}

	public long getWhitePlayerId() {
		return whitePlayerId;
	}

	public long getBlackPlayerId() {
		return blackPlayerId;
	}
}
