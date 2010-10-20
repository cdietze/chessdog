package com.christophdietze.jack.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface ChallengeRevokedEventHandler extends EventHandler {
	public void onChallengeCancelled(ChallengeRevokedEvent event);
}
