package com.christophdietze.jack.client;

import com.allen_sauer.gwt.log.client.Log;
import com.christophdietze.jack.client.presenter.ApplicationContext;
import com.christophdietze.jack.client.view.MainView;
import com.christophdietze.jack.common.JackService;
import com.christophdietze.jack.common.JackServiceAsync;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootLayoutPanel;

public class Main implements EntryPoint {

	private static final MyGinjector injector = GWT.create(MyGinjector.class);

	public void onModuleLoad() {
		final ApplicationContext applicationContext = injector.getApplicationContext();
		GWT.setUncaughtExceptionHandler(new GWT.UncaughtExceptionHandler() {
			public void onUncaughtException(Throwable ex) {
				Log.error("Uncaught exception:", ex);
				JackServiceAsync service = GWT.create(JackService.class);
				service.sendErrorReport(applicationContext.getLocationId(), buildErrorMessage(ex),
						new AsyncCallback<Void>() {
							@Override
							public void onSuccess(Void result) {
							}
							@Override
							public void onFailure(Throwable ex) {
								// don't throw another uncaught exception, rather just fail silently
							}
						});
			}
		});
		MainView mainView = injector.getMainView();
		RootLayoutPanel.get().add(mainView);
		Log.info("GWT Module " + this.getClass().getName() + " initialized.");
	}

	private static String buildErrorMessage(Throwable ex) {
		return buildErrorMessage(new StringBuilder(), ex).toString();
	}

	private static StringBuilder buildErrorMessage(StringBuilder sb, Throwable ex) {
		sb.append(ex.getClass().getName() + ": ");
		sb.append(ex.getMessage());
		if (ex.getCause() != null) {
			sb.append("; Caused by: ");
			return buildErrorMessage(sb, ex.getCause());
		}
		return sb;
	}
}
