package com.christophdietze.jack.client.presenter;

import com.allen_sauer.gwt.log.client.Log;
import com.christophdietze.jack.client.event.ChallengeRevokedEvent;
import com.christophdietze.jack.client.event.ChallengeRevokedEventHandler;
import com.christophdietze.jack.client.event.ChallengeReceivedEvent;
import com.christophdietze.jack.client.event.ChallengeReceivedEventHandler;
import com.christophdietze.jack.client.event.MatchStartedEvent;
import com.christophdietze.jack.client.event.MatchStartedEventHandler;
import com.christophdietze.jack.client.util.GlobalEventBus;
import com.christophdietze.jack.client.util.MyAsyncCallback;
import com.christophdietze.jack.shared.ChessService.ChallengeCancellationReason;
import com.christophdietze.jack.shared.ChessServiceAsync;
import com.christophdietze.jack.shared.Player;
import com.google.inject.Inject;

public class ChallengeReceivedPresenter {

	public interface View {
		void showPopup(Player challenger);
		void hidePopup();
	}

	private GlobalEventBus eventBus;
	private ApplicationContext applicationContext;
	private ChessServiceAsync chessService;
	private View view;

	private long challengeId = -1;

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

	public void onAcceptChallenge() {
		chessService.acceptChallenge(applicationContext.getLocationId(), challengeId, new MyAsyncCallback<Void>() {
			@Override
			public void onSuccess(Void result) {
			}
			@Override
			public void onFailure(Throwable ex) {
				applicationContext.setAvailableForChallenges(true);
				super.onFailure(ex);
			}
		});
	}

	public void onDeclineChallenge() {
		applicationContext.setAvailableForChallenges(true);
		challengeId = -1;
		chessService.declineChallenge(applicationContext.getLocationId(), challengeId,
				ChallengeCancellationReason.DECLINED, MyAsyncCallback.<Void> doNothing());
	}

	private void initListeners() {
		eventBus.addHandler(ChallengeReceivedEvent.TYPE, new ChallengeReceivedEventHandler() {
			@Override
			public void onChallengeReceived(ChallengeReceivedEvent event) {
				assert applicationContext.isAvailableForChallenges() == true;
				applicationContext.setAvailableForChallenges(false);
				assert challengeId < 0 : "only one challenge supported - I should have been marked as busy!";
				challengeId = event.getChallengeId();
				view.showPopup(event.getChallenger());
			}
		});
		eventBus.addHandler(ChallengeRevokedEvent.TYPE, new ChallengeRevokedEventHandler() {
			@Override
			public void onChallengeCancelled(ChallengeRevokedEvent event) {
				if (event.getChallengeId() == challengeId) {
					challengeId = -1;
					view.hidePopup();
				} else {
					Log.warn("Received ChallengeCancellation for unknown challenge: " + event.getChallengeId());
				}
			}
		});
		eventBus.addHandler(MatchStartedEvent.TYPE, new MatchStartedEventHandler() {
			@Override
			public void onMatchStarted(MatchStartedEvent event) {
				challengeId = -1;
				view.hidePopup();
			}
		});
	}
}
