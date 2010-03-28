package com.christophdietze.jack.client.presenter;

import com.christophdietze.jack.common.board.PieceType;

public interface GameMode {

	public void makePromotionMove(int fromIndex, int toIndex, PieceType promotionPiece);

	public void makeMove(int fromIndex, int toIndex);
}
