package com.mdmp.infra.cache;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.mdmp.infra.handler.MessageOperator;

public class OperatorCache extends AbstractCache{

	Map<String, MessageOperator> opCache = new ConcurrentHashMap<String, MessageOperator>();
	
	public MessageOperator getOperator(Object key) {
		return opCache.get(key);
	}

	@Override
	public void putValue(Object key, Object value) {
		String k = (String) key;
		MessageOperator op = (MessageOperator)value;
		opCache.put(k, op);
	}

	@Override
	public void remove(Object key) {
		opCache.remove(key);
	}

	@Override
	public Set getKeySet() {
		return opCache.keySet();
	}

}
