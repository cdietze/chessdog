package com.christophdietze.jack.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class GameUpdatedEvent extends GwtEvent<GameUpdatedEventHandler> {

	public static final Type<GameUpdatedEventHandler> TYPE = new Type<GameUpdatedEventHandler>();

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<GameUpdatedEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(GameUpdatedEventHandler handler) {
		handler.onUpdate(this);
	}
}
