package com.christophdietze.jack.client.presenter;

import com.christophdietze.jack.common.board.PieceType;

public abstract class GameMode {

	void deactivate() {
	}

	public abstract void makePromotionMove(int fromIndex, int toIndex, PieceType promotionPiece);

	public abstract void makeMove(int fromIndex, int toIndex);
}
