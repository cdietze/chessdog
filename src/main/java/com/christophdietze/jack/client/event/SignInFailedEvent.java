package com.christophdietze.jack.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class SignInFailedEvent extends GwtEvent<SignInFailedEventHandler> {

	public static final Type<SignInFailedEventHandler> TYPE = new Type<SignInFailedEventHandler>();

	@Override
	public GwtEvent.Type<SignInFailedEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(SignInFailedEventHandler handler) {
		handler.onSignInFailed(this);
	}
}
