package com.christophdietze.jack.shared;

public class ChallengeReceivedCometMessage extends CometMessage {

	private long challengeId;
	private Player challenger;

	/**
	 * For serialization
	 */
	@SuppressWarnings("unused")
	private ChallengeReceivedCometMessage() {
	}

	public ChallengeReceivedCometMessage(long challengeId, Player challenger) {
		this.challengeId = challengeId;
		this.challenger = challenger;
	}

	public long getChallengeId() {
		return challengeId;
	}

	public Player getChallenger() {
		return challenger;
	}
}
