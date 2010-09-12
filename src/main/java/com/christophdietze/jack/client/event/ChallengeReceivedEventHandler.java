package com.christophdietze.jack.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface ChallengeReceivedEventHandler extends EventHandler {
	public void onChallengeReceived(ChallengeReceivedEvent event);
}
