package com.christophdietze.jack.client.util;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerManager;
import com.google.inject.Singleton;

@Singleton
public class GlobalEventBus extends HandlerManager {

	public static final String EVENT_SOURCE = "GlobalHandlerManager";

	public GlobalEventBus() {
		super(EVENT_SOURCE);
	}

	@Override
	public void fireEvent(GwtEvent<?> event) {
		Log.debug("Event fired: " + event.toDebugString());
		super.fireEvent(event);
	}

}
