package com.christophdietze.jack.common.board;

import java.util.Collections;
import java.util.List;


/**
 * TODO make immutable
 */
public class MoveNode2 {

	/**
	 * The move that was most recently done before this position was reached or null for initial positions.
	 */
	private Move move;
	private int ply;
	private Position2 position;
	private MoveNode2 prev;
	private MoveNode2 next;
	private List<MoveNode2> variations = Collections.emptyList();
	private String sanNotation;

	public static MoveNode2 createInitialNode(Position2 position) {
		MoveNode2 node = new MoveNode2();
		node.position = position;
		node.ply = position.getPly() - 1;
		node.prev = null;
		return node;
	}

	public static MoveNode2 createNextNodeVerified(MoveNode2 currentNode, Move move) throws IllegalMoveException {
		Position2 newPosition = Position2Utils.makeMoveVerified(currentNode.position, move);

		MoveNode2 newNode = new MoveNode2();
		newNode.move = move;
		newNode.ply = currentNode.ply + 1;
		if (currentNode.next != null) {
			// TODO create a variant here
		}
		currentNode.next = newNode;
		newNode.prev = currentNode;
		newNode.position = newPosition;

		try {
			SanWriter2 sanWriter = new SanWriter2();
			newNode.sanNotation = sanWriter.write(currentNode.position, move);
		} catch (SanWritingException ex) {
			throw new RuntimeException(ex);
		}

		return newNode;
	}

	private MoveNode2() {
	}

	public Move getMove() {
		return move;
	}

	public int getPly() {
		return ply;
	}

	public Position2 getPosition() {
		return position;
	}

	public MoveNode2 getPrev() {
		return prev;
	}

	public MoveNode2 getNext() {
		return next;
	}

	public boolean hasPrev() {
		return prev != null;
	}

	public boolean hasNext() {
		return next != null;
	}

	public boolean isInitialNode() {
		return this == prev;
	}

	public boolean hasVariations() {
		return !variations.isEmpty();
	}

	public List<MoveNode2> getVariations() {
		return variations;
	}

	public String getSanNotation() {
		return sanNotation;
	}
}
