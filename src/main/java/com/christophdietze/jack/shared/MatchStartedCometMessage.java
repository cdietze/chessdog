package com.christophdietze.jack.shared;

import com.christophdietze.jack.shared.util.SimpleToStringBuilder;

public class MatchStartedCometMessage extends CometMessage {
	private long whitePlayerId;
	private long blackPlayerId;

	/**
	 * For serialization
	 */
	@SuppressWarnings("unused")
	private MatchStartedCometMessage() {
	}

	public MatchStartedCometMessage(long whitePlayerId, long blackPlayerId) {
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
