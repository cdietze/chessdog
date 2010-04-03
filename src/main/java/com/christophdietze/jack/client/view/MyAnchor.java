package com.christophdietze.jack.client.view;

import com.christophdietze.jack.client.resources.MyClientBundle;
import com.christophdietze.jack.client.resources.MyCss;
import com.google.gwt.user.client.ui.Anchor;

public class MyAnchor extends Anchor {

	private static MyCss CSS = MyClientBundle.CSS;

	static {
		CSS.ensureInjected();
	}

	public MyAnchor() {
		super();
		setStyleName(CSS.myLink());
		setHref("#");
	}
}
