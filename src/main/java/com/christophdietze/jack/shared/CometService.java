package com.christophdietze.jack.shared;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.google.gwt.user.client.rpc.SerializationStreamFactory;

/**
 * Inspired by {@link http
 * ://code.google.com/p/dance-dance-robot/source/browse/trunk/src/com/google/appengine/demos/dda/client
 * /PushService.java}
 * <p>
 * A GWT RPC service interface for RPC calls which are pushed to this game client.
 * <p>
 * This interface is odd in that the client doesn't actually make calls through this interface to the server. Instead
 * the server uses server-side push to send GWT RPC encoded data to the client via an alternate transport. The
 * definition of this interface helps to ensure that all the correct de-serialization code is generated for the client.
 * A call to GWT.create on this service must be made to ensure the de-serialization code is actually generated.
 */
@RemoteServiceRelativePath(CometService.SERVLET_PATH)
public interface CometService extends RemoteService {
	static final String SERVLET_PATH = "cometService";

	public static class App {
		/**
		 * The GWT.create() call returns a proxy instance for the async interface which is a subclass of
		 * SerializationStreamFactory, thus it is ok to cast it to that.
		 */
		private static CometServiceAsync serviceAsync = GWT.create(CometService.class);
		private static SerializationStreamFactory streamFactory = (SerializationStreamFactory) serviceAsync;

		public static SerializationStreamFactory getSerializationStreamFactory() {
			return streamFactory;
		}
	}

	CometMessage dummyReceiveCometMessage();
}
