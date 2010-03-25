package com.christophdietze.jack.common.board;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

/**
 * A Immutable Position
 * 
 * <pre>
 *   |-----------------------|
 * 8 |56|57|58|59|60|61|62|63|
 *   |--+--+--+--+--+--+--+--|
 * 7 |48|49|50|51|52|53|54|55|
 *   |--+--+--+--+--+--+--+--|
 * 6 |40|41|42|43|44|45|46|47|
 *   |--+--+--+--+--+--+--+--|
 * 5 |32|33|34|35|36|37|38|39|
 *   |--+--+--+--+--+--+--+--|
 * 4 |24|25|26|27|28|29|30|31|
 *   |--+--+--+--+--+--+--+--|
 * 3 |16|17|18|19|20|21|22|23|
 *   |--+--+--+--+--+--+--+--|
 * 2 | 8| 9|10|11|12|13|14|15|
 *   |--+--+--+--+--+--+--+--|
 * 1 | 0| 1| 2| 3| 4| 5| 6| 7|
 *   |-----------------------|
 *     a  b  c  d  e  f  g  h
 * </pre>
 * 
 * 'a' to 'h' are called files and '1' to '8' are called ranks.
 */
public class Position {
	public static final Position STARTING_POSITION = buildStartingPosition();
	public static final Position EMPTY_POSITION = new Builder().build();

	private final ImmutableList<Piece> pieces;
	private final boolean whiteToMove;
	private final boolean canWhiteCastleKingside;
	private final boolean canWhiteCastleQueenside;
	private final boolean canBlackCastleKingside;
	private final boolean canBlackCastleQueenside;
	/** Starts with 1 and is incremented after every black move. */
	private final int fullmoveNumber;
	/** Number of plys since the last pawn has moved. */
	private final int halfmoveClock;
	/** After a double pawn move, contains the index of the skipped square. Is null otherwise. */
	private final Integer enPassantPawnIndex;
	private final ImmutableList<Integer> phantomKingIndices;

	private Position(Builder b) {
		this.pieces = ImmutableList.copyOf(b.pieces);
		this.whiteToMove = b.whiteToMove;
		this.canWhiteCastleKingside = b.canWhiteCastleKingside;
		this.canWhiteCastleQueenside = b.canWhiteCastleQueenside;
		this.canBlackCastleKingside = b.canBlackCastleKingside;
		this.canBlackCastleQueenside = b.canBlackCastleQueenside;
		this.fullmoveNumber = b.fullmoveNumber;
		this.halfmoveClock = b.halfmoveClock;
		this.enPassantPawnIndex = b.enPassantPawnIndex;
		this.phantomKingIndices = ImmutableList.copyOf(b.phantomKingIndices);
	}

	public Piece getPiece(int index) {
		return pieces.get(index);
	}

	public Piece getPiece(String algebraic) {
		return pieces.get(ChessUtils.toIndexFromAlgebraic(algebraic));
	}

	public boolean isWhiteToMove() {
		return whiteToMove;
	}

	public boolean canWhiteCastleKingside() {
		return canWhiteCastleKingside;
	}

	public boolean canWhiteCastleQueenside() {
		return canWhiteCastleQueenside;
	}

	public boolean canBlackCastleKingside() {
		return canBlackCastleKingside;
	}

	public boolean canBlackCastleQueenside() {
		return canBlackCastleQueenside;
	}

	public int getFullmoveNumber() {
		return fullmoveNumber;
	}

	public int getHalfmoveClock() {
		return halfmoveClock;
	}

	public ImmutableList<Integer> getPhantomKingIndices() {
		return phantomKingIndices;
	}

	public int getPly() {
		return ChessUtils.toPlyFromFullmoveNumber(fullmoveNumber, whiteToMove);
	}

	public List<Integer> findPieces(final Piece piece) {
		List<Integer> result = new ArrayList<Integer>();
		for (int index = 0; index < 64; ++index) {
			if (pieces.get(index).equals(piece)) {
				result.add(index);
			}
		}
		return result;
	}

	// public Position setWhiteToMove(boolean whiteToMove) {
	// return new Builder(this).whiteToMove(whiteToMove).build();
	// }

	public static class Builder {
		private List<Piece> pieces;
		private boolean whiteToMove = true;
		private boolean canWhiteCastleKingside = true;
		private boolean canWhiteCastleQueenside = true;
		private boolean canBlackCastleKingside = true;
		private boolean canBlackCastleQueenside = true;
		private int fullmoveNumber = 1;
		private int halfmoveClock = 0;
		private Integer enPassantPawnIndex;
		private List<Integer> phantomKingIndices = ImmutableList.of();

