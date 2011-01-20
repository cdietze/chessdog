package com.christophdietze.jack.client.channel;

import com.google.gwt.core.client.JavaScriptObject;

public class Socket extends JavaScriptObject {
	protected Socket() {
	}

	public final native void close() /*-{
		this.close();
	}-*/;
}
