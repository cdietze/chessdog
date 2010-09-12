package com.christophdietze.jack.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class LogMessageEvent extends GwtEvent<LogMessageEventHandler> {

	public static final Type<LogMessageEventHandler> TYPE = new Type<LogMessageEventHandler>();

	private String message;

	public LogMessageEvent(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	@Override
	public GwtEvent.Type<LogMessageEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(LogMessageEventHandler handler) {
		handler.onMessage(this);
	}
}
