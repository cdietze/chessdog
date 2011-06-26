package com.christophdietze.jack.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.servlet.ServletModule;

public class MyGuiceServletConfig extends GuiceServletContextListener {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	protected Injector getInjector() {
		log.debug("creating injector");
		return Guice.createInjector(new ServletModule() {
			@Override
			protected void configureServlets() {
				serve("/test/chessdog.js").with(DynamicChessdogEmbedJavascriptServlet.class);
			}
		});
	}
}
