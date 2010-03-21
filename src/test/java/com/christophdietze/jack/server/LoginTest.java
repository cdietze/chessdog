package com.christophdietze.jack.server;

import junit.framework.TestCase;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.SessionScoped;

public class LoginTest extends TestCase {

	@Override
	protected void tearDown() throws Exception {
		MockSessionScope.INSTANCE.reset();
	}

	public void testLogin1() {
		Injector injector = getInjector();
		ChessServiceImpl chessService = injector.getInstance(ChessServiceImpl.class);
		long userId1 = chessService.login();
		MockSessionScope.INSTANCE.setCurrentSessionId(2);
		long userId2 = chessService.login();
		assertNotSame(userId1, userId2);
	}

	private Injector getInjector() {
		return Guice.createInjector(new AbstractModule() {
			@Override
			protected void configure() {
				bindScope(SessionScoped.class, MockSessionScope.INSTANCE);
			}
		});
	}
}
