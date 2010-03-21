package com.christophdietze.jack.server;

import junit.framework.TestCase;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class LoginTest extends TestCase {

	private final LocalServiceTestHelper appengineHelper = new LocalServiceTestHelper(
			new LocalDatastoreServiceTestConfig());

	@Override
	protected void setUp() throws Exception {
		appengineHelper.setUp();
	}

	@Override
	protected void tearDown() throws Exception {
		appengineHelper.tearDown();
	}

	public void testLogin1() {
		SessionTestHelper sessionHelper = new SessionTestHelper();
		Injector injector = Guice.createInjector(sessionHelper.getModule());
		ChessServiceImpl chessService = injector.getInstance(ChessServiceImpl.class);
		long userId1 = chessService.login();
		assertSame(userId1, chessService.login());

		sessionHelper.setCurrentSessionId(2);
		long userId2 = chessService.login();
		assertSame(userId2, chessService.login());

		assertNotSame(userId1, userId2);
	}
}
