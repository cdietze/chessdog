package com.christophdietze.jack.client.presenter;

public class MatchInfo {

	private final long whitePlayerId;
	private final long blackPlayerId;
	private final boolean isPlayerWhite;

	public MatchInfo(long whitePlayerId, long blackPlayerId, boolean isPlayerWhite) {
		this.whitePlayerId = whitePlayerId;
		this.blackPlayerId = blackPlayerId;
		this.isPlayerWhite = isPlayerWhite;
	}

	public long getWhitePlayerId() {
		return whitePlayerId;
	}

	public long getBlackPlayerId() {
		return blackPlayerId;
	}

	public boolean isPlayerWhite() {
		return isPlayerWhite;
	}

	public long getOpponentId() {
		return isPlayerWhite ? blackPlayerId : whitePlayerId;
	}
}
