package com.christophdietze.jack.shared;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath(ChessService.SERVLET_PATH)
public interface ChessService extends RemoteService {
	static final String SERVLET_PATH = "chessService";

	LoginResponse login(String nick);
	void loginComplete(long locationId);

	PostSeekResponse postSeek(long locationId);
	PostChallengeResponse postChallenge(long locationId, String opponentNick);

	AbortResponse abortMatch(long locationId);
	MakeMoveResponse makeMove(long locationId, String algebraicMove);

	/**
	 * The server will log the submitted message which can be used for analysis when an unexpected error occured on the
	 * client.
	 */
	void sendErrorReport(long locationId, String message);

	/**
	 * Raises an Exception on the server having the specified message. Only intended for debugging purposes.
	 */
	void induceError(String message);
}
