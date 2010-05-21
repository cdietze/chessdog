package com.christophdietze.jack.client.event;

import com.christophdietze.jack.client.presenter.GameMode;
import com.christophdietze.jack.client.presenter.MatchInfo;
import com.google.gwt.event.shared.GwtEvent;

public class SwitchGameModeEvent extends GwtEvent<SwitchGameModeEventHandler> {

	public static final Type<SwitchGameModeEventHandler> TYPE = new Type<SwitchGameModeEventHandler>();

	private GameMode newMode;
	private MatchInfo matchInfo;

	private SwitchGameModeEvent(GameMode newMode, MatchInfo matchInfo) {
		this.newMode = newMode;
		this.matchInfo = matchInfo;
	}

	public static SwitchGameModeEvent newSwitchToAnalysisModeEvent() {
		return new SwitchGameModeEvent(GameMode.ANALYSIS_MODE, null);
	}
	public static SwitchGameModeEvent newSwitchToMatchModeEvent(MatchInfo matchInfo) {
		return new SwitchGameModeEvent(GameMode.MATCH_MODE, matchInfo);
	}

	public GameMode getNewMode() {
		return newMode;
	}

	public MatchInfo getMatchInfo() {
		return matchInfo;
	}

	@Override
	public GwtEvent.Type<SwitchGameModeEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(SwitchGameModeEventHandler handler) {
		handler.onSwitchGameMode(this);
	}
}
