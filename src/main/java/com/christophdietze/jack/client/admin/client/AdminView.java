package com.christophdietze.jack.client.admin.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

@Singleton
public class AdminView extends Composite {

	private static AdminViewUiBinder uiBinder = GWT.create(AdminViewUiBinder.class);

	interface AdminViewUiBinder extends UiBinder<Widget, AdminView> {
	}

	private AutoRefreshJob autoRefreshJob;

	@UiField
	Button autoUpdateButton;

	@UiField
	ListBox seekBox;

	@UiField
	ListBox matchBox;

	@Inject
	public AdminView(Provider<AutoRefreshJob> autoRefreshJobProvider) {
		initWidget(uiBinder.createAndBindUi(this));
		autoRefreshJob = autoRefreshJobProvider.get();
		autoRefreshJob.attachToView(this);
		updateAutoRefreshButton();
	}

	@UiHandler("autoUpdateButton")
	void onClick(ClickEvent event) {
		autoRefreshJob.toggleActive();
		updateAutoRefreshButton();
	}

	private void updateAutoRefreshButton() {
		autoUpdateButton.setText(autoRefreshJob.isActive() ? "Turn auto refresh off" : "Turn auto refresh on");
	}
}
