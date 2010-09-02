package com.christophdietze.jack.shared;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface CometServiceAsync {
	void dummyReceiveCometMessage(AsyncCallback<CometMessage> callback);
}
