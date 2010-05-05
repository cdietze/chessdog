package com.christophdietze.jack.client.view;

import java.util.List;

import com.allen_sauer.gwt.log.client.Log;
import com.christophdietze.jack.client.presenter.BoardPresenter;
import com.christophdietze.jack.client.resources.MyClientBundle;
import com.christophdietze.jack.client.resources.MyCss;
import com.christophdietze.jack.client.resources.PieceImageBundle;
import com.christophdietze.jack.client.resources.PieceImageProvider;
import com.christophdietze.jack.common.board.ChessUtils;
import com.christophdietze.jack.common.board.Piece;
import com.christophdietze.jack.common.board.PieceType;
import com.google.common.collect.Lists;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class BoardView extends Composite implements BoardPresenter.View {

	private static MyCss CSS = MyClientBundle.CSS;

	static {
		CSS.ensureInjected();
	}

	private BoardPresenter model;
	private FlowPanel rootPanel = new FlowPanel();
	private final Image[] squareImages = new Image[64];
	private List<Label> fileLabels = Lists.newArrayList(); // a-h
	private List<Label> rankLabels = Lists.newArrayList(); // 1-8

	@Inject
	public BoardView(BoardPresenter model) {
		super.initWidget(rootPanel);
		this.model = model;
		rootPanel.addStyleName(CSS.boardContainer());
		initGrid();

		update();
		model.setView(this);
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
					rankLabel.addStyleName(CSS.rankLabel2());
					rootPanel.add(rankLabel);
				}

				Image pieceImage = new Image(PieceImageBundle.INSTANCE.empty());
				pieceImage.addStyleName(isWhite ? CSS.whiteSquare2() : CSS.blackSquare2());
				squareImages[index] = pieceImage;
				rootPanel.add(pieceImage);
			}
		}
		Label fileLabelPlaceholder = new Label();
		fileLabelPlaceholder.addStyleName(CSS.fileLabelPlaceHolder());
		rootPanel.add(fileLabelPlaceholder);
		for (int file = 0; file < 8; ++file) {
			Label fileLabel = new Label(Character.toString((char) ('a' + file)));
			fileLabels.add(fileLabel);
			fileLabel.addStyleName(CSS.fileLabel2());
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
			squareImages[viewIndex].setResource(PieceImageProvider.getImageResource(square));
		}
	}
	@Override
	public void showPromotionPawn(int from, int to) {
		int rank = ChessUtils.toRank(to);
		assert rank == 0 || rank == 7;
		Piece pawn = Piece.getFromColorAndPiece(rank == 7, PieceType.PAWN);
		squareImages[to].setResource(PieceImageProvider.getImageResource(pawn));
		squareImages[from].setResource(PieceImageBundle.INSTANCE.empty());
	}

	public Image[] getSquareImages() {
		return squareImages;
	}
}
