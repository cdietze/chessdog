package com.christophdietze.jack.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class LoginResponse implements IsSerializable {

	public static enum State {
		SUCCESS, NICKNAME_ALREADY_EXISTS;
	}

	public static LoginResponse newSuccessfulResponse(long locationId, String channelId) {
		return new LoginResponse(State.SUCCESS, locationId, channelId);
	}
	public static LoginResponse newNicknameAlreadyExistsResponse() {
		return new LoginResponse(State.NICKNAME_ALREADY_EXISTS, -1, null);
	}

	private State state;
	private long locationId;
	private String channelId;

	/**
	 * For serialization
	 */
	@SuppressWarnings("unused")
	private LoginResponse() {
		this(null, -1, null);
	}

	private LoginResponse(State state, long locationId, String channelId) {
		this.state = state;
		this.locationId = locationId;
		this.channelId = channelId;
	}

	public State getState() {
		return state;
	}

	public long getLocationId() {
		return locationId;
	}

	public String getChannelId() {
		return channelId;
	}
}
