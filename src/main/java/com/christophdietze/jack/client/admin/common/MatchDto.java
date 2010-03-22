package com.christophdietze.jack.client.admin.common;

import com.google.gwt.user.client.rpc.IsSerializable;

public class MatchDto implements IsSerializable {
	private long userA;
	private long userB;
	private boolean active;

	@SuppressWarnings("unused")
	private MatchDto() {
	}

	public MatchDto(long userA, long userB, boolean active) {
		this.userA = userA;
		this.userB = userB;
		this.active = active;
	}
	public long getUserA() {
		return userA;
	}
	public long getUserB() {
		return userB;
	}
	public boolean isActive() {
		return active;
	}
}
