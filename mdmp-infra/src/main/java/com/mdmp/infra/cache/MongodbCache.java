package com.mdmp.infra.cache;

import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class MongodbCache extends AbstractCache{
	ConcurrentHashMap<Object, Object> cache = new ConcurrentHashMap<Object, Object>();
	public Object getValue(Object key) {
		return cache.get(key);
	}

	@Override
	public void putValue(Object key, Object value) {
		cache.put(key, value);
	}

	@Override
	public void remove(Object key) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Set getKeySet() {
		// TODO Auto-generated method stub
		return null;
	}

}
