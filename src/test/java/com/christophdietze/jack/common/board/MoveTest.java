package com.christophdietze.jack.common.board;

import com.christophdietze.jack.MyTestCase;
import com.christophdietze.jack.common.board.ChessUtils;
import com.christophdietze.jack.common.board.IllegalMoveException;
import com.christophdietze.jack.common.board.Piece;
import com.christophdietze.jack.common.board.Position;

public class MoveTest extends MyTestCase {

	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testWhiteCastleKingside() {
		Position position = PositionTestUtils.newPosition("Ke1", "Rh1");
		position.makeMove(ChessUtils.toMoveFromAlgebraic("e1g1"));
		PositionTestUtils.assertSquares(position, "Kg1", "Rf1");
	}

	public void testWhiteCastleQueenside() {
		Position position = PositionTestUtils.newPosition("Ke1", "Ra1");
		position.makeMove(ChessUtils.toMoveFromAlgebraic("e1c1"));
		PositionTestUtils.assertSquares(position, "Kc1", "Rd1");
	}

	public void testBlackCastleKingside() {
		Position position = PositionTestUtils.newPosition("ke8", "rh8");
		position.setWhiteToMove(false);
		position.makeMove(ChessUtils.toMoveFromAlgebraic("e8g8"));
		PositionTestUtils.assertSquares(position, "kg8", "rf8");
	}

	public void testBlackCastleQueenside() {
		Position position = PositionTestUtils.newPosition("ke8", "ra8");
		position.setWhiteToMove(false);
		position.makeMove(ChessUtils.toMoveFromAlgebraic("e8c8"));
		PositionTestUtils.assertSquares(position, "kc8", "rd8");
	}

	public void testPhantomKings1() throws Exception {
		boolean fail = false;
		Position position = PositionTestUtils.newPosition("Ke1", "Rh1", "re8");
		try {
			position.makeMoveVerified(ChessUtils.toMoveFromAlgebraic("e1g1"));
		} catch (IllegalMoveException ex) {
			fail = true;
		}
		assertTrue(fail);
	}

	public void testPhantomKings2() throws Exception {
		boolean fail = false;
		Position position = PositionTestUtils.newPosition("Ke1", "Rh1", "rf8");
		try {
			position.makeMoveVerified(ChessUtils.toMoveFromAlgebraic("e1g1"));
		} catch (IllegalMoveException ex) {
			fail = true;
		}
		assertTrue(fail);
	}

	/**
	 * Doesn't really test for a phantom king, but for the real one.
	 */
	public void testPhantomKings3() throws Exception {
		Position position = PositionTestUtils.newPosition("Ke1", "Rh1", "rg8");
		boolean fail = false;
		try {
			position.makeMoveVerified(ChessUtils.toMoveFromAlgebraic("e1g1"));
		} catch (IllegalMoveException ex) {
			fail = true;
		}
		assertTrue(fail);
	}

	/**
	 * moving the king makes castle unavailable
	 */
	public void testCastleInvalidation1() throws Exception {
		Position position = PositionTestUtils.newPosition("Ke1", "Rh1", "ka8");
		position.makeMoveVerified(ChessUtils.toMoveFromAlgebraic("e1e2"));
		position.makeMoveVerified(ChessUtils.toMoveFromAlgebraic("a8a7"));
		position.makeMoveVerified(ChessUtils.toMoveFromAlgebraic("e2e1"));
		position.makeMoveVerified(ChessUtils.toMoveFromAlgebraic("a7a8"));
		boolean fail = false;
		try {
			position.makeMoveVerified(ChessUtils.toMoveFromAlgebraic("e1g1"));
		} catch (IllegalMoveException ex) {
			fail = true;
		}
		assertTrue(fail);
	}

	/**
	 * moving the rook makes castle unavailable
	 */
	public void testCastleInvalidation2() throws Exception {
		Position position = PositionTestUtils.newPosition("Ke1", "Rh1", "ka8");
		position.makeMoveVerified(ChessUtils.toMoveFromAlgebraic("h1h2"));
		position.makeMoveVerified(ChessUtils.toMoveFromAlgebraic("a8a7"));
		position.makeMoveVerified(ChessUtils.toMoveFromAlgebraic("h2h1"));
		position.makeMoveVerified(ChessUtils.toMoveFromAlgebraic("a7a8"));
		boolean fail = false;
		try {
			position.makeMoveVerified(ChessUtils.toMoveFromAlgebraic("e1g1"));
		} catch (IllegalMoveException ex) {
			fail = true;
		}
		assertTrue(fail);
	}

	public void testEnPassant1() throws Exception {
		Position position = PositionTestUtils.newPosition("Ke1", "Pe2", "ke8", "pf4");
		position.makeMoveVerified(ChessUtils.toMoveFromAlgebraic("e2e4"));
		position.makeMoveVerified(ChessUtils.toMoveFromAlgebraic("f4e3"));
		assertEquals(Piece.BLACK_PAWN, position.getPiece("e3"));
		assertEquals(Piece.EMPTY, position.getPiece("e4"));
	}

	/**
	 * en passant is not possible two moves later
	 */
	public void testEnPassant2() throws Exception {
		Position position = PositionTestUtils.newPosition("Ke1", "Pe2", "ke8", "pf4");
		position.makeMoveVerified(ChessUtils.toMoveFromAlgebraic("e2e4"));
		position.makeMoveVerified(ChessUtils.toMoveFromAlgebraic("e8f8"));
		position.makeMoveVerified(ChessUtils.toMoveFromAlgebraic("e1f1"));
		boolean fail = false;
		try {
			position.makeMoveVerified(ChessUtils.toMoveFromAlgebraic("f4e3"));
		} catch (IllegalMoveException ex) {
			fail = true;
		}
		assertTrue(fail);
	}

	public void testPromotion1() throws Exception {
		Position position = PositionTestUtils.newPosition("Ke1", "Pa7", "kh6");
		position.makeMoveVerified(ChessUtils.toMoveFromAlgebraic("a7a8R"));
		position.makeMoveVerified(ChessUtils.toMoveFromAlgebraic("h6h7"));
		position.makeMoveVerified(ChessUtils.toMoveFromAlgebraic("a8f8"));
	}

	public void testPromotion2() throws Exception {
		Position position = PositionTestUtils.newPosition("Ke1", "Ra1", "kh6", "pb2");
		position.setWhiteToMove(false);
		position.makeMoveVerified(ChessUtils.toMoveFromAlgebraic("b2a1Q"));
		position.makeMoveVerified(ChessUtils.toMoveFromAlgebraic("e1e2"));
		position.makeMoveVerified(ChessUtils.toMoveFromAlgebraic("a1h8"));
	}
}
