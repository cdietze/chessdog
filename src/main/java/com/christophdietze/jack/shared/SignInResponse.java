package com.christophdietze.jack.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class SignInResponse implements IsSerializable {

	public static enum Type {
		SUCCESS, NICKNAME_ALREADY_EXISTS;
	}

	public static SignInSuccessfulResponse newSuccessfulResponse(long locationId, String channelId) {
		return new SignInSuccessfulResponse(locationId, channelId);
	}
	public static SignInResponse newNicknameAlreadyExistsResponse() {
		return new SignInResponse(Type.NICKNAME_ALREADY_EXISTS);
	}

	private Type type;

	/**
	 * For serialization
	 */
	private SignInResponse() {
		this(null);
		// this(null, -1, null);
	}

	private SignInResponse(Type type) {
		this.type = type;
	}

	public Type getType() {
		return type;
	}

	public static class SignInSuccessfulResponse extends SignInResponse {
		private long locationId;
		private String channelId;

		/**
		 * For serialization
		 */
		private SignInSuccessfulResponse() {
		}

		private SignInSuccessfulResponse(long locationId, String channelId) {
			super(Type.SUCCESS);
			this.locationId = locationId;
			this.channelId = channelId;
		}

		public long getLocationId() {
			return locationId;
		}

		public String getChannelId() {
			return channelId;
		}
	}

}
