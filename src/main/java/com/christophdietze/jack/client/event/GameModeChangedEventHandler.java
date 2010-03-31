package com.christophdietze.jack.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface GameModeChangedEventHandler extends EventHandler {
	public void onGameModeChanged(GameModeChangedEvent event);
}
