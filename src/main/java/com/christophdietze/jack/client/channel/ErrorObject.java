package com.christophdietze.jack.client.channel;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * see http://code.google.com/appengine/docs/java/channel/javascript.html
 */
public class ErrorObject extends JavaScriptObject {
	protected ErrorObject() {
	}

	public final native String getDescription() /*-{
		return this.description;
	}-*/;

	public final native int getCode() /*-{
		return this.code;
	}-*/;
}
