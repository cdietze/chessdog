package com.christophdietze.jack.test.server.dao;

import junit.framework.TestCase;

import org.junit.Test;

import com.christophdietze.jack.server.dao.Match;
import com.christophdietze.jack.server.dao.MatchDao;
import com.christophdietze.jack.server.dao.NoActiveMatchForPlayerException;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.googlecode.objectify.Key;

public class MatchDaoTest extends TestCase {

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
	public void testCreate() {
		Injector injector = Guice.createInjector();
		MatchDao matchDao = injector.getInstance(MatchDao.class);
		assertEquals(0, matchDao.getAllMatches().size());
		matchDao.createMatch(2, 3);
		assertEquals(1, matchDao.getAllMatches().size());
		matchDao.createMatch(4, 5);
		assertEquals(2, matchDao.getAllMatches().size());
	}

	@Test
	public void testHasPlayerActiveMatches() throws NoActiveMatchForPlayerException, EntityNotFoundException {
		long playerId = 3;
		Injector injector = Guice.createInjector();
		MatchDao matchDao = injector.getInstance(MatchDao.class);
		assertFalse(matchDao.hasPlayerActiveMatch(playerId));
		matchDao.createMatch(4, 5);
		assertFalse(matchDao.hasPlayerActiveMatch(playerId));
		Match match = matchDao.createMatch(playerId, 6);
		assertTrue(match.isActive());
		assertTrue(matchDao.hasPlayerActiveMatch(playerId));
		matchDao.makePlayersGameInactive(playerId);
		match = ObjectifyTestHelper.ofy().get(new Key<Match>(Match.class, match.getId()));
		assertFalse(match.isActive());
		assertFalse(matchDao.hasPlayerActiveMatch(3));
	}
}
