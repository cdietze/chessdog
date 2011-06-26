package com.christophdietze.jack.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.christophdietze.jack.server.util.ServerConstants;
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
				serve(ServerConstants.SERVLET_JACK_PATH_PREFIX + "/" + ChessServiceImpl.SERVLET_PATH).with(
						ChessServiceImpl.class);

				serve(ServerConstants.SERVLET_JACK_ADMIN_PATH_PREFIX + "/" + AdminServiceImpl.SERVLET_PATH).with(
						AdminServiceImpl.class);

				serve("/cron/cleanup").with(CleanupServlet.class);

				bind(CometServer.class).to(CometServerImpl.class);

				serve("/test/chessdog.js").with(DynamicChessdogEmbedJavascriptServlet.class);
			}
		});
	}
}
