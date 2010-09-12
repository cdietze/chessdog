package com.christophdietze.jack.client.presenter;

import com.allen_sauer.gwt.log.client.Log;
import com.christophdietze.jack.client.event.LogMessageEvent;
import com.christophdietze.jack.client.util.GlobalEventBus;
import com.christophdietze.jack.client.util.MyAsyncCallback;
import com.christophdietze.jack.shared.ChessServiceAsync;
import com.christophdietze.jack.shared.PostChallengeResponse;
import com.google.inject.Inject;

public class PostChallengePresenter {

	private ApplicationContext applicationContext;
	private ChessServiceAsync chessService;
	private GlobalEventBus eventBus;

	@Inject
	public PostChallengePresenter(ApplicationContext applicationContext, GlobalEventBus eventBus,
			ChessServiceAsync chessService) {
		this.applicationContext = applicationContext;
		this.eventBus = eventBus;
		this.chessService = chessService;
	}

	public void onPostPersonalChallenge(final String opponentNick) {
		chessService.postChallenge(applicationContext.getLocationId(), opponentNick,
				new MyAsyncCallback<PostChallengeResponse>() {
					@Override
					public void onSuccess(PostChallengeResponse result) {
						Log.debug("challenge posted. response: " + result);
						switch (result) {
						case SUCCESS:
							eventBus.fireEvent(new LogMessageEvent("Challenge sent to " + opponentNick + "."));
							break;
						case NO_PLAYER_WITH_OPPONENT_NICK_FOUND:
							eventBus.fireEvent(new LogMessageEvent("No player with the name " + opponentNick + " found."));
							break;
						case OPPONENT_BUSY:
							eventBus.fireEvent(new LogMessageEvent("Player " + opponentNick
									+ " is currently unavailable for a challenge."));
							break;
						default:
							throw new AssertionError("unexpected PostChallengeResponse type");
						}
					}
				});
	}
}
