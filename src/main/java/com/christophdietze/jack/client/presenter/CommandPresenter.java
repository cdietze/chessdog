package com.christophdietze.jack.client.presenter;

import com.allen_sauer.gwt.log.client.Log;
import com.christophdietze.jack.client.event.GameModeChangedEvent;
import com.christophdietze.jack.client.event.GameModeChangedEventHandler;
import com.christophdietze.jack.client.event.SignedInEvent;
import com.christophdietze.jack.client.util.GlobalEventBus;
import com.christophdietze.jack.client.util.MyAsyncCallback;
import com.christophdietze.jack.common.AbortResponse;
import com.christophdietze.jack.common.ChessServiceAsync;
import com.christophdietze.jack.common.PostSeekResponse;
import com.google.inject.Inject;

public class CommandPresenter {

	public interface View {
		void update();
	}

	private GlobalEventBus eventBus;
	private ApplicationContext applicationContext;
	private GameModeManager gameModeManager;
	private ChessServiceAsync chessService;

	private View view;

	@Inject
	public CommandPresenter(GlobalEventBus eventBus, ApplicationContext applicationContext,
			GameModeManager gameModeManager, ChessServiceAsync chessService) {
		this.eventBus = eventBus;
		this.applicationContext = applicationContext;
		this.gameModeManager = gameModeManager;
		this.chessService = chessService;
		initListeners();
	}

	public void bindView(View view) {
		assert this.view == null;
		this.view = view;
		view.update();
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
				view.update();
			}
		});
	}

	public void onSignOutClick() {
		throw new RuntimeException();
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

	private void initListeners() {
		eventBus.addHandler(GameModeChangedEvent.TYPE, new GameModeChangedEventHandler() {
			@Override
			public void onGameModeChanged(GameModeChangedEvent event) {
				view.update();
			}
		});
	}
}
