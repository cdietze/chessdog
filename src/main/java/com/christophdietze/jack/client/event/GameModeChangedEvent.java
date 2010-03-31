package com.christophdietze.jack.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class GameModeChangedEvent extends GwtEvent<GameModeChangedEventHandler> {

	public static final Type<GameModeChangedEventHandler> TYPE = new Type<GameModeChangedEventHandler>();

	public GameModeChangedEvent() {
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<GameModeChangedEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(GameModeChangedEventHandler handler) {
		handler.onGameModeChanged(this);
	}
}
