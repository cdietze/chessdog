package com.christophdietze.jack.common;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ChessServiceAsync {

	void login(AsyncCallback<Long> callback);
	void postSeek(AsyncCallback<PostSeekResponse> callback);
	void abortMatch(AsyncCallback<AbortResponse> callback);
	void makeMove(String algebraicMove, AsyncCallback<MakeMoveResponse> callback);
	void poll(AsyncCallback<RemoteEvent> callback);

}
