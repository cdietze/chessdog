package com.christophdietze.jack.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class MatchEndedEvent extends GwtEvent<MatchEndedEventHandler> {

	public static final Type<MatchEndedEventHandler> TYPE = new Type<MatchEndedEventHandler>();

	public MatchEndedEvent() {
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<MatchEndedEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(MatchEndedEventHandler handler) {
		handler.onMatchEnded(this);
	}
}
