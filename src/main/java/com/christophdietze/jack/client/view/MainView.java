package com.christophdietze.jack.client.view;

import com.christophdietze.jack.client.remote.RemotePoller;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiFactory;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class MainView extends Composite {

	private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

	interface MyUiBinder extends UiBinder<Widget, MainView> {
	}

	// inner class must be public for injection to work
	public static class ProviderHelper {
		@Inject
		private Provider<CommandView> commandPanelProvider;
		@Inject
		private Provider<BoardView> boardViewProvider;
		@Inject
		private Provider<DragAndDropView> dndViewProvider;
		@Inject
		private Provider<PromotionView> promotionViewProvider;
		@Inject
		private Provider<RemotePoller> remotePollerProvider;
	}

	private ProviderHelper providerHelper;

	@Inject
	public MainView(ProviderHelper providerHelper) {
		this.providerHelper = providerHelper;
		initWidget(uiBinder.createAndBindUi(this));
		providerHelper.dndViewProvider.get();
		providerHelper.promotionViewProvider.get();
		providerHelper.remotePollerProvider.get();
	}

	@UiFactory
	CommandView makeCommandPanel() {
		return providerHelper.commandPanelProvider.get();
	}
	@UiFactory
	BoardView makeBoardView() {
		return providerHelper.boardViewProvider.get();
	}
}
