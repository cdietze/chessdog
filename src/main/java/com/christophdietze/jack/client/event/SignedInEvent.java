package com.christophdietze.jack.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class SignedInEvent extends GwtEvent<SignedInEventHandler> {

	public static final Type<SignedInEventHandler> TYPE = new Type<SignedInEventHandler>();

	private final long locationId;

	public SignedInEvent(long locationId) {
		this.locationId = locationId;
	}

	public long getLocationId() {
		return locationId;
	}

	@Override
	public GwtEvent.Type<SignedInEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(SignedInEventHandler handler) {
		handler.onSignIn(this);
	}
}
