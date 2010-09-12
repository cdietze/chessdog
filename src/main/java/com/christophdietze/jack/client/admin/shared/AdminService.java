package com.christophdietze.jack.client.admin.shared;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath(AdminService.SERVLET_PATH)
public interface AdminService extends RemoteService {
	public static final String SERVLET_PATH = "adminService";

	public ArrayList<MatchDto> getMatches();
}
