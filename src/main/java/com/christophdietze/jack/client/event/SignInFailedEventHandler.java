package com.christophdietze.jack.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface SignInFailedEventHandler extends EventHandler {
	public void onSignInFailed(SignInFailedEvent event);
}
