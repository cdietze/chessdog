package com.christophdietze.jack.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public enum PostSeekResponse implements IsSerializable {
	OK, ALREADY_SEEKING, HAS_ACTIVE_MATCH;
}
