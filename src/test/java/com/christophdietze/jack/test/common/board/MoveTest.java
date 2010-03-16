package com.christophdietze.jack.test.common.board;

import com.christophdietze.jack.common.board.ChessUtils;
import com.christophdietze.jack.common.board.Position;
import com.christophdietze.jack.test.MyTestCase;

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
		Position position = PositionSetupUtils.newPosition("Ke1", "Rh1");
		position.makeMove(ChessUtils.fromAlgebraic("e1g1"));
		PositionSetupUtils.assertSquaresInPosition(position, "Kg1", "Rf1");
	}

	// public void testWhiteCastleQueenside() {
	// Position position = new Position();
	// PositionSetupUtils.newPosition(position, "Ke1", "Ra1");
	// position.makeMove(PositionSetupUtils.createMove("e1", "c1"));
	// PositionSetupUtils.assertSquares(position, "Kc1", "Rd1");
	// }
	//
	// public void testBlackCastleKingside() {
	// Position position = new Position();
	// position.setWhiteToMove(false);
	// PositionSetupUtils.newPosition(position, "ke8", "rh8");
	// position.makeMove(PositionSetupUtils.createMove("e8", "g8"));
	// PositionSetupUtils.assertSquares(position, "kg8", "rf8");
	// }
	//
	// public void testBlackCastleQueenside() {
	// Position position = new Position();
	// position.setWhiteToMove(false);
	// PositionSetupUtils.newPosition(position, "ke8", "ra8");
	// position.makeMove(PositionSetupUtils.createMove("e8", "c8"));
	// PositionSetupUtils.assertSquares(position, "kc8", "rd8");
	// }
	//
	// public void testPhantomKings1() throws Exception {
	// boolean fail = false;
	// Position position = new Position();
	// PositionSetupUtils.newPosition(position, "Ke1", "Rh1", "re8");
	// try {
	// position.makeMoveVerified(PositionSetupUtils.createMove("e1", "g1"));
	// } catch (IllegalMoveException ex) {
	// fail = true;
	// }
	// assertTrue(fail);
	// }
	//
	// public void testPhantomKings2() throws Exception {
	// boolean fail = false;
	// Position position = new Position();
	// PositionSetupUtils.newPosition(position, "Ke1", "Rh1", "rf8");
	// try {
	// position.makeMoveVerified(PositionSetupUtils.createMove("e1", "g1"));
	// } catch (IllegalMoveException ex) {
	// fail = true;
	// }
	// assertTrue(fail);
	// }
	//
	// /**
	// * Doesn't really test phantom kings, but the name fits so nicely in the pattern
	// */
	// public void testPhantomKings3() throws Exception {
	// Position position = new Position();
	// PositionSetupUtils.newPosition(position, "Ke1", "Rh1", "rg8");
	// boolean fail = false;
	// try {
	// position.makeMoveVerified(PositionSetupUtils.createMove("e1", "g1"));
	// } catch (IllegalMoveException ex) {
	// fail = true;
	// }
	// assertTrue(fail);
	// }
	//
	// /**
	// * moving the king makes castle unavailable
	// */
	// public void testCastleInvalidation1() throws Exception {
	// Position position = new Position();
	// PositionSetupUtils.newPosition(position, "Ke1", "Rh1", "ka8");
	// position.makeMoveVerified(PositionSetupUtils.createMove("e1", "e2"));
	// position.makeMoveVerified(PositionSetupUtils.createMove("a8", "a7"));
	// position.makeMoveVerified(PositionSetupUtils.createMove("e2", "e1"));
	// position.makeMoveVerified(PositionSetupUtils.createMove("a7", "a8"));
	// boolean fail = false;
	// try {
	// position.makeMoveVerified(PositionSetupUtils.createMove("e1", "g1"));
	// } catch (IllegalMoveException ex) {
	// fail = true;
	// }
	// assertTrue(fail);
	// }
	//
	// /**
	// * moving the rook makes castle unavaible
	// */
	// public void testCastleInvalidation2() throws Exception {
	// Position position = new Position();
	// PositionSetupUtils.newPosition(position, "Ke1", "Rh1", "ka8");
	// position.makeMoveVerified(PositionSetupUtils.createMove("h1", "h2"));
	// position.makeMoveVerified(PositionSetupUtils.createMove("a8", "a7"));
	// position.makeMoveVerified(PositionSetupUtils.createMove("h2", "h1"));
	// position.makeMoveVerified(PositionSetupUtils.createMove("a7", "a8"));
	// boolean fail = false;
	// try {
	// position.makeMoveVerified(PositionSetupUtils.createMove("e1", "g1"));
	// } catch (IllegalMoveException ex) {
	// fail = true;
	// }
	// assertTrue(fail);
	// }
	//
	// public void testEnPassant1() throws Exception {
	// Position position = new Position();
	// PositionSetupUtils.newPosition(position, "Ke1", "Pe2", "ke8", "pf4");
	// position.makeMoveVerified(PositionSetupUtils.createMove("e2", "e4"));
	// position.makeMoveVerified(PositionSetupUtils.createMove("f4", "e3"));
	//
	// log.info(position.toDiagramString());
	// assertEquals(Piece.BLACK_PAWN, position.getPiece("e3"));
	// assertEquals(Piece.EMPTY, position.getPiece("e4"));
	// }
	//
	// /**
	// * en passant is not possible two moves later
	// */
	// public void testEnPassant2() throws Exception {
	// Position position = new Position();
	// PositionSetupUtils.newPosition(position, "Ke1", "Pe2", "ke8", "pf4");
	// position.makeMoveVerified(PositionSetupUtils.createMove("e2", "e4"));
	// position.makeMoveVerified(PositionSetupUtils.createMove("e8", "f8"));
	// position.makeMoveVerified(PositionSetupUtils.createMove("e1", "f1"));
	// boolean fail = false;
	// try {
	// position.makeMoveVerified(PositionSetupUtils.createMove("f4", "e3"));
	// } catch (IllegalMoveException ex) {
	// fail = true;
	// }
	// assertTrue(fail);
	// }
	//
	// public void testPromotion1() throws Exception {
	// Position position = new Position();
	// PositionSetupUtils.newPosition(position, "Ke1", "Pa7", "kh6");
	// position.makeMoveVerified(PositionSetupUtils.createMove("a7", "a8").setPromotionPiece(Piece.ROOK));
	// position.makeMoveVerified(PositionSetupUtils.createMove("h6", "h7"));
	// position.makeMoveVerified(PositionSetupUtils.createMove("a8", "f8"));
	// }
	//
	// public void testPromotion2() throws Exception {
	// Position position = new Position();
	// PositionSetupUtils.newPosition(position, "Ke1", "Ra1", "kh6", "pb2");
	// position.setWhiteToMove(false);
	// position.makeMoveVerified(PositionSetupUtils.createMove("b2", "a1").setPromotionPiece(Piece.QUEEN));
	// position.makeMoveVerified(PositionSetupUtils.createMove("e1", "e2"));
	// position.makeMoveVerified(PositionSetupUtils.createMove("a1", "h8"));
	// }
}
