package com.christophdietze.jack.common.board;

import java.util.ArrayList;
import java.util.List;

/**
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

	public static final Position STARTING_POSITION;
	static {
		STARTING_POSITION = new Position();
		STARTING_POSITION.setupStartingPosition();
	}

	private Piece[] pieces;

	private boolean whiteToMove;
	private boolean canWhiteCastleKingside;
	private boolean canWhiteCastleQueenside;
	private boolean canBlackCastleKingside;
	private boolean canBlackCastleQueenside;

	// first move is 1
	private int fullmoveNumber = 1;

	private int halfmoveClock;

	private List<Integer> phantomKingIndices = new ArrayList<Integer>();

	private Integer enPassantPawnIndex;

	/**
	 * Creates an empty position
	 */
	public Position() {
		pieces = new Piece[64];
		clear();
	}

	public Position(Position position) {
		this.copy(position);
	}

	public Piece getPiece(int index) {
		return pieces[index];
	}

	public Piece getPiece(String algebraic) {
		return pieces[ChessUtils.toIndexFromAlgebraic(algebraic)];
	}

	public void setPiece(int index, Piece piece) {
		pieces[index] = piece;
	}

	public void clear() {
		for (int i = 0; i < pieces.length; ++i) {
			pieces[i] = Piece.EMPTY;
		}
		whiteToMove = true;
		canWhiteCastleKingside = true;
		canWhiteCastleQueenside = true;
		canBlackCastleKingside = true;
		canBlackCastleQueenside = true;
		fullmoveNumber = 1;
		halfmoveClock = 0;
		phantomKingIndices.clear();
	}

	public void setupStartingPosition() {
		clear();
		pieces[0] = Piece.WHITE_ROOK;
		pieces[1] = Piece.WHITE_KNIGHT;
		pieces[2] = Piece.WHITE_BISHOP;
		pieces[3] = Piece.WHITE_QUEEN;
		pieces[4] = Piece.WHITE_KING;
		pieces[5] = Piece.WHITE_BISHOP;
		pieces[6] = Piece.WHITE_KNIGHT;
		pieces[7] = Piece.WHITE_ROOK;
		for (int i = 8; i < 16; ++i) {
			pieces[i] = Piece.WHITE_PAWN;
		}
		pieces[56] = Piece.BLACK_ROOK;
		pieces[57] = Piece.BLACK_KNIGHT;
		pieces[58] = Piece.BLACK_BISHOP;
		pieces[59] = Piece.BLACK_QUEEN;
		pieces[60] = Piece.BLACK_KING;
		pieces[61] = Piece.BLACK_BISHOP;
		pieces[62] = Piece.BLACK_KNIGHT;
		pieces[63] = Piece.BLACK_ROOK;
		for (int i = 48; i < 56; ++i) {
			pieces[i] = Piece.BLACK_PAWN;
		}
		whiteToMove = true;
	}

	public void makeMoveVerified(Move move) throws IllegalMoveException {
		MoveLegality legality = MoveChecker.isPseudoLegalMove(move, this);
		if (!legality.isLegal()) {
			throw new IllegalMoveException(legality.getMessage());
		}
		Position trialPosition = this.copy();
		trialPosition.makeMove(move);
		if (MoveChecker.canCaptureKing(trialPosition)) {
			throw new IllegalMoveException();
		}
		this.copy(trialPosition);
	}

	public void makeMove(Move move) {
		clearPhantomKings();

		if (MoveUtil.makeCastleMove(move, this)) {
			clearEnPassantPawn();
		} else {
			// normal move
			Piece fromPiece = this.getPiece(move.getFrom());
			Piece toPiece = this.getPiece(move.getTo());
			// adjust castling availability
			if (fromPiece == Piece.WHITE_KING) {
				this.canWhiteCastleKingside = false;
				this.canWhiteCastleQueenside = false;
			} else if (fromPiece == Piece.BLACK_KING) {
				this.canBlackCastleKingside = false;
				this.canBlackCastleQueenside = false;
			}
			if (move.getFrom() == 7) {
				this.canWhiteCastleKingside = false;
			} else if (move.getFrom() == 0) {
				this.canWhiteCastleQueenside = false;
			} else if (move.getFrom() == 63) {
				this.canBlackCastleKingside = false;
			} else if (move.getFrom() == 56) {
				this.canBlackCastleQueenside = false;
			}

			// if we're capturing an en passant pawn, remove the real pawn, too
			if (toPiece == Piece.WHITE_EN_PASSANT_PAWN) {
				this.setPiece(move.getTo() + 8, Piece.EMPTY);
			} else if (toPiece == Piece.BLACK_EN_PASSANT_PAWN) {
				this.setPiece(move.getTo() - 8, Piece.EMPTY);
			}

			clearEnPassantPawn();

			// create en passant pawns if necessary
			if (fromPiece == Piece.WHITE_PAWN && move.getFrom() / 8 == 1 && move.getTo() / 8 == 3) {
				int enPassantPosition = move.getFrom() + 8;
				this.setPiece(enPassantPosition, Piece.WHITE_EN_PASSANT_PAWN);
				this.enPassantPawnIndex = enPassantPosition;
			} else if (fromPiece == Piece.BLACK_PAWN && move.getFrom() / 8 == 6 && move.getTo() / 8 == 4) {
				int enPassantPosition = move.getFrom() - 8;
				this.setPiece(enPassantPosition, Piece.BLACK_EN_PASSANT_PAWN);
				this.enPassantPawnIndex = enPassantPosition;
			}

			boolean isPromotion = MoveUtil.isPseudoPromotionMove(move, this);

			// actually move the selected piece
			this.setPiece(move.getTo(), fromPiece);
			this.setPiece(move.getFrom(), Piece.EMPTY);

			// if necessary, perform a promotion
			if (isPromotion) {
				if (move.getPromotionPiece() == null) {
					throw new RuntimeException("Made a promotion move, but no promotion piece selected");
				}
				Piece promoPiece = Piece.getFromColorAndPiece(whiteToMove, move.getPromotionPiece());
				this.setPiece(move.getTo(), promoPiece);
			}
		}
		this.whiteToMove = !whiteToMove;
		if (this.whiteToMove) {
			fullmoveNumber++;
		}
	}

	private void clearPhantomKings() {
		this.phantomKingIndices.clear();
	}

	private void clearEnPassantPawn() {
		if (enPassantPawnIndex != null) {
			setPiece(enPassantPawnIndex, Piece.EMPTY);
		}
		this.enPassantPawnIndex = null;
	}

	public Integer getEnPassantPawnIndex() {
		return enPassantPawnIndex;
	}

	public void setEnPassantPawnIndex(int index) {
		clearEnPassantPawn();
		this.enPassantPawnIndex = index;
		this.setPiece(enPassantPawnIndex, whiteToMove ? Piece.BLACK_EN_PASSANT_PAWN : Piece.WHITE_EN_PASSANT_PAWN);
	}

	public boolean isWhiteToMove() {
		return whiteToMove;
	}

	public void setWhiteToMove(boolean whiteToMove) {
		if (this.whiteToMove != whiteToMove) {
			clearPhantomKings();
			clearEnPassantPawn();
		}
		this.whiteToMove = whiteToMove;
	}

	public boolean canWhiteCastleKingside() {
		return canWhiteCastleKingside;
	}

	public void setCanWhiteCastleKingside(boolean canWhiteCastleKingside) {
		this.canWhiteCastleKingside = canWhiteCastleKingside;
	}

	public boolean canWhiteCastleQueenside() {
		return canWhiteCastleQueenside;
	}

	public void setCanWhiteCastleQueenside(boolean canWhiteCastleQueenside) {
		this.canWhiteCastleQueenside = canWhiteCastleQueenside;
	}

	public boolean canBlackCastleKingside() {
		return canBlackCastleKingside;
	}

	public void setCanBlackCastleKingside(boolean canBlackCastleKingside) {
		this.canBlackCastleKingside = canBlackCastleKingside;
	}

	public boolean canBlackCastleQueenside() {
		return canBlackCastleQueenside;
	}

	public void setCanBlackCastleQueenside(boolean canBlackCastleQueenside) {
		this.canBlackCastleQueenside = canBlackCastleQueenside;
	}

	public int getFullmoveNumber() {
		return fullmoveNumber;
	}

	public int getPly() {
		return ChessUtils.toPlyFromFullmoveNumber(fullmoveNumber, isWhiteToMove());
	}

	public void setFullmoveNumber(int fullmoveNumber) {
		this.fullmoveNumber = fullmoveNumber;
	}

	public int getHalfmoveClock() {
		return halfmoveClock;
	}

	public void setHalfmoveClock(int halfmoveClock) {
		this.halfmoveClock = halfmoveClock;
	}

	public void addPhantomKing(int index) {
		this.phantomKingIndices.add(index);
	}

	public List<Integer> getPhantomKingIndices() {
		return phantomKingIndices;
	}

	public String toDiagramString() {
		StringBuilder sb = new StringBuilder();
		for (int y = 7; y >= 0; --y) {
			if (y == 7) {
				sb.append("\n");
			} else {
				sb.append("|\n");
			}
			sb.append("--------------------------------\n");
			for (int x = 0; x < 8; ++x) {
				int index = y * 8 + x;
				sb.append("| ");
				sb.append(pieces[index].getSymbol());
				sb.append(" ");
			}
		}
		sb.append("|\n--------------------------------\n");
		return sb.toString();
	}

	public List<Integer> findPieces(Piece piece) {
		List<Integer> result = new ArrayList<Integer>();
		for (int index = 0; index < 64; ++index) {
			if (pieces[index].equals(piece)) {
				result.add(index);
			}
		}
		return result;
	}

	public void copy(Position position) {
		this.pieces = new Piece[64];
		for (int i = 0; i < 64; ++i) {
			this.pieces[i] = position.pieces[i];
		}
		this.whiteToMove = position.whiteToMove;
		this.canWhiteCastleKingside = position.canWhiteCastleKingside;
		this.canWhiteCastleQueenside = position.canWhiteCastleQueenside;
		this.canBlackCastleKingside = position.canBlackCastleKingside;
		this.canBlackCastleQueenside = position.canBlackCastleQueenside;
		this.fullmoveNumber = position.fullmoveNumber;
		this.halfmoveClock = position.halfmoveClock;
		this.phantomKingIndices = new ArrayList<Integer>(position.phantomKingIndices);
		this.enPassantPawnIndex = position.enPassantPawnIndex;
	}

	public Position copy() {
		return new Position(this);
	}
}
