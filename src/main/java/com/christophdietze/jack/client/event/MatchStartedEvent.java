package com.christophdietze.jack.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class MatchStartedEvent extends GwtEvent<MatchStartedEventHandler> {

	public static final Type<MatchStartedEventHandler> TYPE = new Type<MatchStartedEventHandler>();

	public MatchStartedEvent() {
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<MatchStartedEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(MatchStartedEventHandler handler) {
		handler.onMatchStarted(this);
	}
}
