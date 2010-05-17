package com.christophdietze.jack.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class UncaughtExceptionEvent extends GwtEvent<UncaughtExceptionEventHandler> {

	public static final Type<UncaughtExceptionEventHandler> TYPE = new Type<UncaughtExceptionEventHandler>();

	private String message;

	public UncaughtExceptionEvent(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	@Override
	public GwtEvent.Type<UncaughtExceptionEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(UncaughtExceptionEventHandler handler) {
		handler.onException(this);
	}
}
