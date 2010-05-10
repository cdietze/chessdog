package com.christophdietze.jack.common.board;

import java.util.List;

public class MoveChecker {

	/**
	 * A bitboard with an additional 2 square sized border.<br>
	 * Contains the index of the corresponding normal chess board square or -1 if out of bounds.<br>
	 */
	private static final int boundaryBoardIndices[] = new int[12 * 12];

	/**
	 * The offsets and directions are defined in the boundary board dimensions. Thus on a 12x12 board.
	 */
	private static final int kingMoveOffsets[] = { -13, -12, -11, -1, 1, 11, 12, 13 };
	private static final int knightMoveOffsets[] = { -25, -23, -14, -10, 10, 14, 23, 25 };
	private static final int bishopDirections[] = { -13, -11, 11, 13 };
	private static final int rookDirections[] = { -12, -1, 1, 12 };
	private static final int queenDirections[] = { -13, -12, -11, -1, 1, 11, 12, 13 };
	private static final int whitePawnOffsets[] = { 11, 12, 13, 24 };
	private static final int blackPawnOffsets[] = { -11, -12, -13, -24 };

	static {
		init();
	}

	private static void init() {
		initBoundaryBoard();
	}

	private static void initBoundaryBoard() {
		for (int x = 0; x < 12; ++x) {
			for (int y = 0; y < 12; ++y) {
				int file = x - 2;
				int rank = y - 2;
				if (file >= 0 && file <= 7 && rank >= 0 && rank <= 7) {
					boundaryBoardIndices[x + y * 12] = file + rank * 8;
				} else {
					boundaryBoardIndices[x + y * 12] = -1;
				}
			}
		}
	}

	public static MoveLegality isPseudoLegalMove(Position position, Move move) {
		Piece toSquare = position.getPiece(move.getTo());
		if (toSquare.isPiece() && toSquare.isWhite() == position.isWhiteToMove()) {
			// cannot capture or move over our own pieces
			return MoveLegality.CANNOT_CAPTURE_OWN_PIECES;
		}
		Piece fromSquare = position.getPiece(move.getFrom());
		PieceType piece = fromSquare.getPieceType();
		if (piece == null) {
			return MoveLegality.BASIC_ILLEGAL_MOVE;
		}

		if (position.isWhiteToMove() && !fromSquare.isWhite()) {
			return MoveLegality.IT_IS_WHITES_TURN;
		}
		if (!position.isWhiteToMove() && fromSquare.isWhite()) {
			return MoveLegality.IT_IS_BLACKS_TURN;
		}

		switch (piece) {
		case PAWN:
			return MoveLegality.valueOf(isPseudoLegalPawnMove(position, move));
		case KING:
			if (isPseudoLegalCastleMove(position, move)) {
				return MoveLegality.LEGAL_MOVE;
			} else {
				return MoveLegality.valueOf(isPseudoLegalMoveByOffset(position, kingMoveOffsets, move));
			}
		case KNIGHT:
			return MoveLegality.valueOf(isPseudoLegalMoveByOffset(position, knightMoveOffsets, move));
		case BISHOP:
			return MoveLegality.valueOf(isPseudoLegalMoveByDirection(position, bishopDirections, move));
		case ROOK:
			return MoveLegality.valueOf(isPseudoLegalMoveByDirection(position, rookDirections, move));
		case QUEEN:
			return MoveLegality.valueOf(isPseudoLegalMoveByDirection(position, queenDirections, move));
		default:
			throw new AssertionError();
		}
	}

	private static boolean isPseudoLegalMoveByOffset(Position position, int[] offsets, Move move) {
		int boundaryIndexFrom = toBoundaryIndex(move.getFrom());
		int boundaryIndexTo = toBoundaryIndex(move.getTo());
		int desiredOffset = boundaryIndexTo - boundaryIndexFrom;
		for (int possibleOffset : offsets) {
			if (desiredOffset == possibleOffset) {
				return true;
			}
		}
		return false;
	}

