package com.christophdietze.jack.test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class AsyncProxyGenerator {

	@SuppressWarnings("unchecked")
	public static <T> T newProxy(Class<T> asyncInterface, final Object impl) {
		InvocationHandler handler = new InvocationHandler() {
			@Override
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				Class[] paramTypes = Arrays.copyOf(method.getParameterTypes(), method.getParameterTypes().length - 1);
				Method implMethod = impl.getClass().getMethod(method.getName(), paramTypes);
				Object[] implArgs = Arrays.copyOf(args, args.length - 1);
				Object result = implMethod.invoke(impl, implArgs);
				AsyncCallback callback = (AsyncCallback) args[args.length - 1];
				callback.onSuccess(result);
				return Void.TYPE;
			}
		};
		T proxy = (T) Proxy.newProxyInstance(AsyncProxyGenerator.class.getClassLoader(), new Class[] { asyncInterface },
				handler);
		return proxy;
	}
}
