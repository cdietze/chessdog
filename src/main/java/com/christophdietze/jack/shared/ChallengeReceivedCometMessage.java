package com.christophdietze.jack.shared;

public class ChallengeReceivedCometMessage extends CometMessage {

	private long challengeId;
	private long challengerId;

	/**
	 * For serialization
	 */
	@SuppressWarnings("unused")
	private ChallengeReceivedCometMessage() {
	}

	public ChallengeReceivedCometMessage(long challengeId, long challengerId) {
		this.challengeId = challengeId;
		this.challengerId = challengerId;
	}

	public long getChallengeId() {
		return challengeId;
	}

	public long getChallengerId() {
		return challengerId;
	}
}
