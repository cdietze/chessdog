package com.christophdietze.jack.client.presenter;

import com.google.inject.Singleton;

@Singleton
public class ApplicationContext {

	private static final long NOT_SIGNED_IN_ID = -1;

	private long locationId = NOT_SIGNED_IN_ID;

	public void setLocationId(long locationId) {
		this.locationId = locationId;
	}

	public long getLocationId() {
		return locationId;
	}

	public boolean isSignedIn() {
		return locationId != NOT_SIGNED_IN_ID;
	}
}
