package com.christophdietze.jack.common;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath(JackService.SERVLET_PATH)
public interface JackService extends RemoteService {
	public static final String SERVLET_PATH = "jackService";

	public long login();
	public PostSeekResponse postSeek(long locationId);
	public AbortResponse abortMatch(long locationId);
	public MakeMoveResponse makeMove(long locationId, String algebraicMove);
	public RemoteEvent poll(long locationId);
}
