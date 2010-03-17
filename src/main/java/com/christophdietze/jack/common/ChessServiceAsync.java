package com.christophdietze.jack.common;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ChessServiceAsync {

	void login(AsyncCallback<Long> callback);

	void makeMove(String algebraicMove, AsyncCallback<Void> callback);

	void poll(AsyncCallback<RemoteEvent> callback);

}
