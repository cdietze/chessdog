package com.christophdietze.jack.client.util;

import com.google.gwt.user.client.rpc.AsyncCallback;

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
		throw new RuntimeException(ex);
	}
}
