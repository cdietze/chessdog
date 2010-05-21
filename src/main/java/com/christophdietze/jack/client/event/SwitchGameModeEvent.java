package com.christophdietze.jack.client.event;

import com.christophdietze.jack.client.presenter.MatchInfo;
import com.google.gwt.event.shared.GwtEvent;

public class SwitchGameModeEvent extends GwtEvent<SwitchGameModeEventHandler> {

	public static final Type<SwitchGameModeEventHandler> TYPE = new Type<SwitchGameModeEventHandler>();

	public static enum Mode {
		ANALYSIS_MODE, MATCH_MODE;
	}

	private Mode newMode;
	private MatchInfo matchInfo;

	private SwitchGameModeEvent(Mode newMode, MatchInfo matchInfo) {
		this.newMode = newMode;
		this.matchInfo = matchInfo;
	}

	public static SwitchGameModeEvent newSwitchToAnalysisModeEvent() {
		return new SwitchGameModeEvent(Mode.ANALYSIS_MODE, null);
	}
	public static SwitchGameModeEvent newSwitchToMatchModeEvent(MatchInfo matchInfo) {
		return new SwitchGameModeEvent(Mode.MATCH_MODE, matchInfo);
	}

	public Mode getNewMode() {
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