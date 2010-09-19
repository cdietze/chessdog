package com.christophdietze.jack.test.server;

import java.util.Iterator;

import com.christophdietze.jack.server.CometServer;
import com.christophdietze.jack.shared.CometMessage;
import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.ListMultimap;
import com.google.inject.Singleton;

@Singleton
public class CometServerMock implements CometServer {

	private ListMultimap<Long, CometMessage> messageMap = LinkedListMultimap.create();

	@Override
	public String createChannel(long locationId) {
		return Long.toString(locationId);
	}

	@Override
	public void sendMessage(long locationId, CometMessage message) {
		messageMap.put(locationId, message);
	}

	public static interface CometListener {
	}

	@SuppressWarnings("unchecked")
	public <T extends CometMessage> T getNextMessageOfType(long locationId, Class<T> messageType) {
		for (Iterator<CometMessage> iter = messageMap.get(locationId).iterator(); iter.hasNext();) {
			CometMessage message = iter.next();
			iter.remove();
			if (message.getClass().isAssignableFrom(messageType)) {
				return (T) message;
			}
		}
		throw new RuntimeException("Not comet message of type " + messageType + " in queue for locationId " + locationId);
	}
}
