package com.christophdietze.jack.client.presenter;

import com.christophdietze.jack.client.event.SignedInEvent;
import com.christophdietze.jack.client.event.SignedInEventHandler;
import com.christophdietze.jack.client.util.GlobalEventBus;
import com.google.inject.Inject;

public class HeaderBarPresenter {

	public interface View {
		void setUserName(String userName);
		void setUserNameVisible(boolean visible);
		void setSignOutVisible(boolean visible);
	}

	private GlobalEventBus eventBus;

	private View view;

	@Inject
	public HeaderBarPresenter(GlobalEventBus eventBus) {
		this.eventBus = eventBus;
		initListeners();
	}

	public void bindView(View view) {
		this.view = view;
		view.setUserNameVisible(false);
		view.setSignOutVisible(false);
	}

	private void initListeners() {
		eventBus.addHandler(SignedInEvent.TYPE, new SignedInEventHandler() {
			@Override
			public void onSignIn(SignedInEvent event) {
				view.setUserName("User[" + event.getLocationId() + "]");
				view.setUserNameVisible(true);
				view.setSignOutVisible(true);
			}
		});
	}
}
