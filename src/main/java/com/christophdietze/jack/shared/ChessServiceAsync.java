package com.christophdietze.jack.shared;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ChessServiceAsync {

	void login(AsyncCallback<Long> callback);
	void postSeek(long locationId, AsyncCallback<PostSeekResponse> callback);
	void abortMatch(long locationId, AsyncCallback<AbortResponse> callback);
	void makeMove(long locationId, String algebraicMove, AsyncCallback<MakeMoveResponse> callback);
	void poll(long locationId, AsyncCallback<RemoteEvent> callback);
	void sendErrorReport(long locationId, String message, AsyncCallback<Void> callback);
	void induceError(String message, AsyncCallback<Void> callback);

}
