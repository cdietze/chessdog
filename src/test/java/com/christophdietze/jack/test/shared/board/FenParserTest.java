package com.christophdietze.jack.test.shared.board;

import junit.framework.TestCase;

import org.junit.Test;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import com.christophdietze.jack.shared.pgn.FenParser;
import com.christophdietze.jack.shared.pgn.FenWriter;

public class FenParserTest extends TestCase {

	private static String[] testFenPositions = { "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1",
			"8/8/8/2k5/4K3/8/8/8 w KQkq - 0 1", "8/8/8/2k5/4K3/8/8/8 w - - 0 1", "k7/8/8/pP6/8/8/8/K7 w - a6 0 1",
			"k7/8/8/8/pP6/8/8/K7 b - b3 0 1" };

	private Logger log = LoggerFactory.getLogger(this.getClass());

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
