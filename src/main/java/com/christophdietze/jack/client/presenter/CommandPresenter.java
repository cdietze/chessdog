package com.christophdietze.jack.client.presenter;

import com.allen_sauer.gwt.log.client.Log;
import com.christophdietze.jack.client.event.SignedInEvent;
import com.christophdietze.jack.client.util.GlobalEventBus;
import com.christophdietze.jack.client.util.MyAsyncCallback;
import com.christophdietze.jack.common.AbortResponse;
import com.christophdietze.jack.common.ChessServiceAsync;
import com.christophdietze.jack.common.PostSeekResponse;
import com.christophdietze.jack.common.board.Game;
import com.google.gwt.user.client.ui.UIObject;
import com.google.inject.Inject;

public class CommandPresenter {

	public interface View {
		UIObject signInLink();
		UIObject seekLink();
		UIObject abortMatchLink();
	}

	private View view;

	@Inject
	private GlobalEventBus eventBus;
	@Inject
	private ApplicationContext applicationContext;
	@Inject
	private GameModeManager gameModeManager;
	@Inject
	private ChessServiceAsync chessService;

	public void bindView(View view) {
		assert this.view == null;
		this.view = view;
		updateView();
	}

	public void onSignInClick() {
		Log.info("presenter: sign in");
		chessService.login(new MyAsyncCallback<Long>() {
			@Override
			public void onSuccess(Long result) {
				assert result != null;
				Log.info("Logged in as user with id " + result);
				applicationContext.setLocationId(result);
				eventBus.fireEvent(new SignedInEvent(result));
				updateView();
			}
		});
	}

	public void onSeekClick() {
		chessService.postSeek(applicationContext.getLocationId(), new MyAsyncCallback<PostSeekResponse>() {
			@Override
			public void onSuccess(PostSeekResponse result) {
				switch (result) {
				case OK:
					Log.debug("You joined the seek list");
					break;
				case ALREADY_SEEKING:
					Log.warn("You already have an active seek");
					break;
				case HAS_ACTIVE_MATCH:
					Log.warn("You cannot seek while you have an active match");
					break;
				default:
					throw new AssertionError();
				}
			}
		});
	}

	public void onAbortMatchClick() {
		chessService.abortMatch(applicationContext.getLocationId(), new MyAsyncCallback<AbortResponse>() {
			@Override
			public void onSuccess(AbortResponse result) {
				switch (result) {
				case OK:
					Log.debug("You aborted the game");
					gameModeManager.activateAnalysisMode();
					break;
				case NO_ACTIVE_MATCH:
					Log.warn("You have no active match");
					break;
				default:
					throw new AssertionError();
				}
			}
		});
	}

	private void updateView() {
		boolean signedIn = applicationContext.isSignedIn();
		view.signInLink().setVisible(!signedIn);
		view.seekLink().setVisible(signedIn);
	}
}
