package com.christophdietze.jack.shared;

import com.christophdietze.jack.shared.util.SimpleToStringBuilder;
import com.google.gwt.user.client.rpc.IsSerializable;

public class Player implements IsSerializable {

	private long locationId;
	private String nickname;

	/**
	 * For serialization
	 */
	@SuppressWarnings("unused")
	private Player() {
	}

	public Player(long locationId, String nickname) {
		this.locationId = locationId;
		this.nickname = nickname;
	}

	public long getLocationId() {
		return locationId;
	}

	public String getNickname() {
		return nickname;
	}

	@Override
	public String toString() {
		return SimpleToStringBuilder.create(this).append("id", locationId).append("nickname", nickname).toString();
	}
}
