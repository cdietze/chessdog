package com.christophdietze.jack.client.presenter;

import com.allen_sauer.gwt.log.client.Log;
import com.christophdietze.jack.client.util.MyAsyncCallback;
import com.christophdietze.jack.shared.ChessServiceAsync;
import com.christophdietze.jack.shared.PostChallengeResponse;
import com.google.inject.Inject;

public class PostChallengePresenter {

	private ApplicationContext applicationContext;
	private ChessServiceAsync chessService;

	@Inject
	public PostChallengePresenter(ApplicationContext applicationContext, ChessServiceAsync chessService) {
		this.applicationContext = applicationContext;
		this.chessService = chessService;
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
}
