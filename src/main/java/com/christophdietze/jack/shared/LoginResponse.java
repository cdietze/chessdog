package com.christophdietze.jack.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class LoginResponse implements IsSerializable {

	public static enum Type {
		SUCCESS, NICKNAME_ALREADY_EXISTS;
	}

	public static LoginSuccessfulResponse newSuccessfulResponse(long locationId, String channelId) {
		return new LoginSuccessfulResponse(locationId, channelId);
	}
	public static LoginResponse newNicknameAlreadyExistsResponse() {
		return new LoginResponse(Type.NICKNAME_ALREADY_EXISTS);
	}

	private Type type;

	/**
	 * For serialization
	 */
	private LoginResponse() {
		this(null);
		// this(null, -1, null);
	}

	private LoginResponse(Type type) {
		this.type = type;
	}

	public Type getType() {
		return type;
	}

	public static class LoginSuccessfulResponse extends LoginResponse {
		private long locationId;
		private String channelId;

		/**
		 * For serialization
		 */
		private LoginSuccessfulResponse() {
		}

		private LoginSuccessfulResponse(long locationId, String channelId) {
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
