package com.christophdietze.jack.client.embed;

import com.allen_sauer.gwt.log.client.Log;
import com.christophdietze.jack.client.MyGinjector;
import com.christophdietze.jack.client.event.UncaughtExceptionEvent;
import com.christophdietze.jack.client.util.GlobalEventBus;
import com.christophdietze.jack.client.view.embed.MainPanelEmbed;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.RootLayoutPanel;

public class EmbedEntryPoint implements EntryPoint {

	private static final MyGinjector injector = GWT.create(MyGinjector.class);

	public void onModuleLoad() {
		injector.getJavaScriptBindings();
		final GlobalEventBus eventBus = injector.getEventBus();
		GWT.setUncaughtExceptionHandler(new GWT.UncaughtExceptionHandler() {
			public void onUncaughtException(Throwable ex) {
				Log.error("Uncaught exception:", ex);
				String errorMessage = buildErrorMessage(ex);
				eventBus.fireEvent(new UncaughtExceptionEvent(errorMessage));
			}
		});
		MainPanelEmbed mainPanel = injector.getMainPanelEmbed();
		clearAnyRemoveMeElement();
		RootLayoutPanel.get().add(mainPanel);
		Log.info("GWT Module " + this.getClass().getName() + " initialized.");
	}

	private void clearAnyRemoveMeElement() {
		Element element = Document.get().getElementById("removeme");
		if (element != null)
			element.removeFromParent();
	}

	private String buildErrorMessage(Throwable ex) {
		return buildErrorMessage(new StringBuilder(), ex).toString();
	}

	private StringBuilder buildErrorMessage(StringBuilder sb, Throwable ex) {
		sb.append(ex.getClass().getName() + ": ");
		sb.append(ex.getMessage());
		if (ex.getCause() != null) {
			sb.append("; Caused by: ");
			return buildErrorMessage(sb, ex.getCause());
		}
		return sb;
	}
}
