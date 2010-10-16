package com.christophdietze.jack.test.server.dao;

import java.util.Date;

import junit.framework.TestCase;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.christophdietze.jack.server.dao.Location;
import com.christophdietze.jack.server.dao.LocationDao;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class LocationDaoTest extends TestCase {

	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());

	private Logger log = LoggerFactory.getLogger(this.getClass());

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
		locationDao.delete(location);
		Location location3 = locationDao.findById(location.getId());
		assertNull(location3);
	}

	@Test
	public void testFindByPingTimestamp1() throws Exception {
		Injector injector = Guice.createInjector();
		LocationDao locationDao = injector.getInstance(LocationDao.class);
		assertEquals(0, locationDao.findLocationsWithKeepAliveBefore(new Date(100), 10).size());

		locationDao.createLocation(new Location("grete", new Date(1000)));
		assertEquals(0, locationDao.findLocationsWithKeepAliveBefore(new Date(100), 10).size());
		assertEquals(1, locationDao.findLocationsWithKeepAliveBefore(new Date(2000), 10).size());
	}

	@Test
	public void testFindByPingTimestamp_Limit() throws Exception {
		Injector injector = Guice.createInjector();
		LocationDao locationDao = injector.getInstance(LocationDao.class);

		for (int i = 0; i < 20; ++i) {
			locationDao.createLocation(new Location("grete" + i, new Date(1000 + i)));
		}
		assertEquals(1, locationDao.findLocationsWithKeepAliveBefore(new Date(5000), 1).size());
		assertEquals(10, locationDao.findLocationsWithKeepAliveBefore(new Date(5000), 10).size());
		assertEquals(20, locationDao.findLocationsWithKeepAliveBefore(new Date(5000), 100).size());

		log.info(": " + locationDao.findLocationsWithKeepAliveBefore(new Date(5000), 1).get(0));
	}
}
