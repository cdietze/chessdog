package com.christophdietze.jack.common.board;

import com.christophdietze.jack.client.event.GameUpdatedEvent;
import com.christophdietze.jack.client.util.GlobalEventBus;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class Game {

	private GlobalEventBus eventBus;

	private MoveNode2 initialMoveNode;
	private MoveNode2 currentMoveNode;

	private GameMetaInfo metaInfo = new GameMetaInfo();
	private GameResult gameResult;

	@Inject
	public Game(GlobalEventBus eventBus) {
		this.eventBus = eventBus;
		clearAttributes();
		Position2 initialPosition = Position2.STARTING_POSITION;
		initialMoveNode = MoveNode2.createInitialNode(initialPosition);
		currentMoveNode = initialMoveNode;
		initListeners();
	}

	private void clearAttributes() {
		metaInfo.clear();
		gameResult = GameResult.UNDECIDED;
	}

	public MoveNode2 getInitialMoveNode() {
		return initialMoveNode;
	}

	public MoveNode2 getCurrentMoveNode() {
		return currentMoveNode;
	}

	public Position2 getPosition() {
		return currentMoveNode.getPosition();
	}

	public Position2 getInitialPosition() {
		return initialMoveNode.getPosition();
	}

	public boolean hasPrevMove() {
		return currentMoveNode.hasPrev();
	}

	public boolean hasNextMove() {
		return currentMoveNode.hasNext();
	}

	public GameMetaInfo getMetaInfo() {
		return metaInfo;
	}

	public GameResult getGameResult() {
		assert gameResult != null;
		return gameResult;
	}

	public void clear() {
		clearAttributes();
		clearPosition();
	}

	public void makeMoveVerified(Move move) throws IllegalMoveException {
		MoveNode2 newNode = MoveNode2.createNextNodeVerified(currentMoveNode, move);
		currentMoveNode = newNode;
		// TODO set GameResult when checkmate / stalemate occurs
		gameResult = GameResult.UNDECIDED;
		firePositionChanged();
	}

	/**
	 * This method is used to import moves.<br>
	 * It does not verify the move, does not fire events.
	 */
	public void addMove(Move move) {
		MoveNode2 newNode;
		try {
			newNode = MoveNode2.createNextNodeVerified(currentMoveNode, move);
		} catch (IllegalMoveException e) {
			throw new AssertionError();
		}
		currentMoveNode = newNode;
	}

	/**
	 * move must be a legal move
	 */
	// public void makeMove(Move move) {
	// currentMoveListNode = MoveListNode.createNext(position, move,
	// currentMoveListNode);
	// position.makeMove(move);
	// firePositionChanged();
	// }
	public void setInitialPosition(Position2 initialPosition) {
		initialMoveNode = MoveNode2.createInitialNode(initialPosition);
		currentMoveNode = initialMoveNode;
		gameResult = GameResult.UNDECIDED;
		firePositionChanged();
	}

	// public void setCurrentAsInitialPosition() {
	// position.setFullmoveNumber(1);
	// this.initialPosition = position.copy();
	// currentMoveListNode = MoveListNode.createRoot(position);
	// firePositionChanged();
	// }

	public void setupStartingPosition() {
		clearAttributes();
		setInitialPosition(Position2.STARTING_POSITION);
	}

	public void clearPosition() {
		clearAttributes();
		setInitialPosition(Position2.EMPTY_POSITION);
	}

	public void gotoPrevMove() {
		assert hasPrevMove();
		currentMoveNode = currentMoveNode.getPrev();
		firePositionChanged();
	}

	public void gotoNextMove() {
		assert hasNextMove();
		currentMoveNode = currentMoveNode.getNext();
		firePositionChanged();
	}

	public void gotoFirstPosition() {
		currentMoveNode = initialMoveNode;
		firePositionChanged();
	}

	/**
	 * Goes to the last position of the current thread.
	 */
	public void gotoLastPosition() {
		while (currentMoveNode.hasNext()) {
			currentMoveNode = currentMoveNode.getNext();
		}
		firePositionChanged();
	}

	public void gotoPly(int ply) {
		if (ply < initialMoveNode.getPly()) {
			throw new RuntimeException("ply too low");
		}

		while (ply < currentMoveNode.getPly()) {
			currentMoveNode = currentMoveNode.getPrev();
		}
		while (ply > currentMoveNode.getPly()) {
			currentMoveNode = currentMoveNode.getNext();
		}
		firePositionChanged();
	}

	public void setGameResult(GameResult gameResult) {
		assert gameResult != null;
		this.gameResult = gameResult;
		firePositionChanged();
	}

	//
	// public void addListener(GameListener listener) {
	// listeners.add(listener);
	// }

	private void initListeners() {
		metaInfo.addListener(new GameMetaInfoListener() {
			@Override
			public void onMetaInfoChanged() {
				firePositionChanged();
			}
		});
	}

	private void firePositionChanged() {
		eventBus.fireEvent(new GameUpdatedEvent());
		// for (GameListener listener : listeners) {
		// listener.onPositionChanged();
		// }
	}

	// private void fireMetaInfoChanged() {
	// for (GameListener listener : listeners) {
	// listener.onMetaInfoChanged();
	// }
	// }

	// public HandlerRegistration addGameUpdatedEventHandler(
	// GameUpdatedEventHandler handler) {
	// return handlerManager.addHandler(GameUpdatedEvent.TYPE, handler);
	// }
}
