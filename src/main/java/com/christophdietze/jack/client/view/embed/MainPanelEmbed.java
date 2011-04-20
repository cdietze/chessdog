package com.christophdietze.jack.client.view.embed;

import com.christophdietze.jack.client.view.BoardPanel;
import com.christophdietze.jack.client.view.DragAndDropView;
import com.christophdietze.jack.client.view.PlayerNamePanel;
import com.christophdietze.jack.client.view.PromotionView;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiFactory;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class MainPanelEmbed extends Composite {

	private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

	interface MyUiBinder extends UiBinder<Widget, MainPanelEmbed> {
	}

	// inner class must be public for injection to work
	public static class ProviderHelper {
		@Inject
		private Provider<BoardPanel> boardPanelProvider;
		@Inject
		private Provider<DragAndDropView> dndViewProvider;
		@Inject
		private Provider<PromotionView> promotionViewProvider;
		@Inject
		private Provider<PlayerNamePanel> playerNamePanelProvider;
	}

	private ProviderHelper providerHelper;

	@Inject
	public MainPanelEmbed(ProviderHelper providerHelper) {
		this.providerHelper = providerHelper;
		initWidget(uiBinder.createAndBindUi(this));
		initializeDetachedViews();
	}

	private void initializeDetachedViews() {
		providerHelper.dndViewProvider.get();
		providerHelper.promotionViewProvider.get();
	}

	@UiFactory
	BoardPanel makeBoardPanel() {
		return providerHelper.boardPanelProvider.get();
	}
	@UiFactory
	PlayerNamePanel makePlayerNamePanel() {
		return providerHelper.playerNamePanelProvider.get();
	}
}
