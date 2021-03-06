package com.christophdietze.jack.client.view;

import java.util.List;

import com.allen_sauer.gwt.log.client.Log;
import com.christophdietze.jack.client.presenter.BoardPresenter;
import com.christophdietze.jack.client.resources.MyClientBundle;
import com.christophdietze.jack.client.resources.MyCss;
import com.christophdietze.jack.client.resources.PieceImageBundle;
import com.christophdietze.jack.client.resources.PieceImageProvider;
import com.christophdietze.jack.shared.board.ChessUtils;
import com.christophdietze.jack.shared.board.Move;
import com.christophdietze.jack.shared.board.Piece;
import com.christophdietze.jack.shared.board.PieceType;
import com.google.common.collect.Lists;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class BoardPanel extends Composite implements BoardPresenter.View {

	private static MyCss CSS = MyClientBundle.CSS;

	static {
		CSS.ensureInjected();
	}

	private BoardPresenter model;
	private FlowPanel rootPanel = new FlowPanel();
	private final BoardSquare[] squares = new BoardSquare[64];
	private List<Label> fileLabels = Lists.newArrayList(); // a-h
	private List<Label> rankLabels = Lists.newArrayList(); // 1-8
	private Image moveFromImage = new Image(MyClientBundle.INSTANCE.moveMarker());
	private Image moveToImage = new Image(MyClientBundle.INSTANCE.moveMarker());

	@Inject
	public BoardPanel(BoardPresenter model) {
		super.initWidget(rootPanel);
		this.model = model;
		initGrid();
		rootPanel.addStyleName(CSS.boardContainer());
		moveFromImage.addStyleName(CSS.moveMarker());
		moveFromImage.setVisible(false);
		rootPanel.add(moveFromImage);
		moveToImage.addStyleName(CSS.moveMarker());
		moveToImage.setVisible(false);
		rootPanel.add(moveToImage);
		update();
		model.bindView(this);
		Log.debug("BoardView initialized");
	}

	private void initGrid() {
		for (int rank = 7; rank >= 0; --rank) {
			for (int file = 0; file < 8; ++file) {
				int index = ChessUtils.toIndex(file, rank);
				boolean isWhite = ChessUtils.isWhite(index);

				if (file == 0) {
					Label rankLabel = new Label(Character.toString((char) ('1' + rank)));
					rankLabels.add(rankLabel);
					rankLabel.addStyleName(CSS.rankLabel());
					rootPanel.add(rankLabel);
				}

				BoardSquare square = new BoardSquare(new Image(PieceImageBundle.INSTANCE.empty()));
				square.getImage().setStylePrimaryName(CSS.squareImage());
				squares[index] = square;
				square.setStylePrimaryName(isWhite ? CSS.whiteSquare() : CSS.blackSquare());
				rootPanel.add(squares[index]);
			}
		}
		Label fileLabelPlaceholder = new Label();
		fileLabelPlaceholder.addStyleName(CSS.fileLabelPlaceHolder());
		rootPanel.add(fileLabelPlaceholder);
		for (int file = 0; file < 8; ++file) {
			Label fileLabel = new Label(Character.toString((char) ('a' + file)));
			fileLabels.add(fileLabel);
			fileLabel.addStyleName(CSS.fileLabel());
			rootPanel.add(fileLabel);
		}
	}

	@Override
	public void update() {
		for (int i = 0; i < 8; ++i) {
			int file = model.getGame().isWhiteAtBottom() ? i : 7 - i;
			int rank = model.getGame().isWhiteAtBottom() ? 7 - i : i;
			fileLabels.get(i).setText(Character.toString((char) ('a' + file)));
			rankLabels.get(i).setText(Character.toString((char) ('1' + rank)));
		}
		for (int index = 0; index < 64; ++index) {
			int viewIndex = model.getGame().isWhiteAtBottom() ? index : 63 - index;
			Piece square = model.getGame().getPosition().getPiece(index);
			squares[viewIndex].getImage().setResource(PieceImageProvider.getImageResource(square));
		}

		Move lastMove = model.getGame().getCurrentMoveNode().getMove();
		if (lastMove == null) {
			moveFromImage.setVisible(false);
			moveToImage.setVisible(false);
		} else {
			int fromIndex = lastMove.getFrom();
			int toIndex = lastMove.getTo();
			if (!model.getGame().isWhiteAtBottom()) {
				fromIndex = 63 - fromIndex;
				toIndex = 63 - toIndex;
			}
			Widget fromSquare = squares[fromIndex];
			moveFromImage.getElement().getStyle()
					.setLeft(fromSquare.getAbsoluteLeft() - rootPanel.getAbsoluteLeft(), Unit.PX);
			moveFromImage.getElement().getStyle()
					.setTop(fromSquare.getAbsoluteTop() - rootPanel.getAbsoluteTop(), Unit.PX);
			moveFromImage.setVisible(true);
			Widget toSquare = squares[toIndex];
			moveToImage.getElement().getStyle().setLeft(toSquare.getAbsoluteLeft() - rootPanel.getAbsoluteLeft(), Unit.PX);
			moveToImage.getElement().getStyle().setTop(toSquare.getAbsoluteTop() - rootPanel.getAbsoluteTop(), Unit.PX);
			moveToImage.setVisible(true);
		}
	}

	@Override
	public void showPromotionPawn(int from, int to) {
		int rank = ChessUtils.toRank(to);
		assert rank == 0 || rank == 7;
		Piece pawn = Piece.getFromColorAndPieceType(rank == 7, PieceType.PAWN);
		squares[to].getImage().setResource(PieceImageProvider.getImageResource(pawn));
		squares[from].getImage().setResource(PieceImageBundle.INSTANCE.empty());
	}

	public BoardSquare[] getSquares() {
		return squares;
	}

	public void addWidget(Widget widget) {
		rootPanel.add(widget);
	}
}
