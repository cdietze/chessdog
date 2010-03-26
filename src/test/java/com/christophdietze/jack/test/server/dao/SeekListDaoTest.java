package com.christophdietze.jack.test.server.dao;

import junit.framework.TestCase;

import org.junit.Test;

import com.christophdietze.jack.server.dao.SeekListDao;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class SeekListDaoTest extends TestCase {

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
	public void testAddAndPop() {
		long userId1 = 5;
		long userId2 = 7;
		Injector injector = Guice.createInjector();
		SeekListDao dao = injector.getInstance(SeekListDao.class);
		// first add some users
		assertEquals(0, dao.getAllSeekers().size());
		assertFalse(dao.isSeeking(userId1));
		dao.addSeeker(userId1);
		assertTrue(dao.isSeeking(userId1));
		assertEquals(1, dao.getAllSeekers().size());
		dao.addSeeker(userId2);
		assertEquals(2, dao.getAllSeekers().size());
		// then remove them again
		assertEquals(Long.valueOf(userId1), dao.popSeeker());
		assertFalse(dao.isSeeking(userId1));
		assertEquals(1, dao.getAllSeekers().size());
		assertEquals(Long.valueOf(userId2), dao.popSeeker());
		assertEquals(0, dao.getAllSeekers().size());
	}

}
