package com.christophdietze.jack.client;

import com.christophdietze.jack.client.embed.JavaScriptBindings;
import com.christophdietze.jack.client.util.GlobalEventBus;
import com.christophdietze.jack.client.view.embed.MainPanelEmbed;
import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;

@GinModules(MyGinModule.class)
public interface MyGinjector extends Ginjector {

	JavaScriptBindings getJavaScriptBindings();

	MainPanelEmbed getMainPanelEmbed();

	GlobalEventBus getEventBus();
}
