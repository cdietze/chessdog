package com.christophdietze.jack.client.presenter;

import com.google.inject.Singleton;

@Singleton
public class ApplicationContext {

	private Long locationId;

	public void setLocationId(Long locationId) {
		this.locationId = locationId;
	}

	public long getLocationId() {
		if (locationId == null) {
			throw new RuntimeException("Expected User to be logged in");
		}
		return locationId;
	}

	public boolean isSignedIn() {
		return locationId != null;
	}
}