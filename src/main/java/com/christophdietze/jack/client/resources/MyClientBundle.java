package com.christophdietze.jack.client.resources;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.ImageResource;

public interface MyClientBundle extends ClientBundle {

	static MyClientBundle INSTANCE = GWT.create(MyClientBundle.class);

	static MyCss CSS = INSTANCE.myCss();

	MyCss myCss();

	CssResource gwtDndOverrides();

	ImageResource blackPlayerIcon();

	ImageResource whitePlayerIcon();

}
