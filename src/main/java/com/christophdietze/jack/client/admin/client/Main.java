package com.christophdietze.jack.client.admin.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.RootPanel;

public class Main implements EntryPoint {

	private static final MyAdminGinjector injector = GWT.create(MyAdminGinjector.class);

	@Override
	public void onModuleLoad() {
		AdminView adminView = injector.getAdminView();
		RootPanel.get().add(adminView);
	}
}
