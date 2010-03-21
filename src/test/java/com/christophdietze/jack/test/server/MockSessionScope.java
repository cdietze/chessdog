package com.christophdietze.jack.test.server;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.appengine.repackaged.com.google.common.collect.Maps;
import com.google.inject.Key;
import com.google.inject.Provider;
import com.google.inject.Scope;

class MockSessionScope implements Scope {
	public static MockSessionScope INSTANCE = new MockSessionScope();

	private Logger log = LoggerFactory.getLogger(this.getClass());

	private Map<Key<?>, Map<Long, Object>> map = Maps.newHashMap();
	private long currentSessionId = 0L;

	public void setCurrentSessionId(long id) {
		currentSessionId = id;
	}

	public void reset() {
		map.clear();
	}

	@Override
	public <T> Provider<T> scope(final Key<T> key, final Provider<T> provider) {
		return new Provider<T>() {
			@SuppressWarnings("unchecked")
			@Override
			public T get() {
				Map<Long, Object> map2 = map.get(key);
				if (map2 == null) {
					map2 = Maps.newHashMap();
					map.put(key, map2);
				}
				Object instance = map2.get(currentSessionId);
				if (instance == null) {
					instance = provider.get();
					log.info("instantiated new " + instance);
					map2.put(currentSessionId, instance);
				}
				return (T) instance;
			}
		};
	}
}