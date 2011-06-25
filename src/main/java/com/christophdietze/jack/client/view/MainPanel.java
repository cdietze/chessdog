package com.christophdietze.jack.client.view;

import com.christophdietze.jack.client.presenter.KeepAlivePresenter;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiFactory;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class MainPanel extends Composite {

	private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

	interface MyUiBinder extends UiBinder<Widget, MainPanel> {
	}

	// inner class must be public for injection to work
	public static class ProviderHelper {
		@Inject
		private Provider<BoardPanel> boardPanelProvider;
		@Inject
		private Provider<SquareSelectionView> squareSelectionViewProvider;
		@Inject
		private Provider<DragAndDropView> dndViewProvider;
		@Inject
		private Provider<PromotionView> promotionViewProvider;
		@Inject
		private Provider<CommandPanel> commandPanelProvider;
		@Inject
		private Provider<PlayerNamePanel> playerNamePanelProvider;
		@Inject
		private Provider<MessagePanel> messagePanelProvider;
		@Inject
		private Provider<ChallengeReceivedPopup> challengeReceivedPopupProvider;
		@Inject
		private Provider<KeepAlivePresenter> keepAliveProvider;
	}

	private ProviderHelper providerHelper;

	@Inject
	public MainPanel(ProviderHelper providerHelper) {
		this.providerHelper = providerHelper;
		initWidget(uiBinder.createAndBindUi(this));
		initializeDetachedViews();
	}

	private void initializeDetachedViews() {
		providerHelper.squareSelectionViewProvider.get();
		providerHelper.dndViewProvider.get();
		providerHelper.promotionViewProvider.get();
		providerHelper.challengeReceivedPopupProvider.get();
		providerHelper.keepAliveProvider.get();
	}

	@UiFactory
	CommandPanel makeCommandPanel() {
		return providerHelper.commandPanelProvider.get();
	}
	@UiFactory
	BoardPanel makeBoardPanel() {
		return providerHelper.boardPanelProvider.get();
	}
	@UiFactory
	PlayerNamePanel makePlayerNamePanel() {
		return providerHelper.playerNamePanelProvider.get();
	}
	@UiFactory
	MessagePanel makeMessagePanel() {
		return providerHelper.messagePanelProvider.get();
	}
}
