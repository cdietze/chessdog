package com.christophdietze.jack.client;

import com.christophdietze.jack.client.presenter.ApplicationContext;
import com.christophdietze.jack.client.view.MainView;
import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;

@GinModules(MyGinModule.class)
public interface MyGinjector extends Ginjector {

	Application getApplication();

	ApplicationContext getApplicationContext();

	MainView getMainView();
}
