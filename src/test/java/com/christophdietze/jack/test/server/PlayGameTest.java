package com.christophdietze.jack.test.server;

import junit.framework.TestCase;

import org.junit.Test;

import com.christophdietze.jack.client.presenter.ApplicationContext;
import com.christophdietze.jack.client.presenter.MatchInfo;
import com.christophdietze.jack.client.presenter.MatchMode;
import com.christophdietze.jack.common.JackService;
import com.christophdietze.jack.common.JackServiceAsync;
import com.christophdietze.jack.common.MatchStartedRemoteEvent;
import com.christophdietze.jack.common.board.ChessUtils;
import com.christophdietze.jack.common.board.Game;
import com.christophdietze.jack.common.board.Move;
import com.christophdietze.jack.server.JackServiceImpl;
import com.christophdietze.jack.test.AsyncProxyGenerator;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.google.inject.AbstractModule;
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
		Injector injector = Guice.createInjector();
		JackServiceImpl chessService = injector.getInstance(JackServiceImpl.class);

		long login1 = chessService.login();
		chessService.postSeek(login1);

		long login2 = chessService.login();
		chessService.postSeek(login2);

		chessService.makeMove(login2, "e2e4");
		chessService.makeMove(login1, "e7e5");
	}

	@Test
	public void testIssue19() {
		Injector injector = Guice.createInjector(new AbstractModule() {
			@Override
			protected void configure() {
				Injector subInjector = Guice.createInjector();
				JackService impl = subInjector.getInstance(JackServiceImpl.class);
				JackServiceAsync mock = AsyncProxyGenerator.newProxy(JackServiceAsync.class, impl);
				bind(JackServiceAsync.class).toInstance(mock);
			}
		});
		JackServiceImpl chessService = injector.getInstance(JackServiceImpl.class);
		long location1 = chessService.login();
		long location2 = chessService.login();
		chessService.postSeek(location1);
		chessService.postSeek(location2);

		assertEquals(MatchStartedRemoteEvent.class, chessService.poll(location1).getClass());
		assertEquals(MatchStartedRemoteEvent.class, chessService.poll(location2).getClass());

		chessService.abortMatch(location1);
		ApplicationContext applicationContext = injector.getInstance(ApplicationContext.class);
		applicationContext.setLocationId(location2);
		MatchMode matchMode = injector.getInstance(MatchMode.class);
		matchMode.activate(new MatchInfo(location1, location2, true));
		Move m = ChessUtils.toMoveFromAlgebraic("e2e4");
		matchMode.makeMove(m.getFrom(), m.getTo());
		Game game = injector.getInstance(Game.class);
		assertEquals(0, game.getPosition().getPly()); // no move was made because the match is already aborted
	}
}
