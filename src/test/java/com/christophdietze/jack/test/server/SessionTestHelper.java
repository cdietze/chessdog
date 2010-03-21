package com.christophdietze.jack.test.server;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;

import java.util.Map;

import javax.servlet.http.HttpSession;

import com.google.common.collect.Maps;
import com.google.inject.AbstractModule;
import com.google.inject.Module;
import com.google.inject.Provider;

class SessionTestHelper {
	private static final long DEFAULT_SESSION_ID = 0;

	private long currentSessionId = DEFAULT_SESSION_ID;

	private Map<Long, HttpSession> sessions = Maps.newHashMap();

	public void setCurrentSessionId(long id) {
		currentSessionId = id;
	}

	private Provider<HttpSession> getSessionProvider() {
		return new Provider<HttpSession>() {
			@Override
			public HttpSession get() {
				HttpSession session = sessions.get(currentSessionId);
				if (session == null) {
					session = newMockSession();
					sessions.put(currentSessionId, session);
				}
				return session;
			}
		};
	}

	public Module getModule() {
		return new AbstractModule() {
			@Override
			protected void configure() {
				bind(HttpSession.class).toProvider(getSessionProvider());
			}
		};
	}

	private HttpSession newMockSession() {
		HttpSession session = mock(MockHttpSession.class);
		doCallRealMethod().when(session).getAttribute(anyString());
		doCallRealMethod().when(session).setAttribute(anyString(), any());
		return session;
	}

	private static abstract class MockHttpSession implements HttpSession {

		private Map<String, Object> attributes;

		private Map<String, Object> getAttributesMap() {
			if (attributes == null) {
				attributes = Maps.newHashMap();
			}
			return attributes;
		}

		@Override
		public Object getAttribute(String name) {
			return getAttributesMap().get(name);
		}

		@Override
		public void setAttribute(String name, Object value) {
			getAttributesMap().put(name, value);
		}
	}
}
