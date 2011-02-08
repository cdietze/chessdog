package com.christophdietze.jack.shared;

import com.christophdietze.jack.shared.ChessService.ChallengeCancellationReason;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ChessServiceAsync {

	void signIn(String nickname, AsyncCallback<SignInResponse> callback);
	void completeSignIn(long locationId, AsyncCallback<Void> callback);
	void signOut(long locationId, AsyncCallback<Void> callback);

	void postPublicChallenge(long locationId, AsyncCallback<PostPublicChallengeResponse> callback);
	void postPersonalChallenge(long locationId, String opponentNickname,
			AsyncCallback<PostPersonalChallengeResponse> callback);
	// void revokeChallenge(long locationId, long challengeId, AsyncCallback<Void> callback);

	void acceptChallenge(long locationId, long challengeId, AsyncCallback<Void> callback);
	void declineChallenge(long locationId, long challengeId, ChallengeCancellationReason reason,
			AsyncCallback<Void> callback);

	void abortMatch(long locationId, AsyncCallback<AbortResponse> callback);
	void makeMove(long locationId, String algebraicMove, AsyncCallback<MakeMoveResponse> callback);

	void keepAlive(long locationId, AsyncCallback<Void> callback);
	void sendErrorReport(long locationId, String message, AsyncCallback<Void> callback);
	void induceError(String message, AsyncCallback<Void> callback);
}
