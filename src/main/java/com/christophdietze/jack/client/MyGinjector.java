package com.christophdietze.jack.client;

import com.christophdietze.jack.client.embed.JavaScriptBindings;
import com.christophdietze.jack.client.presenter.ApplicationContext;
import com.christophdietze.jack.client.util.GlobalEventBus;
import com.christophdietze.jack.client.view.MainPanel;
import com.christophdietze.jack.shared.ChessServiceAsync;
import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;

@GinModules(MyGinModule.class)
public interface MyGinjector extends Ginjector {

	JavaScriptBindings getJavaScriptBindings();

	Application getApplication();

	ApplicationContext getApplicationContext();

	MainPanel getMainPanel();

	ChessServiceAsync getChessServiceAsync();

	GlobalEventBus getEventBus();
}
