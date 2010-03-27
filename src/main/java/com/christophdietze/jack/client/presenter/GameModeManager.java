package com.christophdietze.jack.client.presenter;

import com.google.inject.Singleton;

@Singleton
public class GameModeManager {

	private GameMode currentMode = AnalysisMode.INSTANCE;

	public void setCurrentMode(GameMode currentMode) {
		this.currentMode = currentMode;
	}

	public GameMode get() {
		return currentMode;
	}

}
