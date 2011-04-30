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
import com.google.gwt.user.client.ui.Label;
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
		moveListPanel.add(new Label("hi "));
	}

	@Override
	public void update() {
		moveListPanel.add(new Label("hi "));
		moveListPanel.clear();
		Game game = presenter.getGame();
		appendToMoveList(game.getInitialMoveNode());
		if (game.getGameResult() != GameResult.UNDECIDED) {
			addBigSpace();
			addStaticText(game.getGameResult().getSymbol(), CSS.nowrap());
		}
	}

	private void appendToMoveList(MoveNode node) {
		if (node.isInitialNode()) {
			return;
		}
		boolean firstElement = true;
		while (true) {
			if (node.hasVariations()) {
				for (MoveNode variation : node.getVariations()) {
					addStaticText("(");
					addSmallNonbreakableSpace();
					appendToMoveList(variation);
					addStaticText(")");
					addBigSpace();
				}
			}

			final MoveNode finalNode = node;

			int fullMoveNum = ChessUtils.toFullmoveNumberFromPly(node.getPly());
			boolean isWhiteToMove = ChessUtils.toIsWhiteToMoveFromPly(node.getPly());
			if (isWhiteToMove || firstElement) {
				Anchor moveNumberLink = new Anchor(fullMoveNum + ".");
				moveNumberLink.addStyleName(CSS.moveListMoveNumber());
				moveNumberLink.addClickHandler(new ClickHandler() {
					public void onClick(ClickEvent event) {
						presenter.getGame().gotoPly(finalNode.getPly());
					}
				});
				addWidget(moveNumberLink);
				// addStaticText(fullMoveNum + ".");
				addSmallNonbreakableSpace();
				if (!isWhiteToMove) {
					addStaticText("...");
					addSmallNonbreakableSpace();
				}
			}
			Anchor link = new Anchor(node.getSanNotation());
			link.addStyleName(CSS.moveListMove());
			link.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					presenter.getGame().gotoPly(finalNode.getPly());
				}
			});
			if (presenter.getGame().getCurrentMoveNode() == node) {
				link.addStyleName(CSS.moveListCurrentMove());
			}

			addWidget(link);
			// addStaticText(node.getNotation());

			if (isWhiteToMove) {
				addSmallBreakableSpace();
			} else {
				addBigSpace();
			}

			if (!node.hasNext()) {
				break;
			}
			node = node.getNext();
			firstElement = false;
		}
	}

	private void addBigSpace() {
		HTML widget = new InlineHTML("&nbsp; ");
		widget.addStyleName(CSS.moveListBigSpace());
		moveListPanel.add(widget);
	}

	private void addSmallNonbreakableSpace() {
		InlineHTML widget = new InlineHTML("&nbsp;");
		widget.addStyleName(CSS.moveListSmallNonBreakableSpace());
		moveListPanel.add(widget);
	}

	private void addSmallBreakableSpace() {
		InlineHTML widget = new InlineHTML("&nbsp; ");
		widget.addStyleName(CSS.moveListSmallBreakableSpace());
		moveListPanel.add(widget);
	}

	private void addStaticText(String text) {
		moveListPanel.add(new InlineHTML(text));
	}

	private void addStaticText(String text, String cssStyle) {
		InlineHTML element = new InlineHTML(text);
		element.addStyleName(cssStyle);
		moveListPanel.add(element);
	}

	private void addWidget(Widget widget) {
		moveListPanel.add(widget);
	}
}
