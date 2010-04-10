package com.christophdietze.jack.client.event;

import com.christophdietze.jack.client.presenter.MatchInfo;
import com.google.gwt.event.shared.GwtEvent;

public class MatchStartedEvent extends GwtEvent<MatchStartedEventHandler> {

	public static final Type<MatchStartedEventHandler> TYPE = new Type<MatchStartedEventHandler>();

	private MatchInfo matchInfo;

	public MatchStartedEvent(MatchInfo matchInfo) {
		this.matchInfo = matchInfo;
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<MatchStartedEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(MatchStartedEventHandler handler) {
		handler.onMatchStarted(this);
	}

	public MatchInfo getMatchInfo() {
		return matchInfo;
	}
}