		public Builder() {
			pieces = Lists.newArrayList();
			for (int i = 0; i < 64; ++i) {
				pieces.add(Piece.EMPTY);
			}
		}

		public Builder(Position position) {
			this.pieces = position.pieces;
			this.whiteToMove = position.whiteToMove;
			this.canWhiteCastleKingside = position.canWhiteCastleKingside;
			this.canWhiteCastleQueenside = position.canWhiteCastleQueenside;
			this.canBlackCastleKingside = position.canBlackCastleKingside;
			this.canBlackCastleQueenside = position.canBlackCastleQueenside;
			this.fullmoveNumber = position.fullmoveNumber;
			this.halfmoveClock = position.halfmoveClock;
			this.phantomKingIndices = position.phantomKingIndices;
			this.enPassantPawnIndex = position.enPassantPawnIndex;
		}

		public Piece getPiece(int index) {
			return pieces.get(index);
		}

		public boolean isWhiteToMove() {
			return whiteToMove;
		}

		public int getFullmoveNumber() {
			return fullmoveNumber;
		}

		public Builder piece(int index, Piece piece) {
			if (pieces instanceof ImmutableList<?>) {
				this.pieces = Lists.newArrayList(this.pieces);
			}
			this.pieces.set(index, piece);
			return this;
		}

		public Builder whiteToMove(boolean whiteToMove) {
			this.whiteToMove = whiteToMove;
			return this;
		}

		public Builder canWhiteCastleKingside(boolean canWhiteCastleKingside) {
			this.canWhiteCastleKingside = canWhiteCastleKingside;
			return this;
		}

		public Builder canWhiteCastleQueenside(boolean canWhiteCastleQueenside) {
			this.canWhiteCastleQueenside = canWhiteCastleQueenside;
			return this;
		}

		public Builder canBlackCastleKingside(boolean canBlackCastleKingside) {
			this.canBlackCastleKingside = canBlackCastleKingside;
			return this;
		}

		public Builder canBlackCastleQueenside(boolean canBlackCastleQueenside) {
			this.canBlackCastleQueenside = canBlackCastleQueenside;
			return this;
		}

		public Builder fullmoveNumber(int fullmoveNumber) {
			this.fullmoveNumber = fullmoveNumber;
			return this;
		}

		public Builder halfmoveClock(int halfmoveClock) {
			this.halfmoveClock = halfmoveClock;
			return this;
		}

		public Builder enPassantPawnIndex(int enPassantPawnIndex, boolean isWhiteEnPassantPawn) {
			clearEnPassantPawn();
			this.enPassantPawnIndex = enPassantPawnIndex;
			piece(enPassantPawnIndex, isWhiteEnPassantPawn ? Piece.WHITE_EN_PASSANT_PAWN : Piece.BLACK_EN_PASSANT_PAWN);
			return this;
		}

		public Builder clearEnPassantPawn() {
			if (this.enPassantPawnIndex != null) {
				piece(enPassantPawnIndex, Piece.EMPTY);
			}
			this.enPassantPawnIndex = null;
			return this;
		}

		public Builder clearPhantomKings() {
			this.phantomKingIndices = ImmutableList.of();
			return this;
		}

		public Builder setPhantomKings(int indexA, int indexB) {
			this.phantomKingIndices = ImmutableList.of(indexA, indexB);
			return this;
		}

		public Builder switchPlayerToMove() {
			this.whiteToMove = !whiteToMove;
			return this;
		}

		public Position build() {
			return new Position(this);
		}
	}

	private static Position buildStartingPosition() {
		Builder b = new Builder();
		b.piece(0, Piece.WHITE_ROOK).piece(1, Piece.WHITE_KNIGHT).piece(2, Piece.WHITE_BISHOP)
				.piece(3, Piece.WHITE_QUEEN).piece(4, Piece.WHITE_KING).piece(5, Piece.WHITE_BISHOP).piece(6,
						Piece.WHITE_KNIGHT).piece(7, Piece.WHITE_ROOK);
		for (int i = 8; i < 16; ++i) {
			b.piece(i, Piece.WHITE_PAWN);
		}
		b.piece(56, Piece.BLACK_ROOK).piece(57, Piece.BLACK_KNIGHT).piece(58, Piece.BLACK_BISHOP).piece(59,
				Piece.BLACK_QUEEN).piece(60, Piece.BLACK_KING).piece(61, Piece.BLACK_BISHOP).piece(62, Piece.BLACK_KNIGHT)
				.piece(63, Piece.BLACK_ROOK);
		for (int i = 48; i < 56; ++i) {
			b.piece(i, Piece.BLACK_PAWN);
		}
		return b.build();
	}
}
