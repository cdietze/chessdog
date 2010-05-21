package com.christophdietze.jack.test.shared.board;

import junit.framework.TestCase;

import com.christophdietze.jack.shared.board.Position;
import com.christophdietze.jack.shared.pgn.ReducedFenParser;
import com.christophdietze.jack.shared.pgn.ReducedFenWriter;

public class ReducedFenParserTest extends TestCase {

	public void test1() throws Exception {
		Position position = Position.STARTING_POSITION;
		ReducedFenWriter writer = new ReducedFenWriter();
		String result = writer.write(position);
		// log.info("position: '" + result + "'");

		ReducedFenParser parser = new ReducedFenParser();
		parser.parse(result);

		assertTrue(PositionTestHelper.arePositionsEqual(position, parser.getPosition()));
	}
}
