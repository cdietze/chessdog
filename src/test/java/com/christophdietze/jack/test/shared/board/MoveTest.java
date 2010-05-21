package com.christophdietze.jack.test.shared.board;

import static com.christophdietze.jack.shared.board.PositionUtils.makeMove;
import static com.christophdietze.jack.shared.board.PositionUtils.makeMoveVerified;
import static com.christophdietze.jack.test.shared.board.PositionTestHelper.newPosition;
import junit.framework.TestCase;

import com.christophdietze.jack.shared.board.IllegalMoveException;
import com.christophdietze.jack.shared.board.Piece;
import com.christophdietze.jack.shared.board.Position;

public class MoveTest extends TestCase {

	public void testWhiteCastleKingside() {
		Position position = newPosition("Ke1", "Rh1").build();
		position = makeMove(position, "e1g1");
		PositionTestHelper.assertSquares(position, "Kg1", "Rf1");
	}

	public void testWhiteCastleQueenside() {
		Position position = newPosition("Ke1", "Ra1").build();
		position = makeMove(position, "e1c1");
		PositionTestHelper.assertSquares(position, "Kc1", "Rd1");
	}

	public void testBlackCastleKingside() {
		Position position = newPosition("ke8", "rh8").whiteToMove(false).build();
		position = makeMove(position, "e8g8");
		PositionTestHelper.assertSquares(position, "kg8", "rf8");
	}

	public void testBlackCastleQueenside() {
		Position position = newPosition("ke8", "ra8").whiteToMove(false).build();
		position = makeMove(position, "e8c8");
		PositionTestHelper.assertSquares(position, "kc8", "rd8");
	}

	public void testPhantomKings1() throws Exception {
		boolean fail = false;
		Position position = newPosition("Ke1", "Rh1", "re8").build();
		try {
			position = makeMoveVerified(position, "e1g1");
		} catch (IllegalMoveException ex) {
			fail = true;
		}
		assertTrue(fail);
	}

	public void testPhantomKings2() throws Exception {
		boolean fail = false;
		Position position = newPosition("Ke1", "Rh1", "rf8").build();
		try {
			position = makeMoveVerified(position, "e1g1");
		} catch (IllegalMoveException ex) {
			fail = true;
		}
		assertTrue(fail);
	}

	/**
	 * Doesn't really test for a phantom king, but for the real one.
	 */
	public void testPhantomKings3() throws Exception {
		Position position = newPosition("Ke1", "Rh1", "rg8").build();
		boolean fail = false;
		try {
			position = makeMoveVerified(position, "e1g1");
		} catch (IllegalMoveException ex) {
			fail = true;
		}
		assertTrue(fail);
	}

	/**
	 * moving the king makes castle unavailable
	 */
	public void testCastle1() throws Exception {
		Position position = newPosition("Ke1", "Rh1", "ka8").build();
		position = makeMoveVerified(position, "e1e2");
		position = makeMoveVerified(position, "a8a7");
		position = makeMoveVerified(position, "e2e1");
		position = makeMoveVerified(position, "a7a8");
		boolean fail = false;
		try {
			position = makeMoveVerified(position, "e1g1");
		} catch (IllegalMoveException ex) {
			fail = true;
		}
		assertTrue(fail);
	}

	/**
	 * moving the rook makes castle unavailable
	 */
	public void testCastle2() throws Exception {
		Position position = newPosition("Ke1", "Rh1", "ka8").build();
		position = makeMoveVerified(position, "h1h2");
		position = makeMoveVerified(position, "a8a7");
		position = makeMoveVerified(position, "h2h1");
		position = makeMoveVerified(position, "a7a8");
		boolean fail = false;
		try {
			position = makeMoveVerified(position, "e1g1");
		} catch (IllegalMoveException ex) {
			fail = true;
		}
		assertTrue(fail);
	}

	/**
	 * assert that only the king can castle
	 */
	public void testCastle3() throws Exception {
		Position position = newPosition("Kd1", "Qe1", "Rh1", "ka8").build();
		position = makeMoveVerified(position, "e1g1");
		PositionTestHelper.assertSquares(position, "Kd1", "e1", "f1", "Qg1", "Rh1");
	}

	public void testEnPassant1() throws Exception {
		Position position = newPosition("Ke1", "Pe2", "ke8", "pf4").build();
		position = makeMoveVerified(position, "e2e4");
		position = makeMoveVerified(position, "f4e3");
		assertEquals(Piece.BLACK_PAWN, position.getPiece("e3"));
		assertEquals(Piece.EMPTY, position.getPiece("e4"));
	}

	/**
	 * en passant is not possible two moves later
	 */
	public void testEnPassant2() throws Exception {
		Position position = newPosition("Ke1", "Pe2", "ke8", "pf4").build();
		position = makeMoveVerified(position, "e2e4");
		position = makeMoveVerified(position, "e8f8");
		position = makeMoveVerified(position, "e1f1");
		boolean fail = false;
		try {
			position = makeMoveVerified(position, "f4e3");
		} catch (IllegalMoveException ex) {
			fail = true;
		}
		assertTrue(fail);
	}

	/**
	 * pieces can move over en passant pawns.
	 */
	public void testEnPassant3() throws Exception {
		Position position = newPosition("Ke1", "Pe2", "ke8", "rf3").build();
		position = makeMoveVerified(position, "e2e4");
		position = makeMoveVerified(position, "f3a3");
	}

	public void testPromotion1() throws Exception {
		Position position = newPosition("Ke1", "Pa7", "kh6").build();
		position = makeMoveVerified(position, "a7a8R");
		position = makeMoveVerified(position, "h6h7");
		position = makeMoveVerified(position, "a8f8");
	}

	public void testPromotion2() throws Exception {
		Position position = newPosition("Ke1", "Ra1", "kh6", "pb2").whiteToMove(false).build();
		position = makeMoveVerified(position, "b2a1Q");
		position = makeMoveVerified(position, "e1e2");
		position = makeMoveVerified(position, "a1h8");
	}
}
