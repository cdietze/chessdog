package com.christophdietze.jack.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class ChallengeRevokedEvent extends GwtEvent<ChallengeRevokedEventHandler> {

	public static final Type<ChallengeRevokedEventHandler> TYPE = new Type<ChallengeRevokedEventHandler>();

	private long challengeId;

	public ChallengeRevokedEvent(long challengeId) {
		this.challengeId = challengeId;
	}

	public long getChallengeId() {
		return challengeId;
	}

	@Override
	public GwtEvent.Type<ChallengeRevokedEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(ChallengeRevokedEventHandler handler) {
		handler.onChallengeCancelled(this);
	}
}
