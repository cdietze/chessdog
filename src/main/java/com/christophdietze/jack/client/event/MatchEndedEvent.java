package com.christophdietze.jack.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class MatchEndedEvent extends GwtEvent<MatchEndedEventHandler> {

	public static final Type<MatchEndedEventHandler> TYPE = new Type<MatchEndedEventHandler>();

	public enum Reason {
		YOU_ABORTED, OPPONENT_ABORTED;
	}

	private Reason reason;

	public MatchEndedEvent(Reason reason) {
		assert reason != null;
		this.reason = reason;
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<MatchEndedEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(MatchEndedEventHandler handler) {
		handler.onMatchEnded(this);
	}

	public Reason getReason() {
		return reason;
	}
}
