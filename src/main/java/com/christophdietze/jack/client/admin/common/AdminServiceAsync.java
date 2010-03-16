package com.christophdietze.jack.client.admin.common;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface AdminServiceAsync {

	void getSeekingUsers(AsyncCallback<ArrayList<Long>> callback);

	void getMatches(AsyncCallback<ArrayList<MatchDto>> callback);

}
