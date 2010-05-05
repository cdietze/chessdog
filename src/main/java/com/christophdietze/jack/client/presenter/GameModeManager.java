package com.christophdietze.jack.client.presenter;

import com.christophdietze.jack.client.event.SwitchGameModeEvent;
import com.christophdietze.jack.client.event.SwitchGameModeEventHandler;
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
		initListeners();
	}

	public GameMode getCurrentMode() {
		return currentMode;
	}

	private void initListeners() {
		eventBus.addHandler(SwitchGameModeEvent.TYPE, new SwitchGameModeEventHandler() {
			@Override
			public void onSwitchGameMode(SwitchGameModeEvent event) {
				switch (event.getNewMode()) {
				case ANALYSIS_MODE:
					currentMode.deactivate();
					currentMode = analysisMode;
					break;
				case MATCH_MODE:
					currentMode.deactivate();
					currentMode = matchMode;
					matchMode.activate(event.getMatchInfo());
					break;
				default:
					throw new AssertionError();
				}
			}
		});
	}
}
