package com.christophdietze.jack.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface SignedInEventHandler extends EventHandler {
	public void onSignIn(SignedInEvent event);
}
