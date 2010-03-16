package com.christophdietze.jack.common;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath(ChessService.SERVLET_PATH)
public interface ChessService extends RemoteService {
	public static final String SERVLET_PATH = "chessService";

	public long login();
	public void makeMove(int from, int to, Character promotionPieceSymbol);

	public RemoteEvent poll();

}
