package com.christophdietze.jack.client.admin.shared;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface AdminServiceAsync {

	void getMatches(AsyncCallback<ArrayList<MatchDto>> callback);

}
