package com.christophdietze.jack.test.server;

import junit.framework.TestCase;

import org.junit.Test;

import com.christophdietze.jack.server.ChessServiceImpl;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.google.inject.Guice;
import com.google.inject.Injector;

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
		SessionTestHelper sessionHelper = new SessionTestHelper();
		Injector injector = Guice.createInjector(sessionHelper.getModule());
		ChessServiceImpl chessService = injector.getInstance(ChessServiceImpl.class);

		final long session1 = 1;
		final long session2 = 2;

		sessionHelper.setCurrentSessionId(session1);
		chessService.login();
		chessService.postSeek();
		sessionHelper.setCurrentSessionId(session2);
		chessService.login();
		chessService.postSeek();

		chessService.makeMove("e2e4");
		sessionHelper.setCurrentSessionId(session1);
		chessService.makeMove("e7e5");
	}
}
