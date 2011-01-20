package com.christophdietze.jack.client.channel;

/**
 * see http://code.google.com/appengine/docs/java/channel/javascript.html
 */
public interface SocketListener {
	void onOpen();
	void onClose();
	void onMessage(String message);
	void onError(String description, int code);
}
