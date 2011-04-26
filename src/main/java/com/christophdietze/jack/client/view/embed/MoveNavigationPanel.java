package com.christophdietze.jack.client.view.embed;

import com.christophdietze.jack.client.presenter.embed.MoveNavigationPresenter;
import com.christophdietze.jack.shared.board.Game;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

public class MoveNavigationPanel extends Composite implements MoveNavigationPresenter.View {

	private static MoveNavigationPanelUiBinder uiBinder = GWT.create(MoveNavigationPanelUiBinder.class);

	interface MoveNavigationPanelUiBinder extends UiBinder<Widget, MoveNavigationPanel> {
	}

	private MoveNavigationPresenter presenter;

	@UiField
	Button navStartButton;
	@UiField
	Button navPrevMoveButton;
	@UiField
	Button navNextMoveButton;
	@UiField
	Button navEndButton;

	@Inject
	public MoveNavigationPanel(MoveNavigationPresenter presenter) {
		this.presenter = presenter;
		initWidget(uiBinder.createAndBindUi(this));
		presenter.bindView(this);
		update();
	}

	@UiHandler("navStartButton")
	void onNavStartClick(ClickEvent event) {
		presenter.onNavStartClick();
	}

	@UiHandler("navPrevMoveButton")
	void onNavPrevMoveClick(ClickEvent event) {
		presenter.onNavPrevMoveClick();
	}

	@UiHandler("navNextMoveButton")
	void onNavNextMoveClick(ClickEvent event) {
		presenter.onNavNextMoveClick();
	}

	@UiHandler("navEndButton")
	void onNavEndClick(ClickEvent event) {
		presenter.onNavEndClick();
	}

	@Override
	public void update() {
		Game game = presenter.getGame();
		navStartButton.setEnabled(game.hasPrevMove());
		navPrevMoveButton.setEnabled(game.hasPrevMove());
		navNextMoveButton.setEnabled(game.hasNextMove());
		navEndButton.setEnabled(game.hasNextMove());
	}
}
