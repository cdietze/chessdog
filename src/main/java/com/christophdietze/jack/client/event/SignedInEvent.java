package com.christophdietze.jack.client.event;

import com.christophdietze.jack.shared.Player;
import com.google.gwt.event.shared.GwtEvent;

public class SignedInEvent extends GwtEvent<SignedInEventHandler> {

	public static final Type<SignedInEventHandler> TYPE = new Type<SignedInEventHandler>();

	private final Player myPlayer;

	public SignedInEvent(Player myPlayer) {
		this.myPlayer = myPlayer;
	}

	public Player getMyPlayer() {
		return myPlayer;
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
