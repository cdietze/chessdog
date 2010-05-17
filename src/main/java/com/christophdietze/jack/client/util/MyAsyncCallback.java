package com.christophdietze.jack.client.util;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.StatusCodeException;

public abstract class MyAsyncCallback<T> implements AsyncCallback<T> {

	public static <U> AsyncCallback<U> doNothing() {
		return new MyAsyncCallback<U>() {
			@Override
			public void onSuccess(U result) {
			}
		};
	}
	@Override
	public void onFailure(Throwable ex) {
		StringBuilder sb = new StringBuilder("An RPC error occured");
		if (ex instanceof StatusCodeException) {
			sb.append(", status code is " + ((StatusCodeException) ex).getStatusCode());
		}
		throw new RuntimeException(sb.toString(), ex);
	}
}
