package com.christophdietze.jack.client.presenter;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class GameModeManager {

	@Inject
	private ApplicationContext applicationContext;

	@Inject
	private AnalysisMode analysisMode;

	@Inject
	private MatchMode matchMode;

	private GameMode currentMode;

	public void activateAnalysisMode() {
		applicationContext.setCurrentMatchInfo(null);
		currentMode = analysisMode;
	}

	public void activateMatchMode(MatchInfo matchInfo) {
		applicationContext.setCurrentMatchInfo(matchInfo);
		currentMode = matchMode;
	}

	public GameMode getCurrentMode() {
		if (currentMode == null) {
			currentMode = analysisMode;
		}
		return currentMode;
	}

}
