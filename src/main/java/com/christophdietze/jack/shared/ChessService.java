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

	public void sendErrorReport(long locationId, String message);
	public void induceError(String message);
}
