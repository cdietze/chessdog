package com.christophdietze.jack.client;

import com.allen_sauer.gwt.log.client.Log;
import com.christophdietze.jack.client.event.UncaughtExceptionEvent;
import com.christophdietze.jack.client.presenter.ApplicationContext;
import com.christophdietze.jack.client.util.GlobalEventBus;
import com.christophdietze.jack.client.view.MainView;
import com.christophdietze.jack.common.JackServiceAsync;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootLayoutPanel;

public class Main implements EntryPoint {

	private static final MyGinjector injector = GWT.create(MyGinjector.class);

	public void onModuleLoad() {
		final ApplicationContext applicationContext = injector.getApplicationContext();
		final JackServiceAsync service = injector.getJackServiceAsync();
		final GlobalEventBus eventBus = injector.getEventBus();
		GWT.setUncaughtExceptionHandler(new GWT.UncaughtExceptionHandler() {
			public void onUncaughtException(Throwable ex) {
				Log.error("Uncaught exception:", ex);
				String errorMessage = buildErrorMessage(ex);
				eventBus.fireEvent(new UncaughtExceptionEvent(errorMessage));
				service.sendErrorReport(applicationContext.getLocationId(), errorMessage, new AsyncCallback<Void>() {
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
