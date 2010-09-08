package com.christophdietze.jack.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class SignInFailedEvent extends GwtEvent<SignInFailedEventHandler> {

	public static final Type<SignInFailedEventHandler> TYPE = new Type<SignInFailedEventHandler>();

	private final String message;

	public SignInFailedEvent(String message) {
		this.message = message;
	}

	@Override
	public GwtEvent.Type<SignInFailedEventHandler> getAssociatedType() {
		return TYPE;
	}

	public String getMessage() {
		return message;
	}

	@Override
	protected void dispatch(SignInFailedEventHandler handler) {
		handler.onSignInFailed(this);
	}
}
