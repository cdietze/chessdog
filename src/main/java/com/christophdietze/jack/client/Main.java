package com.christophdietze.jack.client;

import com.allen_sauer.gwt.log.client.Log;
import com.christophdietze.jack.client.view.MainView;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.RootLayoutPanel;

public class Main implements EntryPoint {

	private static final MyGinjector injector = GWT.create(MyGinjector.class);

	public void onModuleLoad() {
		GWT.setUncaughtExceptionHandler(new GWT.UncaughtExceptionHandler() {
			public void onUncaughtException(Throwable ex) {
				Log.error("uncaught exception", ex);
			}
		});

		MainView mainView2 = injector.getMainView();
		// RootPanel.get().add(mainView2);
		RootLayoutPanel.get().add(mainView2);
		// injector.getApplication();
		Log.info("GWT Module " + this.getClass().getName() + " initialized.");
	}
}
