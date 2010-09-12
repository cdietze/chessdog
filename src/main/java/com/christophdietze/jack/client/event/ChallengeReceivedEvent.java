package com.christophdietze.jack.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class ChallengeReceivedEvent extends GwtEvent<ChallengeReceivedEventHandler> {

	public static final Type<ChallengeReceivedEventHandler> TYPE = new Type<ChallengeReceivedEventHandler>();

	private long challengeId;
	private long challengerId;

	public ChallengeReceivedEvent(long challengeId, long challengerId) {
		this.challengeId = challengeId;
		this.challengerId = challengerId;
	}

	public long getChallengeId() {
		return challengeId;
	}

	public long getChallengerId() {
		return challengerId;
	}

	@Override
	public GwtEvent.Type<ChallengeReceivedEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(ChallengeReceivedEventHandler handler) {
		handler.onChallengeReceived(this);
	}
}
