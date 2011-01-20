package com.christophdietze.jack.client.channel;

import com.google.gwt.core.client.JavaScriptObject;

public class Channel extends JavaScriptObject {
	protected Channel() {
	}

	public final native Socket open(SocketListener listener) /*-{
		var socket = this.open( {
		onopen : $entry(function() {
		listener.@com.christophdietze.jack.client.channel.SocketListener::onOpen()();
		}), 
		onclose : $entry(function() {
		listener.@com.christophdietze.jack.client.channel.SocketListener::onClose()();
		})	,
		onmessage : $entry(function(event) {
		listener.@com.christophdietze.jack.client.channel.SocketListener::onMessage(Ljava/lang/String;)(event.data);
		})	,
		onerror : $entry(function(event) {
		listener.@com.christophdietze.jack.client.channel.SocketListener::onError(Ljava/lang/String;I)(event.description, event.code);
		})	
		}
		);
		return socket;
	}-*/;
}
