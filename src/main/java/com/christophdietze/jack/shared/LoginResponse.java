package com.christophdietze.jack.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class LoginResponse implements IsSerializable {

	private long locationId;
	private String channelId;

	/**
	 * To make GWT-RPC happy
	 */
	@SuppressWarnings("unused")
	private LoginResponse() {
		this(-1, null);
	}

	public LoginResponse(long locationId, String channelId) {
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
