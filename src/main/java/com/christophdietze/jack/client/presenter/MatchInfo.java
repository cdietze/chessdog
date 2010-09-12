package com.christophdietze.jack.client.presenter;

import com.christophdietze.jack.shared.Player;

public class MatchInfo {

	private final Player whitePlayer;
	private final Player blackPlayer;
	private final boolean isPlayerWhite;

	public MatchInfo(Player whitePlayer, Player blackPlayer, boolean isPlayerWhite) {
		this.whitePlayer = whitePlayer;
		this.blackPlayer = blackPlayer;
		this.isPlayerWhite = isPlayerWhite;
	}

	public Player getWhitePlayer() {
		return whitePlayer;
	}

	public Player getBlackPlayer() {
		return blackPlayer;
	}

	public boolean isPlayerWhite() {
		return isPlayerWhite;
	}

	public Player getOpponent() {
		return isPlayerWhite ? blackPlayer : whitePlayer;
	}
}