	private static boolean isPseudoLegalMoveByDirection(Position position, int[] directions, Move move) {
		int boundaryIndexFrom = toBoundaryIndex(move.getFrom());
		for (int dir : directions) {
			for (int curBoundaryIndex = boundaryIndexFrom + dir; boundaryBoardIndices[curBoundaryIndex] >= 0; curBoundaryIndex += dir) {
				int realIndex = toRealIndex(curBoundaryIndex);
				Piece toSquare = position.getPiece(realIndex);
				if (toSquare.isPiece() && toSquare.isWhite() == position.isWhiteToMove()) {
					// cannot capture or move over our own pieces
					break;
				}
				if (realIndex == move.getTo()) {
					return true;
				}
				if (toSquare.isPiece() && toSquare.isWhite() != position.isWhiteToMove()) {
					// we can capture the opponent's pieces, but not move over
					// them
					break;
				}
			}
		}
		return false;
	}

	private static boolean isPseudoLegalPawnMove(Position position, Move move) {
		final int moveOffsetsWhite[] = { 8, 16 };
		final int moveOffsetsBlack[] = { -8, -16 };
		final int captureOffsetsWhite[] = { 7, 9 };
		final int captureOffsetsBlack[] = { -9, -7 };

		int moveOffsets[] = position.isWhiteToMove() ? moveOffsetsWhite : moveOffsetsBlack;
		int captureOffsets[] = position.isWhiteToMove() ? captureOffsetsWhite : captureOffsetsBlack;
		int offset = move.getTo() - move.getFrom();
		Piece toSquare = position.getPiece(move.getTo());

		// cheap prefilter
		if (offset != moveOffsets[0] && offset != moveOffsets[1] && offset != captureOffsets[0]
				&& offset != captureOffsets[1]) {
			return false;
		}

		int file = move.getFrom() % 8;
		int rank = move.getFrom() / 8;
		if (offset == moveOffsets[0]) {
			return toSquare.isEmpty();
		}
		if (offset == moveOffsets[1]) {
			if ((position.isWhiteToMove() && rank != 1) || (!position.isWhiteToMove() && rank != 6)) {
				return false;
			}
			return toSquare.isEmpty() && position.getPiece(move.getFrom() + moveOffsets[0]).isEmpty();
		}

		if (offset == captureOffsets[0]) {
			return file != 0
					&& (toSquare.isPiece() || toSquare == Piece.WHITE_EN_PASSANT_PAWN || toSquare == Piece.BLACK_EN_PASSANT_PAWN)
					&& (toSquare.isWhite() != position.isWhiteToMove());
		}
		if (offset == captureOffsets[1]) {
			return file != 7
					&& (toSquare.isPiece() || toSquare == Piece.WHITE_EN_PASSANT_PAWN || toSquare == Piece.BLACK_EN_PASSANT_PAWN)
					&& (toSquare.isWhite() != position.isWhiteToMove());
		}
		// all other cases must have been prefiltered
		assert false;
		return false;
	}

	public static boolean isPseudoLegalCastleMove(Position position, Move move) {
		if (position.isWhiteToMove() && move.getFrom() == 4 && move.getTo() == 6) {
			return (position.canWhiteCastleKingside() && position.getPiece(4) == Piece.WHITE_KING
					&& position.getPiece(5) == Piece.EMPTY && position.getPiece(6) == Piece.EMPTY && position.getPiece(7) == Piece.WHITE_ROOK);
		}
		if (position.isWhiteToMove() && move.getFrom() == 4 && move.getTo() == 2) {
			return (position.canWhiteCastleQueenside() && position.getPiece(0) == Piece.WHITE_ROOK
					&& position.getPiece(1) == Piece.EMPTY && position.getPiece(2) == Piece.EMPTY
					&& position.getPiece(3) == Piece.EMPTY && position.getPiece(4) == Piece.WHITE_KING);
		}

		if (!position.isWhiteToMove() && move.getFrom() == 60 && move.getTo() == 62) {
			return (position.canBlackCastleKingside() && position.getPiece(60) == Piece.BLACK_KING
					&& position.getPiece(61) == Piece.EMPTY && position.getPiece(62) == Piece.EMPTY && position.getPiece(63) == Piece.BLACK_ROOK);
		}
		if (!position.isWhiteToMove() && move.getFrom() == 60 && move.getTo() == 58) {
			return (position.canBlackCastleQueenside() && position.getPiece(56) == Piece.BLACK_ROOK
					&& position.getPiece(57) == Piece.EMPTY && position.getPiece(58) == Piece.EMPTY
					&& position.getPiece(59) == Piece.EMPTY && position.getPiece(60) == Piece.BLACK_KING);
		}
		return false;
	}

	private static int toBoundaryIndex(int realIndex) {
		return realIndex % 8 + 2 + (realIndex / 8 + 2) * 12;
	}

