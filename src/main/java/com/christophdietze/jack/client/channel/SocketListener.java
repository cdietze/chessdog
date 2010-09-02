package com.christophdietze.jack.client.channel;

public interface SocketListener {
	void onOpen();
	void onMessage(String message);
}
