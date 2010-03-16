package com.christophdietze.jack.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface GameUpdatedEventHandler extends EventHandler {
	public void onUpdate(GameUpdatedEvent event);
}
