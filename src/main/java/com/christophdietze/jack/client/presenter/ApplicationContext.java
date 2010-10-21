package com.christophdietze.jack.client.presenter;

import com.christophdietze.jack.shared.Player;
import com.google.inject.Singleton;

@Singleton
public class ApplicationContext {

	// private static final long NOT_SIGNED_IN_ID = -1;

	private Player myPlayer;

	private boolean availableForChallenges = true;

	public boolean isSignedIn() {
		return myPlayer != null;
	}

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

	/**
	 * Returns true if the player is available to receive challenges. This is true if the player is not in a Match and
	 * has no open Challenge.
	 */
	public boolean isAvailableForChallenges() {
		return availableForChallenges;
	}

	public void setAvailableForChallenges(boolean availableForChallenges) {
		this.availableForChallenges = availableForChallenges;
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
