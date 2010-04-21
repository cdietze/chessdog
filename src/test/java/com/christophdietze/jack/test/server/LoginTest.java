package com.christophdietze.jack.test.server;

import junit.framework.TestCase;

import com.christophdietze.jack.server.JackServiceImpl;
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
		Injector injector = Guice.createInjector();
		JackServiceImpl chessService = injector.getInstance(JackServiceImpl.class);
		long userId1 = chessService.login();
		long userId2 = chessService.login();
		assertNotSame(userId1, userId2);
	}
}
