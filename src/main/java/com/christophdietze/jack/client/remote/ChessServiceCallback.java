package com.christophdietze.jack.client.remote;

import com.allen_sauer.gwt.log.client.Log;

public class ChessServiceCallback {

	public void onMatchFound() {
		Log.info("callback match found");
	}

	public void onMove() {
		Log.info("callback move");
	}
}
