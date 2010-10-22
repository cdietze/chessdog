package com.christophdietze.jack.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class SignedOutEvent extends GwtEvent<SignedOutEventHandler> {

	public static final Type<SignedOutEventHandler> TYPE = new Type<SignedOutEventHandler>();

	@Override
	public GwtEvent.Type<SignedOutEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(SignedOutEventHandler handler) {
		handler.onSignOut(this);
	}
}
