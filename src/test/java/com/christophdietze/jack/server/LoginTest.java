package com.christophdietze.jack.server;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import junit.framework.TestCase;

import com.christophdietze.jack.server.ChessServiceImpl;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.SessionScoped;

public class LoginTest extends TestCase {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	protected void tearDown() throws Exception {
		MockSessionScope.INSTANCE.reset();
	}

	public void test1() {
		log.info("test1 info");
		Injector injector = getInjector();
		ChessServiceImpl chessService = injector.getInstance(ChessServiceImpl.class);
		chessService.login();
		MockSessionScope.INSTANCE.setCurrentSessionId(2);
		chessService.login();
		chessService.login();
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
