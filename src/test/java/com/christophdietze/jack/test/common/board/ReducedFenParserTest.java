package com.christophdietze.jack.test.common.board;

import com.christophdietze.jack.common.board.Position;
import com.christophdietze.jack.common.pgn.ReducedFenParser;
import com.christophdietze.jack.common.pgn.ReducedFenWriter;
import com.christophdietze.jack.test.MyTestCase;

public class ReducedFenParserTest extends MyTestCase {

	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void test1() throws Exception {
		Position position = new Position();
		position.setupStartingPosition();
		ReducedFenWriter writer = new ReducedFenWriter();
		String result = writer.write(position);
		log.info("position: '" + result + "'");

		ReducedFenParser parser = new ReducedFenParser();
		parser.parse(result);

		assertTrue(PositionSetupUtils.arePositionsEqual(position, parser.getPosition()));
	}
}
