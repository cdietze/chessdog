package com.christophdietze.jack.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface LogMessageEventHandler extends EventHandler {
	public void onMessage(LogMessageEvent event);
}
