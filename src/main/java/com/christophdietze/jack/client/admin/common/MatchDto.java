package com.christophdietze.jack.client.admin.common;

import com.google.gwt.user.client.rpc.IsSerializable;

public class MatchDto implements IsSerializable {
	private long userA;
	private long userB;

	@SuppressWarnings("unused")
	private MatchDto() {
	}

	public MatchDto(long userA, long userB) {
		this.userA = userA;
		this.userB = userB;
	}
	public long getUserA() {
		return userA;
	}
	public long getUserB() {
		return userB;
	}
}
