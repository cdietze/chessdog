package com.christophdietze.jack.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * Message sent when a player declines a challenge posted to him.
 * 
 * @see ChallengeRevokedCometMessage
 */
public class ChallengeDeclinedCometMessage extends CometMessage {

	public static enum Reason implements IsSerializable {
		DECLINED, BUSY, ABORTED;
	}

	private long challengeId;
	private Reason reason;

	/**
	 * For serialization
	 */
	@SuppressWarnings("unused")
	private ChallengeDeclinedCometMessage() {
	}

	public ChallengeDeclinedCometMessage(long challengeId, Reason reason) {
		this.challengeId = challengeId;
		this.reason = reason;
	}

	public long getChallengeId() {
		return challengeId;
	}

	public Reason getReason() {
		return reason;
	}
}
