package com.christophdietze.jack.test.server.dao;

import junit.framework.TestCase;

import org.junit.Test;

import com.christophdietze.jack.server.dao.Location;
import com.christophdietze.jack.server.dao.LocationDao;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class LocationDaoTest extends TestCase {

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
	public void testCreate() throws Exception {
		Injector injector = Guice.createInjector();
		LocationDao locationDao = injector.getInstance(LocationDao.class);

		Location location = locationDao.createLocation("horst");
		assertEquals("horst", location.getNickname());
	}

	@Test
	public void testDelete() throws Exception {
		Injector injector = Guice.createInjector();
		LocationDao locationDao = injector.getInstance(LocationDao.class);

		String nick = "peter";
		Location location = locationDao.createLocation(nick);
		Location location2 = locationDao.getById(location.getId());
		assertEquals(location.getId(), location2.getId());
		locationDao.delete(location.getId());
		Location location3 = locationDao.findById(location.getId());
		assertNull(location3);
	}
}
