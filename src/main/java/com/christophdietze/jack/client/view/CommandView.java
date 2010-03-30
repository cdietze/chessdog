package com.christophdietze.jack.client.view;

import com.christophdietze.jack.client.presenter.CommandPresenter;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

public class CommandView extends Composite implements CommandPresenter.View {

	private static CommandViewUiBinder uiBinder = GWT.create(CommandViewUiBinder.class);

	interface CommandViewUiBinder extends UiBinder<Widget, CommandView> {
	}

	private CommandPresenter presenter;

	@UiField
	Hyperlink signInLink;
	@UiField
	Hyperlink seekLink;
	@UiField
	Hyperlink abortMatchLink;

	@Inject
	public CommandView(CommandPresenter presenter) {
		this.presenter = presenter;
		initWidget(uiBinder.createAndBindUi(this));
		presenter.bindView(this);
	}

	@Override
	public UIObject signInLink() {
		return signInLink;
	}
	@Override
	public UIObject seekLink() {
		return seekLink;
	}
	@Override
	public UIObject abortMatchLink() {
		return abortMatchLink;
	}

	@UiHandler("signInLink")
	void onSignInClick(ClickEvent event) {
		presenter.onSignInClick();
	}
	@UiHandler("seekLink")
	void onSeekClick(ClickEvent event) {
		presenter.onSeekClick();
	}
	@UiHandler("abortMatchLink")
	void onAbortMatchClick(ClickEvent event) {
		presenter.onAbortMatchClick();
	}
}
