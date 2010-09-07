package com.christophdietze.jack.shared;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ChessServiceAsync {

	void login(String nick, AsyncCallback<LoginResponse> callback);
	void loginComplete(long locationId, AsyncCallback<Void> callback);

	void postSeek(long locationId, AsyncCallback<PostSeekResponse> callback);
	void postChallenge(long locationId, String opponentNick, AsyncCallback<PostChallengeResponse> callback);

	void abortMatch(long locationId, AsyncCallback<AbortResponse> callback);
	void makeMove(long locationId, String algebraicMove, AsyncCallback<MakeMoveResponse> callback);
	void sendErrorReport(long locationId, String message, AsyncCallback<Void> callback);
	void induceError(String message, AsyncCallback<Void> callback);
}
