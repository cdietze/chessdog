package com.christophdietze.jack;

import junit.framework.TestCase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Guice;
import com.google.inject.Injector;

public abstract class MyTestCase extends TestCase {

	// protected Log log = LogFactory.getLog(this.getClass());

	protected Logger log = LoggerFactory.getLogger(this.getClass());

	/** Uses lazy initialization */
	private Injector injector = null;

	// @SuppressWarnings("unused")
	// private boolean testOk = true;
	//
	// @Override
	// protected void setUp() throws Exception {
	// super.setUp();
	// }
	//
	// @Override
	// protected void tearDown() throws Exception {
	// super.tearDown();
	// }
	//
	// @Override
	// protected void runTest() throws Throwable {
	// log.info(">>>>>>>>>>>>>>>> " + this.getName());
	// try {
	// super.runTest();
	// } catch (Throwable ex) {
	// testOk = false;
	// log.error("Exception while running test " + this.getName(), ex);
	// throw ex;
	// } finally {
	// log.info("<<<<<<<<<<<<<<<< " + this.getName());
	// }
	// }

	protected void injectMembers() {
		getInjector().injectMembers(this);
	}

	protected Injector getInjector() {
		if (injector == null) {
			GuiceTestModule module = new GuiceTestModule();
			injector = Guice.createInjector(module);
		}
		return injector;
	}

}
