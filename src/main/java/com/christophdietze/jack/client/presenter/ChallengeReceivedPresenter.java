package com.christophdietze.jack.client.presenter;

import com.christophdietze.jack.client.event.ChallengeReceivedEvent;
import com.christophdietze.jack.client.event.ChallengeReceivedEventHandler;
import com.christophdietze.jack.client.util.GlobalEventBus;
import com.christophdietze.jack.client.util.MyAsyncCallback;
import com.christophdietze.jack.shared.ChessServiceAsync;
import com.christophdietze.jack.shared.Player;
import com.google.inject.Inject;

public class ChallengeReceivedPresenter {

	public interface View {
		void showPopup(long challengeId, Player challenger);
	}

	private GlobalEventBus eventBus;
	private ApplicationContext applicationContext;
	private ChessServiceAsync chessService;
	private View view;

	@Inject
	public ChallengeReceivedPresenter(GlobalEventBus eventBus, ApplicationContext applicationContext,
			ChessServiceAsync chessService) {
		this.eventBus = eventBus;
		this.applicationContext = applicationContext;
		this.chessService = chessService;
		initListeners();
	}

	public void bindView(View view) {
		assert this.view == null;
		this.view = view;
	}

	public void onAcceptChallenge(long challengeId) {
		chessService.acceptChallenge(applicationContext.getLocationId(), challengeId, new MyAsyncCallback<Void>() {
			@Override
			public void onSuccess(Void result) {
			}
		});
	}

	public void onDeclineChallenge(long challengeId) {
		throw new RuntimeException("not implemented");
	}

	private void initListeners() {
		eventBus.addHandler(ChallengeReceivedEvent.TYPE, new ChallengeReceivedEventHandler() {
			@Override
			public void onChallengeReceived(ChallengeReceivedEvent event) {
				view.showPopup(event.getChallengeId(), event.getChallenger());
			}
		});
	}
}
