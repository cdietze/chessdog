package com.christophdietze.jack.client.view;

import com.christophdietze.jack.client.presenter.PromotionPresenter;
import com.christophdietze.jack.client.resources.MyClientBundle;
import com.christophdietze.jack.client.resources.MyCss;
import com.christophdietze.jack.client.resources.PieceImageProvider;
import com.christophdietze.jack.client.util.UiUtils;
import com.christophdietze.jack.shared.board.ChessUtils;
import com.christophdietze.jack.shared.board.Piece;
import com.christophdietze.jack.shared.board.PieceType;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.PopupPanel.PositionCallback;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * <p>
 * <img class='gallery' src='doc-files/PromotionMoveEvent.jpg'>
 * </p>
 */
@Singleton
public class PromotionView implements PromotionPresenter.View {

	private static MyCss CSS = MyClientBundle.CSS;

	private PromotionPresenter model;
	private BoardPanel boardPanel;
	private PopupPanel whitePopupPanel;
	private PopupPanel blackPopupPanel;
	private boolean pieceSelected = false;

	@Inject
	public PromotionView(PromotionPresenter model, BoardPanel boardView) {
		this.model = model;
		this.boardPanel = boardView;

		whitePopupPanel = createPopupPanel(true);
		blackPopupPanel = createPopupPanel(false);

		model.bindView(this);
	}

	@Override
	public void showPopup(int toIndex) {
		pieceSelected = false;
		int rank = ChessUtils.toRank(toIndex);
		assert rank == 0 || rank == 7;
		final PopupPanel popup = rank == 0 ? blackPopupPanel : whitePopupPanel;
		final Widget reference = boardPanel.getSquares()[toIndex];
		popup.setPopupPositionAndShow(new PositionCallback() {
			public void setPosition(int offsetWidth, int offsetHeight) {
				UiUtils.setPopupPositionAtTopLeft(popup, reference);
			}
		});
	}

	private PopupPanel createPopupPanel(boolean isWhite) {
		final PopupPanel popupPanel = new PopupPanel(true);
		popupPanel.setStylePrimaryName(CSS.promotionPopup());
		FlowPanel flow = new FlowPanel();
		popupPanel.setWidget(flow);
		for (final PieceType piece : new PieceType[] { PieceType.QUEEN, PieceType.ROOK, PieceType.BISHOP,
				PieceType.KNIGHT }) {
			Piece square = Piece.getFromColorAndPieceType(isWhite, piece);
			Image image = PieceImageProvider.getImage(square);
			image.addStyleName(CSS.promotionPieceImage());
			image.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					pieceSelected = true;
					model.onPieceSelected(piece);
					popupPanel.hide();
				}
			});
			flow.add(image);
		}
		popupPanel.addCloseHandler(new CloseHandler<PopupPanel>() {
			public void onClose(CloseEvent<PopupPanel> arg0) {
				if (!pieceSelected) {
					model.onNoPieceSelected();
				}
				pieceSelected = false;
			}
		});
		return popupPanel;
	}
}
