package com.christophdietze.jack.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface SignedOutEventHandler extends EventHandler {
	public void onSignOut(SignedOutEvent event);
}
