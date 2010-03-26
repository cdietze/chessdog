package com.christophdietze.jack.test.server.dao;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;

class ObjectifyTestHelper {

	private static Objectify lazyOfy;

	public static Objectify ofy() {
		if (lazyOfy == null) {
			lazyOfy = ObjectifyService.begin();
		}
		return lazyOfy;
	}
}
