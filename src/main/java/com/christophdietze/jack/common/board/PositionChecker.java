package com.christophdietze.jack.common.board;

public class PositionChecker {

	private StringBuilder sb;

	public PositionChecker() {
	}

	public void checkForSensiblePosition(Position2 position) throws IllegalPositionException {
		sb = new StringBuilder();
		checkKingCount(position);
		checkPawnPositions(position);
		if (sb.length() == 0) {
			// this check is only allowed when the king count check passed
			checkCheckSituation(position);
		}
		if (sb.length() == 0) {
			return;
		}
		sb.insert(0, "This position is illegal. ");
		String msg = sb.toString();
		throw new IllegalPositionException(msg);
	}

	private void checkKingCount(Position2 position) {
		int numWhiteKings = 0;
		int numBlackKings = 0;
		for (int index = 0; index < 64; ++index) {
			if (position.getPiece(index) == Piece.WHITE_KING) {
				numWhiteKings++;
			}
			if (position.getPiece(index) == Piece.BLACK_KING) {
				numBlackKings++;
			}
		}
		if (numWhiteKings != 1 || numBlackKings != 1) {
			sb.append("Exactly one white and one black king are required. ");
		}
	}

	private void checkPawnPositions(Position2 position) {
		for (int index = 0; index < 64; ++index) {
			int rank = ChessUtils.toRank(index);
			if (rank == 0 || rank == 7) {
				if (position.getPiece(index) == Piece.WHITE_PAWN || position.getPiece(index) == Piece.BLACK_PAWN) {
					sb.append("There are pawns on ranks 1 or 8. ");
					return;
				}
			}
		}
	}

	private void checkCheckSituation(Position2 position) {
		if (MoveChecker2.canCaptureKing(position)) {
			sb.append("The player to move can capture the opponent's king. ");
		}
	}
}
