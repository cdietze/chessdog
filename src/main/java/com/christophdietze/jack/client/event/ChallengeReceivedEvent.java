package com.christophdietze.jack.client.event;

import com.christophdietze.jack.shared.Player;
import com.google.gwt.event.shared.GwtEvent;

public class ChallengeReceivedEvent extends GwtEvent<ChallengeReceivedEventHandler> {

	public static final Type<ChallengeReceivedEventHandler> TYPE = new Type<ChallengeReceivedEventHandler>();

	private long challengeId;
	private Player challenger;

	public ChallengeReceivedEvent(long challengeId, Player challenger) {
		this.challengeId = challengeId;
		this.challenger = challenger;
	}

	public long getChallengeId() {
		return challengeId;
	}

	public Player getChallenger() {
		return challenger;
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
