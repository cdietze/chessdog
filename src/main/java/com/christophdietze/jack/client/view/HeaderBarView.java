package com.christophdietze.jack.client.view;

import com.christophdietze.jack.client.presenter.HeaderBarPresenter;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

public class HeaderBarView extends Composite implements HeaderBarPresenter.View {

	private static HeaderBarViewUiBinder uiBinder = GWT.create(HeaderBarViewUiBinder.class);

	interface HeaderBarViewUiBinder extends UiBinder<Widget, HeaderBarView> {
	}

	@UiField
	Label userNameLabel;

	@UiField
	Hyperlink signOutLink;

	@Inject
	public HeaderBarView(HeaderBarPresenter presenter) {
		initWidget(uiBinder.createAndBindUi(this));
		presenter.bindView(this);
	}

	@Override
	public void setSignOutVisible(boolean visible) {
		signOutLink.setVisible(visible);
	}

	@Override
	public void setUserName(String userName) {
		userNameLabel.setText(userName);
	}

	@Override
	public void setUserNameVisible(boolean visible) {
		userNameLabel.setVisible(visible);
	}

	public void addFoo(Widget widget) {
	}

	@UiHandler("signOutLink")
	void onClick(ClickEvent e) {
		Window.alert("Hello!");
	}
}
