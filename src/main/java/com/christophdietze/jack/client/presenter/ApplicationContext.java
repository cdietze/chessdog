package com.christophdietze.jack.client.presenter;

import com.google.inject.Singleton;

@Singleton
public class ApplicationContext {

	private Long myUserId;

	public void setMyUserId(Long myUserId) {
		this.myUserId = myUserId;
	}

	public boolean amILoggedIn() {
		return myUserId != null;
	}

	public long getMyUserId() {
		if (myUserId == null) {
			throw new RuntimeException("Expected User to be logged in");
		}
		return myUserId;
	}

	public Long getMyUserIdOrNull() {
		return myUserId;
	}
}
