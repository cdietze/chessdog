package com.christophdietze.jack.server;

import junit.framework.TestCase;

import org.junit.Test;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.SessionScoped;

public class PlayGameTest extends TestCase {

	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());

	@Override
	protected void setUp() throws Exception {
		helper.setUp();
	}

	@Override
	protected void tearDown() throws Exception {
		helper.tearDown();
	}

	@Test
	public void test1() {
		ChessServiceImpl chessService = getInjector().getInstance(ChessServiceImpl.class);
		final long session1 = 1;
		final long session2 = 2;
		MockSessionScope.INSTANCE.setCurrentSessionId(session1);
		chessService.login();
		chessService.postSeek();
		MockSessionScope.INSTANCE.setCurrentSessionId(session2);
		chessService.login();
		chessService.postSeek();

		chessService.makeMove("e2e4");
		MockSessionScope.INSTANCE.setCurrentSessionId(session1);
		chessService.makeMove("e7e5");
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
