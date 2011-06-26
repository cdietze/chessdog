package com.christophdietze.jack.server;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

import com.google.inject.Singleton;

/**
 * TODO set cache headers correctly. Maybe ETAG and Cachecontrol with a max-age of 600 or so.
 */
@Singleton
public class DynamicChessdogEmbedJavascriptServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String filename = "chessdog.embed.src.js";
		String host = req.getHeader("Host");
		InputStream contentIS = this.getClass().getClassLoader().getResourceAsStream(filename);
		String content = IOUtils.toString(contentIS);
		IOUtils.closeQuietly(contentIS);
		content = content.replaceAll("\\$\\{Host\\}", host);
		resp.setContentType("text/javascript");
		resp.setCharacterEncoding("UTF-8");
		resp.getWriter().print(content);
	}
}
