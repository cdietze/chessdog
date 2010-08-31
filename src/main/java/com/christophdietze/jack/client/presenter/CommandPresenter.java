package com.christophdietze.jack.client.presenter;

import com.allen_sauer.gwt.log.client.Log;
import com.christophdietze.jack.client.channel.Channel;
import com.christophdietze.jack.client.channel.ChannelFactory;
import com.christophdietze.jack.client.channel.SocketListener;
import com.christophdietze.jack.client.event.MatchEndedEvent;
import com.christophdietze.jack.client.event.SignedInEvent;
import com.christophdietze.jack.client.event.MatchEndedEvent.Reason;
import com.christophdietze.jack.client.util.GlobalEventBus;
import com.christophdietze.jack.client.util.MyAsyncCallback;
import com.christophdietze.jack.shared.AbortResponse;
import com.christophdietze.jack.shared.ChessServiceAsync;
import com.christophdietze.jack.shared.LoginResponse;
import com.christophdietze.jack.shared.PostSeekResponse;
import com.google.inject.Inject;

public class CommandPresenter {

	public interface View {
		void update();
	}

	private GlobalEventBus eventBus;
	private ApplicationContext applicationContext;
	private GameManager gameManager;
	private ChessServiceAsync chessService;

	private View view;

	@Inject
	public CommandPresenter(GlobalEventBus eventBus, ApplicationContext applicationContext, GameManager gameManager,
			ChessServiceAsync chessService) {
		this.eventBus = eventBus;
		this.applicationContext = applicationContext;
		this.gameManager = gameManager;
		this.chessService = chessService;
		initListeners();
	}

	public void bindView(View view) {
		assert this.view == null;
		this.view = view;
		view.update();
	}

	public void onSeekClick() {
		if (!applicationContext.isSignedIn()) {
			chessService.login(new MyAsyncCallback<LoginResponse>() {
				@Override
				public void onSuccess(LoginResponse result) {
					assert result != null;
					final long locationId = result.getLocationId();

					Log.debug("Opening Channel " + result.getChannelId());
					Channel channel = ChannelFactory.createChannel(result.getChannelId());
					channel.open(new SocketListener() {

						@Override
						public void onOpen() {
							Log.debug("Channel opened");
							Log.info("Logged in as user with id " + locationId);
							applicationContext.setLocationId(locationId);
							eventBus.fireEvent(new SignedInEvent(locationId));
							postSeek();
						}

						@Override
						public void onMessage(String message) {
							Log.debug("Channel received message: " + message);
						}
					});
				}
			});
		} else {
			postSeek();
		}
	}
	private void postSeek() {
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
				view.update();
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
					gameManager.switchToAnalysisMode();
					eventBus.fireEvent(new MatchEndedEvent(Reason.YOU_ABORTED));
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
	}
}
