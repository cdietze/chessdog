package com.christophdietze.jack.test.server;

import com.christophdietze.jack.server.ChessServiceImpl;

public class ChessServiceImplMock extends ChessServiceImpl {

	@Override
	protected String getRemoteAddress() {
		return "127.0.0.1";
	}

}
