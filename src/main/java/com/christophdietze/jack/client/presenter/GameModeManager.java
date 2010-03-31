package com.christophdietze.jack.client.presenter;

import com.christophdietze.jack.client.event.GameModeChangedEvent;
import com.christophdietze.jack.client.util.GlobalEventBus;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class GameModeManager {

	private GlobalEventBus eventBus;
	private AnalysisMode analysisMode;
	private MatchMode matchMode;

	private GameMode currentMode;

	@Inject
	public GameModeManager(GlobalEventBus eventBus, AnalysisMode analysisMode, MatchMode matchMode) {
		this.eventBus = eventBus;
		this.analysisMode = analysisMode;
		this.matchMode = matchMode;
		this.currentMode = analysisMode;
	}

	public void activateAnalysisMode() {
		currentMode.deactivate();
		currentMode = analysisMode;
		eventBus.fireEvent(new GameModeChangedEvent());
	}

	public void activateMatchMode(MatchInfo matchInfo) {
		currentMode.deactivate();
		currentMode = matchMode;
		matchMode.activate(matchInfo);
		eventBus.fireEvent(new GameModeChangedEvent());
	}

	public GameMode getCurrentMode() {
		return currentMode;
	}
}
