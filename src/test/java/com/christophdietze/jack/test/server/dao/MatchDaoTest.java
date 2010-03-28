package com.christophdietze.jack.test.server.dao;

import junit.framework.TestCase;

import org.junit.Test;

import com.christophdietze.jack.server.dao.Location;
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
		Key<Location> player1 = Location.newKey(11);
		Key<Location> player2 = Location.newKey(12);
		Key<Location> player3 = Location.newKey(13);
		Key<Location> player4 = Location.newKey(14);
		Injector injector = Guice.createInjector();
		MatchDao matchDao = injector.getInstance(MatchDao.class);
		assertEquals(0, matchDao.getAllMatches().size());
		matchDao.createMatch(player1, player2);
		assertEquals(1, matchDao.getAllMatches().size());
		matchDao.createMatch(player3, player4);
		assertEquals(2, matchDao.getAllMatches().size());
	}

	@Test
	public void testHasPlayerActiveMatches() throws NoActiveMatchForPlayerException, EntityNotFoundException {
		Key<Location> player1 = Location.newKey(11);
		Key<Location> player2 = Location.newKey(12);
		Key<Location> player3 = Location.newKey(13);
		Key<Location> player4 = Location.newKey(14);

		Injector injector = Guice.createInjector();
		MatchDao matchDao = injector.getInstance(MatchDao.class);
		assertFalse(matchDao.doesLocationHaveActiveMatch(player1));
		matchDao.createMatch(player2, player3);
		assertFalse(matchDao.doesLocationHaveActiveMatch(player1));
		Match match = matchDao.createMatch(player1, player4);
		assertTrue(match.isActive());
		assertTrue(matchDao.doesLocationHaveActiveMatch(player1));
		matchDao.makeLocationsMatchInactive(player1);
		match = ObjectifyTestHelper.ofy().get(new Key<Match>(Match.class, match.getId()));
		assertFalse(match.isActive());
		assertFalse(matchDao.doesLocationHaveActiveMatch(player1));
	}
}
