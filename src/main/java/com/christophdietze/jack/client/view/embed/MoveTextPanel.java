package com.christophdietze.jack.client.view.embed;

import com.christophdietze.jack.client.presenter.embed.MoveTextPresenter;
import com.christophdietze.jack.client.presenter.embed.MoveTextPresenter.View;
import com.christophdietze.jack.client.resources.MyClientBundle;
import com.christophdietze.jack.client.resources.MyCss;
import com.christophdietze.jack.shared.board.ChessUtils;
import com.christophdietze.jack.shared.board.Game;
import com.christophdietze.jack.shared.board.GameResult;
import com.christophdietze.jack.shared.board.MoveNode;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

public class MoveTextPanel extends Composite implements View {

	private static MyCss CSS = MyClientBundle.CSS;
	private static MoveTextPanelUiBinder uiBinder = GWT.create(MoveTextPanelUiBinder.class);

	static {
		CSS.ensureInjected();
	}

	interface MoveTextPanelUiBinder extends UiBinder<Widget, MoveTextPanel> {
	}

	private MoveTextPresenter presenter;

	@UiField
	FlowPanel moveListPanel;

	@Inject
	public MoveTextPanel(MoveTextPresenter presenter) {
		this.presenter = presenter;
		presenter.bindView(this);
		initWidget(uiBinder.createAndBindUi(this));
		update();
	}

	@Override
	public void update() {
		moveListPanel.clear();
		Game game = presenter.getGame();
		appendMoveNode(game.getInitialMoveNode());
		if (game.getGameResult() != GameResult.UNDECIDED) {
			appendBigSpace();
			appendStaticText(game.getGameResult().getSymbol(), CSS.nowrap());
		}
	}

	private void appendMoveNode(MoveNode node) {
		if (node.isInitialNode()) {
			boolean isWhiteToMove = ChessUtils.toIsWhiteToMoveFromPly(node.getPly() + 1);
			if (isWhiteToMove) {
				appendMoveNumber(node);
			}
			if (!isWhiteToMove) {
				appendMoveNumber(node);
				appendStaticText("...");
				appendSmallNonbreakableSpace();
			}
			node = node.getNext();
		}

		while (node != null) {
			appendMove(node);

			if (node.hasNext() && ChessUtils.toIsWhiteToMoveFromPly(node.getPly() + 1)) {
				// if the next move is by white, then append a move number indicator
				appendMoveNumber(node);
			}

			node = node.getNext();
		}
	}

	private void appendMoveNumber(final MoveNode node) {
		int fullMoveNum = ChessUtils.toFullmoveNumberFromPly(node.getPly() + 1);
		Anchor moveNumberLink = new Anchor(fullMoveNum + ".");
		moveNumberLink.addStyleName(CSS.moveListMoveNumber());
		moveNumberLink.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				presenter.getGame().gotoPly(node.getPly());
			}
		});
		appendWidget(moveNumberLink);
		appendSmallNonbreakableSpace();
	}

	private void appendMove(final MoveNode node) {
		boolean isWhiteToMove = ChessUtils.toIsWhiteToMoveFromPly(node.getPly());
		Anchor link = new Anchor(node.getSanNotation());
		link.addStyleName(CSS.moveListMove());
		link.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				presenter.getGame().gotoPly(node.getPly());
			}
		});
		if (presenter.getGame().getCurrentMoveNode() == node) {
			link.addStyleName(CSS.moveListCurrentMove());
		}
		appendWidget(link);

		if (isWhiteToMove) {
			appendSmallBreakableSpace();
		} else {
			appendBigSpace();
		}
	}

	private void appendBigSpace() {
		HTML widget = new InlineHTML("&nbsp; ");
		widget.addStyleName(CSS.moveListBigSpace());
		moveListPanel.add(widget);
	}

	private void appendSmallNonbreakableSpace() {
		InlineHTML widget = new InlineHTML("&nbsp;");
		widget.addStyleName(CSS.moveListSmallNonBreakableSpace());
		moveListPanel.add(widget);
	}

	private void appendSmallBreakableSpace() {
		InlineHTML widget = new InlineHTML("&nbsp; ");
		widget.addStyleName(CSS.moveListSmallBreakableSpace());
		moveListPanel.add(widget);
	}

	private void appendStaticText(String text) {
		moveListPanel.add(new InlineHTML(text));
	}

	private void appendStaticText(String text, String cssStyle) {
		InlineHTML element = new InlineHTML(text);
		element.addStyleName(cssStyle);
		moveListPanel.add(element);
	}

	private void appendWidget(Widget widget) {
		moveListPanel.add(widget);
	}
}
