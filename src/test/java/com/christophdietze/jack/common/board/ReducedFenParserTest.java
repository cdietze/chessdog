package com.christophdietze.jack.common.board;

import junit.framework.TestCase;

import com.christophdietze.jack.common.pgn.ReducedFenParser;
import com.christophdietze.jack.common.pgn.ReducedFenWriter;

public class ReducedFenParserTest extends TestCase {

	public void test1() throws Exception {
		Position position = new Position();
		position.setupStartingPosition();
		ReducedFenWriter writer = new ReducedFenWriter();
		String result = writer.write(position);
		// log.info("position: '" + result + "'");

		ReducedFenParser parser = new ReducedFenParser();
		parser.parse(result);

		assertTrue(PositionTestUtils.arePositionsEqual(position, parser.getPosition()));
	}
}