	private static int toRealIndex(int boundaryIndex) {
		return boundaryIndex % 12 - 2 + (boundaryIndex / 12 - 2) * 8;
	}

	public static boolean canCaptureKing(Position position) {
		boolean isWhiteKing = !position.isWhiteToMove();
		Piece kingSquare = isWhiteKing ? Piece.WHITE_KING : Piece.BLACK_KING;
		List<Integer> kingIndices = position.findPieces(kingSquare);
		assert kingIndices.size() == 1;
		kingIndices.addAll(position.getPhantomKingIndices());
		for (int index = 0; index < 64; ++index) {
			Piece srcSquare = position.getPiece(index);
			if (srcSquare.isPiece() && srcSquare.isWhite() != isWhiteKing) {
				for (int kingIndex : kingIndices) {
					Move move = new Move(index, kingIndex);
					if (isPseudoLegalMove(position, move).isLegal()) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public static boolean isLegalMove(Position position, Move move) {
		if (!isPseudoLegalMove(position, move).isLegal()) {
			return false;
		}
		Position trialPosition = PositionUtils.makeMove(position, move);
		if (canCaptureKing(trialPosition)) {
			return false;
		}
		return true;
	}

	/**
	 * @return true exactly when the current player has at least one legal move.
	 */
	public static boolean hasLegalMove(Position position) {
		for (int index = 0; index < 64; ++index) {
			Piece piece = position.getPiece(index);
			if (piece.isEmpty() || piece.isWhite() != position.isWhiteToMove()) {
				continue;
			}
			switch (piece.getPieceType()) {
			case PAWN:
				if (hasLegalPawnMoves(position, index)) {
					return true;
				}
				break;
			case KING:
				// A king always has a normal legal move when he has a legal castle move, thus we can skip the castle moves
				if (hasLegalMoveByOffset(position, index, kingMoveOffsets)) {
					return true;
				}
				break;
			case KNIGHT:
				if (hasLegalMoveByOffset(position, index, knightMoveOffsets)) {
					return true;
				}
				break;
			case BISHOP:
				if (hasLegalMoveByDirection(position, index, bishopDirections)) {
					return true;
				}
				break;
			case ROOK:
				if (hasLegalMoveByDirection(position, index, rookDirections)) {
					return true;
				}
				break;
			case QUEEN:
				if (hasLegalMoveByDirection(position, index, queenDirections)) {
					return true;
				}
				break;
			default:
				throw new AssertionError();
			}
		}
		return false;
	}

	private static boolean hasLegalMoveByOffset(Position position, int index, int[] offsets) {
		int boundaryIndex = toBoundaryIndex(index);
		for (int offset : offsets) {
			int toBoundaryIndex = boundaryIndex + offset;
			if (boundaryBoardIndices[toBoundaryIndex] < 0) {
				continue;
			}
			if (isLegalMove(position, new Move(index, toRealIndex(toBoundaryIndex)))) {
				return true;
			}
		}
		return false;
	}

	private static boolean hasLegalMoveByDirection(Position position, int index, int[] directions) {
		int boundaryIndex = toBoundaryIndex(index);
		for (int direction : directions) {
			for (int curBoundaryIndex = boundaryIndex + direction; boundaryBoardIndices[curBoundaryIndex] >= 0; curBoundaryIndex += direction) {
				Move move = new Move(index, toRealIndex(curBoundaryIndex));
				if (!isPseudoLegalMove(position, move).isLegal()) {
					break;
				}
				if (isLegalMove(position, move)) {
					return true;
				}
			}
		}
		return false;
	}

	private static boolean hasLegalPawnMoves(Position position, int index) {
		int boundaryIndex = toBoundaryIndex(index);
		int[] offsets = position.isWhiteToMove() ? whitePawnOffsets : blackPawnOffsets;
		for (int offset : offsets) {
			int toIndex = boundaryBoardIndices[boundaryIndex + offset];
			if (toIndex < 0) {
				continue;
			}
			int toRank = ChessUtils.toRank(toIndex);
			PieceType promotionPiece = position.isWhiteToMove() ? (toRank != 7 ? null : PieceType.QUEEN)
					: (toRank != 0 ? null : PieceType.QUEEN);
			Move move = new Move(index, toIndex, promotionPiece);
			if (isLegalMove(position, move)) {
				return true;
			}
		}
		return false;
	}
}
