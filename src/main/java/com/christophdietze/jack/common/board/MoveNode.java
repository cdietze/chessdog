package com.christophdietze.jack.common.board;

import java.util.Collections;
import java.util.List;

public class MoveNode {

	/**
	 * The move that was most recently done before this position was reached or
	 * null for initial positions.
	 */
	private Move move;
	private int ply;
	private Position position;
	private MoveNode prev;
	private MoveNode next;
	private List<MoveNode> variations = Collections.emptyList();
	private String sanNotation;

	public static MoveNode createInitialNode(Position position) {
		MoveNode node = new MoveNode();
		node.position = position;
		node.ply = position.getPly() - 1;
		node.prev = null;
		return node;
	}

	public static MoveNode createNextNodeVerified(MoveNode currentNode,
			Move move) throws IllegalMoveException {
		Position newPosition = currentNode.position.copy();
		newPosition.makeMoveVerified(move);

		MoveNode newNode = new MoveNode();
		newNode.move = move;
		newNode.ply = currentNode.ply + 1;
		if (currentNode.next != null) {
			// TODO create a variant here
		}
		currentNode.next = newNode;
		newNode.prev = currentNode;
		newNode.position = newPosition;

		try {
			SanWriter sanWriter = new SanWriter();
			newNode.sanNotation = sanWriter.write(currentNode.position, move);
		} catch (SanWritingException ex) {
			throw new RuntimeException(ex);
		}

		return newNode;
	}

	private MoveNode() {
	}

	public Move getMove() {
		return move;
	}

	public int getPly() {
		return ply;
	}

	public Position getPosition() {
		return position;
	}

	public MoveNode getPrev() {
		return prev;
	}

	public MoveNode getNext() {
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

	public List<MoveNode> getVariations() {
		return variations;
	}

	public String getSanNotation() {
		return sanNotation;
	}
}
