package com.christophdietze.jack.shared;

/**
 * Message sent when a player revokes a challenge posted by him.
 * 
 * @see ChallengeDeclinedCometMessage
 */
public class ChallengeRevokedCometMessage extends CometMessage {

	private long challengeId;

	/**
	 * For serialization
	 */
	@SuppressWarnings("unused")
	private ChallengeRevokedCometMessage() {
	}

	public ChallengeRevokedCometMessage(long challengeId) {
		this.challengeId = challengeId;
	}

	public long getChallengeId() {
		return challengeId;
	}

}
