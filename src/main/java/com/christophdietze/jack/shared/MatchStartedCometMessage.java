package com.christophdietze.jack.shared;

import com.christophdietze.jack.shared.util.SimpleToStringBuilder;

public class MatchStartedCometMessage extends CometMessage {
	private Player whitePlayer;
	private Player blackPlayer;

	/**
	 * For serialization
	 */
	@SuppressWarnings("unused")
	private MatchStartedCometMessage() {
	}

	public MatchStartedCometMessage(Player whitePlayer, Player blackPlayer) {
		this.whitePlayer = whitePlayer;
		this.blackPlayer = blackPlayer;
	}

	public Player getWhitePlayer() {
		return whitePlayer;
	}

	public Player getBlackPlayer() {
		return blackPlayer;
	}

	@Override
	public String toString() {
		return SimpleToStringBuilder.create(this).append("white", whitePlayer).append("black", blackPlayer).toString();
	}
}
