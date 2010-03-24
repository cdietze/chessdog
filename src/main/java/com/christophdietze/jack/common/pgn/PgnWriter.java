package com.christophdietze.jack.common.pgn;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.christophdietze.jack.common.board.Game;
import com.christophdietze.jack.common.board.GameMetaInfo;
import com.christophdietze.jack.common.board.Move;
import com.christophdietze.jack.common.board.MoveNode;
import com.christophdietze.jack.common.board.Position;
import com.christophdietze.jack.common.board.PositionUtils;
import com.christophdietze.jack.common.board.SanWriter;
import com.christophdietze.jack.common.board.SanWritingException;
import com.christophdietze.jack.common.board.GameMetaInfo.TagPair;

public class PgnWriter {

	private static final int MAX_LINE_LENGTH = 80;

	private Game game;
	private Position currentPosition;

	private StringBuilder sb;
	int curLineLength = 0;

	public String write(Game game) throws PgnWritingException {
		assert this.game == null;
		this.game = game;
		try {
			sb = new StringBuilder();
			writeTagPairSection();
			writeMoveTextSection();
			return sb.toString();
		} finally {
			this.game = null;
			currentPosition = null;
			sb = null;
		}
	}

	private void writeTagPairSection() {
		GameMetaInfo metaInfo = game.getMetaInfo();
		writeTagPair(PgnStandardTagName.Event, getTag(PgnStandardTagName.Event), "?");
		writeTagPair(PgnStandardTagName.Site, getTag(PgnStandardTagName.Site), "?");
		writeTagPair(PgnStandardTagName.Date, getTag(PgnStandardTagName.Date), "????.??.??");
		writeTagPair(PgnStandardTagName.Round, getTag(PgnStandardTagName.Round), "?");

		String whiteName = metaInfo.getWhitePlayerName() == null ? "?" : metaInfo.getWhitePlayerName();
		writeTagPair(PgnStandardTagName.White, whiteName);
		String blackName = metaInfo.getBlackPlayerName() == null ? "?" : metaInfo.getBlackPlayerName();
		writeTagPair(PgnStandardTagName.Black, blackName);

		writeTagPair(PgnStandardTagName.Result, game.getGameResult().getSymbol());

		List<GameMetaInfo.TagPair> additionalTagPairs = getAdditionalNonStandardTagPairs();

		Position initialPosition = game.getInitialPosition();
		if (initialPosition != null && !initialPosition.equals(Position.STARTING_POSITION)) {
			FenWriter fenWriter = new FenWriter();
			String fenString = fenWriter.write(initialPosition);
			additionalTagPairs.add(new TagPair("FEN", fenString));
			additionalTagPairs.add(new TagPair("SetUp", "1"));
			currentPosition = initialPosition;
		} else {
			currentPosition = Position.STARTING_POSITION;
		}

		Collections.sort(additionalTagPairs, new Comparator<TagPair>() {
			public int compare(TagPair o1, TagPair o2) {
				return o1.getName().compareTo(o2.getName());
			}
		});
		for (TagPair tagPair : additionalTagPairs) {
			writeTagPair(tagPair.getName(), tagPair.getValue());
		}

		sb.append("\n");
	}

	private void writeTagPair(PgnStandardTagName tagName, String tagValue, String defaultValue) {
		if (tagValue != null) {
			writeTagPair(tagName, tagValue);
		} else {
			writeTagPair(tagName, defaultValue);
		}
	}

	private void writeTagPair(PgnStandardTagName tagName, String tagValue) {
		writeTagPair(tagName.name(), tagValue);
	}

	private void writeTagPair(String tagName, String tagValue) {
		sb.append("[");
		sb.append(tagName);
		sb.append(" \"");
		sb.append(tagValue);
		sb.append("\"]\n");
	}

	private void writeMoveTextSection() throws PgnWritingException {
		if (!currentPosition.isWhiteToMove()) {
			StringBuilder sb2 = new StringBuilder();
			sb2.append(currentPosition.getFullmoveNumber());
			sb2.append("...");
			appendWord(sb2.toString());
		}

		// TODO handle variations
		List<Move> moves = new ArrayList<Move>();
		MoveNode node = game.getInitialMoveNode();
		while (node.hasNext()) {
			node = node.getNext();
			moves.add(node.getMove());
		}

		int moveCounter = -1;
		for (Move move : moves) {
			StringBuilder sb2 = new StringBuilder();
			++moveCounter;
			// if (moveCounter != 0) {
			// sb.append(" ");
			// }
			if (currentPosition.isWhiteToMove()) {
				sb2.append(currentPosition.getFullmoveNumber());
				sb2.append(". ");
			}
			sb2.append(getMoveString(move));
			appendWord(sb2.toString());
		}
		appendWord(game.getGameResult().getSymbol());
	}

	private String getMoveString(Move move) throws PgnWritingException {
		try {
			SanWriter sanWriter = new SanWriter();
			String moveString = sanWriter.write(currentPosition, move);
			currentPosition = PositionUtils.makeMove(currentPosition, move);
			return moveString;
		} catch (SanWritingException ex) {
			throw new PgnWritingException("Error writing SAN for " + move + " of position " + currentPosition);
		}
	}

	private void appendWord(String text) {
		if (curLineLength + text.length() + 1 > MAX_LINE_LENGTH) {
			sb.append("\n");
			sb.append(text);
			curLineLength = text.length();
		} else {
			if (curLineLength != 0) {
				sb.append(" ");
				curLineLength += 1;
			}
			sb.append(text);
			curLineLength += text.length();
		}
	}

	private String getTag(PgnStandardTagName tagName) {
		return game.getMetaInfo().getTag(tagName.name());
	}

	/**
	 * @return An unsorted list of all non-standard tag pairs.
	 */
	private List<TagPair> getAdditionalNonStandardTagPairs() {
		List<TagPair> result = new ArrayList<TagPair>();
		Set<String> standardTags = new HashSet<String>();
		for (PgnStandardTagName tagName : PgnStandardTagName.values()) {
			standardTags.add(tagName.name());
		}
		for (Map.Entry<String, String> entry : game.getMetaInfo().getTags().entrySet()) {
			if (standardTags.contains(entry.getKey())) {
				continue;
			}
			result.add(new TagPair(entry));
		}
		return result;
	}
}
