package com.christophdietze.jack.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface UncaughtExceptionEventHandler extends EventHandler {
	public void onException(UncaughtExceptionEvent event);
}
