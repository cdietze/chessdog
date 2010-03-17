package com.christophdietze.jack.test.common.board;

import org.junit.Test;

import com.christophdietze.jack.common.pgn.FenParser;
import com.christophdietze.jack.common.pgn.FenWriter;
import com.christophdietze.jack.test.MyTestCase;

public class FenParserTest extends MyTestCase {

	private static String[] testFenPositions = { "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1",
			"8/8/8/2k5/4K3/8/8/8 w KQkq - 0 1", "8/8/8/2k5/4K3/8/8/8 w - - 0 1", "k7/8/8/pP6/8/8/8/K7 w - a6 0 1",
			"k7/8/8/8/pP6/8/8/K7 b - b3 0 1" };

	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	@Test
	public void test1() throws Exception {
		int counter = -1;
		try {
			for (String inputFenPos : testFenPositions) {
				++counter;
				FenParser parser = new FenParser();
				parser.parse(inputFenPos);
				// log.info(parser.getPosition().toDiagramString());

				FenWriter writer = new FenWriter();
				String outputFenPos = writer.write(parser.getPosition());
				assertEquals(inputFenPos, outputFenPos);
			}
		} catch (Exception ex) {
			log.warn("error while parsing test string number " + counter);
			throw ex;
		}
	}

	@Test
	public void test2() {
	}
}
