package com.christophdietze.jack.common.board;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SanWriter {

	private StringBuilder sb;
	private Position position;
	private Move move;

	private Piece fromSquare;
	private Piece toSquare;

	public String write(Position position, Move move) throws SanWritingException {
		this.sb = new StringBuilder();
		this.position = position;
		this.move = move;

		fromSquare = position.getPiece(move.getFrom());
		toSquare = position.getPiece(move.getTo());

		boolean isCastle = writeIfCastle();
		if (!isCastle) {
			writePieceSymbol();
			writeFrom();
			writeCaptureX();
			writeTo();
			writePromotion();
		}

		writeMateSuffix();

		String result = sb.toString();
		sb = null;
		position = null;
		move = null;
		fromSquare = null;
		toSquare = null;
		return result;
	}

	private boolean writeIfCastle() {
		if (fromSquare.getPieceType() != PieceType.KING) {
			return false;
		}
		if (move.getFrom() == 4 && move.getTo() == 6) {
			sb.append("O-O");
			return true;
		}
		if (move.getFrom() == 4 && move.getTo() == 2) {
			sb.append("O-O-O");
			return true;
		}
		if (move.getFrom() == 60 && move.getTo() == 62) {
			sb.append("O-O");
			return true;
		}
		if (move.getFrom() == 60 && move.getTo() == 58) {
			sb.append("O-O-O");
			return true;
		}
		return false;
	}

	private void writePieceSymbol() {
		if (fromSquare.getPieceType() != PieceType.PAWN) {
			sb.append(fromSquare.getPieceType().getSymbol());
		}
	}

	private void writeFrom() throws SanWritingException {
		List<Integer> fromIndices = position.findPieces(fromSquare);
		if (fromIndices.size() == 0) {
			throw new SanWritingException("No piece of matching type found");
		}
		if (fromIndices.size() == 1) {
			return;
		}

		List<Move> moveCandidates = new ArrayList<Move>();
		for (int fromIndex : fromIndices) {
			moveCandidates.add(new Move(fromIndex, move.getTo()));
		}
		// filter the moves that are pseudo illegal
		for (Iterator<Move> iterator = moveCandidates.iterator(); iterator.hasNext();) {
			Move moveCandidate = iterator.next();
			if (!MoveChecker.isPseudoLegalMove(position, moveCandidate).isLegal()) {
				iterator.remove();
			}
		}
		if (moveCandidates.size() == 0) {
			throw new SanWritingException("No piece can pseudo-legally do that move");
		}
		if (moveCandidates.size() == 1) {
			return;
		}
		// filter the moves that are really illegal
		for (Iterator<Move> iterator = moveCandidates.iterator(); iterator.hasNext();) {
			Move moveCandidate = iterator.next();
			if (!MoveChecker.isLegalMove(position, moveCandidate)) {
				iterator.remove();
			}
		}
		if (moveCandidates.size() == 1) {
			return;
		}
		// more disambiguation is required - try the following:
		// 1. only by file
		// 2. only by rank
		// 3. by file and rank
		List<Integer> concurrentFromIndeces = new ArrayList<Integer>();
		for (Move moveCandidate : moveCandidates) {
			if (moveCandidate.getFrom() == move.getFrom()) {
				continue;
			}
			concurrentFromIndeces.add(moveCandidate.getFrom());
		}
		boolean onlyFileSuffices = true;
		int fromFile = ChessUtils.toFile(move.getFrom());
		for (int concurrentIndex : concurrentFromIndeces) {
			if (ChessUtils.toFile(concurrentIndex) == fromFile) {
				onlyFileSuffices = false;
				break;
			}
		}
		if (onlyFileSuffices) {
			sb.append(ChessUtils.toFileChar(fromFile));
			return;
		}
		boolean onlyRankSuffices = true;
		int fromRank = ChessUtils.toRank(move.getFrom());
		for (int concurrentIndex : concurrentFromIndeces) {
			if (ChessUtils.toRank(concurrentIndex) == fromRank) {
				onlyRankSuffices = false;
				break;
			}
		}
		if (onlyRankSuffices) {
			sb.append(ChessUtils.toRankChar(fromRank));
			return;
		} else {
			sb.append(ChessUtils.toFileChar(fromFile));
			sb.append(ChessUtils.toRankChar(fromRank));
			return;
		}
	}

	private void writeTo() {
		sb.append(ChessUtils.toAlgebraicSquare(move.getTo()));
	}

	private void writeCaptureX() {
		if (toSquare.getPieceType() != null) {
			sb.append("x");
		} else {
			if (fromSquare.getPieceType() == PieceType.PAWN
					&& (toSquare == Piece.WHITE_EN_PASSANT_PAWN || toSquare == Piece.BLACK_EN_PASSANT_PAWN)) {
				sb.append("x");
			}
		}
	}

	private void writePromotion() {
		if (move.getPromotionPiece() != null) {
			sb.append("=");
			sb.append(move.getPromotionPiece().getSymbol());
		}
	}

	/**
	 * Appends + or # when appropriate
	 */
	private void writeMateSuffix() {
		Position position2 = PositionUtils.makeMove(position, move);
		position2 = new Position.Builder(position2).whiteToMove(!position2.isWhiteToMove()).build();
		if (MoveChecker.canCaptureKing(position2)) {
			sb.append("+");
			// TODO check if it is a mate and append # and set the game result,
			// if so
		}
	}
}
