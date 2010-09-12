package com.christophdietze.jack.client.presenter;

import com.allen_sauer.gwt.log.client.Log;
import com.christophdietze.jack.client.util.GlobalEventBus;
import com.christophdietze.jack.client.util.MyAsyncCallback;
import com.christophdietze.jack.shared.ChessServiceAsync;
import com.christophdietze.jack.shared.PostChallengeResponse;
import com.google.inject.Inject;

public class PostChallengePresenter {

	private GlobalEventBus eventBus;
	private ApplicationContext applicationContext;
	private GameManager gameManager;
	private ChessServiceAsync chessService;
	private CometMessageDispatcher cometMessageDispatcher;

	@Inject
	public PostChallengePresenter(GlobalEventBus eventBus, ApplicationContext applicationContext,
			GameManager gameManager, ChessServiceAsync chessService, CometMessageDispatcher cometMessageDispatcher) {
		this.eventBus = eventBus;
		this.applicationContext = applicationContext;
		this.gameManager = gameManager;
		this.chessService = chessService;
		this.cometMessageDispatcher = cometMessageDispatcher;
	}

	public void onPostPersonalChallenge(String opponentNick) {
		chessService.postChallenge(applicationContext.getLocationId(), opponentNick,
				new MyAsyncCallback<PostChallengeResponse>() {
					@Override
					public void onSuccess(PostChallengeResponse result) {
						Log.debug("challenge posted. response: " + result);
						switch (result) {
						case SUCCESS:
							break;
						case NO_PLAYER_WITH_OPPONENT_NICK_FOUND:
							break;
						default:
							throw new AssertionError("unexpected PostChallengeResponse type");
						}
					}
				});
	}

	/*
	 * private void postSeek() { chessService.postSeek(applicationContext.getLocationId(), new
	 * MyAsyncCallback<PostSeekResponse>() {
	 * 
	 * @Override public void onSuccess(PostSeekResponse result) { switch (result) { case OK:
	 * Log.debug("You joined the seek list"); break; case ALREADY_SEEKING: Log.warn("You already have an active seek");
	 * break; case HAS_ACTIVE_MATCH: Log.warn("You cannot seek while you have an active match"); break; default: throw
	 * new AssertionError(); } } }); }
	 */
}
