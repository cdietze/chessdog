package com.christophdietze.jack.client.admin.client;

import com.christophdietze.jack.client.MyGinModule;
import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;

@GinModules(MyGinModule.class)
public interface MyAdminGinjector extends Ginjector {

	AdminView getAdminView();
}
