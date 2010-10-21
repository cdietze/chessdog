package com.christophdietze.jack.test.server;

import junit.framework.TestCase;

import com.christophdietze.jack.server.ChessServiceImpl;
import com.christophdietze.jack.shared.SignInResponse.SignInSuccessfulResponse;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class SignInTest extends TestCase {

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
		SignInSuccessfulResponse userId1 = (SignInSuccessfulResponse) chessService.signIn("Alice");
		SignInSuccessfulResponse userId2 = (SignInSuccessfulResponse) chessService.signIn("Bob");
		assertNotSame(userId1.getLocationId(), userId2.getLocationId());
	}
}
