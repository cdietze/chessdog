package com.christophdietze.jack.test.server;

import junit.framework.TestCase;

import com.christophdietze.jack.server.ChessServiceImpl;
import com.christophdietze.jack.shared.LoginResponse.LoginSuccessfulResponse;
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
		Injector injector = Guice.createInjector(new GuiceModuleForTest());
		ChessServiceImpl chessService = injector.getInstance(ChessServiceImpl.class);
		LoginSuccessfulResponse userId1 = (LoginSuccessfulResponse) chessService.login("Alice");
		LoginSuccessfulResponse userId2 = (LoginSuccessfulResponse) chessService.login("Bob");
		assertNotSame(userId1.getLocationId(), userId2.getLocationId());
	}
}
