package com.christophdietze.jack.client.presenter;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class GameModeManager {

	private AnalysisMode analysisMode;

	private MatchMode matchMode;

	private GameMode currentMode;

	@Inject
	public GameModeManager(AnalysisMode analysisMode, MatchMode matchMode) {
		this.analysisMode = analysisMode;
		this.matchMode = matchMode;
		this.currentMode = analysisMode;
	}

	public void activateAnalysisMode() {
		currentMode.deactivate();
		currentMode = analysisMode;
	}

	public void activateMatchMode(MatchInfo matchInfo) {
		currentMode.deactivate();
		currentMode = matchMode;
		matchMode.activate(matchInfo);
	}

	public GameMode getCurrentMode() {
		return currentMode;
	}
}
