package com.christophdietze.jack.shared;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath(ChessService.SERVLET_PATH)
public interface ChessService extends RemoteService {
	public static final String SERVLET_PATH = "chessService";

	public long login();
	public PostSeekResponse postSeek(long locationId);
	public AbortResponse abortMatch(long locationId);
	public MakeMoveResponse makeMove(long locationId, String algebraicMove);
	public RemoteEvent poll(long locationId);

	/**
	 * The server will log the submitted message which can be used for analysis when an unexpected error occured on the
	 * client.
	 */
	public void sendErrorReport(long locationId, String message);

	/**
	 * Raises an Exception on the server having the specified message. Only intended for debugging purposes.
	 */
	public void induceError(String message);
}
