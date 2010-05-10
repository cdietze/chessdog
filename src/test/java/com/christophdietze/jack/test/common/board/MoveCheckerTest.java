package com.christophdietze.jack.test.common.board;

import static com.christophdietze.jack.test.common.board.PositionTestHelper.newPosition;
import junit.framework.TestCase;

import com.christophdietze.jack.common.board.MoveChecker;
import com.christophdietze.jack.common.board.Position;
import com.christophdietze.jack.common.board.PositionUtils;
import com.christophdietze.jack.common.board.PositionUtils.GameState;

public class MoveCheckerTest extends TestCase {

	public void test1() {
		Position position = Position.STARTING_POSITION;
		assertTrue(MoveChecker.hasLegalMove(position));
		assertEquals(GameState.ACTIVE, PositionUtils.getGameState(position));
	}

	public void testMate1() {
		Position position = newPosition("Ka1", "kb3", "qa2").build();
		assertFalse(MoveChecker.hasLegalMove(position));
		assertEquals(GameState.MATE, PositionUtils.getGameState(position));
	}

	public void testStaleMate1() {
		Position position = newPosition("Ka1", "kb3", "qc2").build();
		assertFalse(MoveChecker.hasLegalMove(position));
		assertEquals(GameState.STALEMATE, PositionUtils.getGameState(position));
	}
}
