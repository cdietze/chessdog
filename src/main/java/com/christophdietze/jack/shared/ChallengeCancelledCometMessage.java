package com.christophdietze.jack.shared;

public class ChallengeCancelledCometMessage extends CometMessage {

	public static enum Reason {
		DECLINED, ABORTED;
	}

	public static ChallengeCancelledCometMessage newDeclinedMessage(long challengeId) {
		return new ChallengeCancelledCometMessage(challengeId, Reason.DECLINED);
	}

	public static ChallengeCancelledCometMessage newAbortedMessage(long challengeId) {
		return new ChallengeCancelledCometMessage(challengeId, Reason.ABORTED);
	}

	private long challengeId;
	private Reason reason;

	/**
	 * For serialization
	 */
	@SuppressWarnings("unused")
	private ChallengeCancelledCometMessage() {
	}

	public ChallengeCancelledCometMessage(long challengeId, Reason reason) {
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
