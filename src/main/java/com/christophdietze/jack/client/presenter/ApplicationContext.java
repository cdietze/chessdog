package com.christophdietze.jack.client.presenter;

import com.christophdietze.jack.shared.Player;
import com.google.inject.Singleton;

@Singleton
public class ApplicationContext {

	// private static final long NOT_SIGNED_IN_ID = -1;

	private Player myPlayer;

	public Player getMyPlayer() {
		return myPlayer;
	}

	public void setMyPlayer(Player myPlayer) {
		this.myPlayer = myPlayer;
	}

	/**
	 * Convenience function for getMyPlayer().getLocationId()
	 */
	public long getLocationId() {
		return myPlayer.getLocationId();
	}

	// private long locationId = NOT_SIGNED_IN_ID;
	//
	// public void setLocationId(long locationId) {
	// this.locationId = locationId;
	// }
	//
	// public long getLocationId() {
	// return locationId;
	// }

}
