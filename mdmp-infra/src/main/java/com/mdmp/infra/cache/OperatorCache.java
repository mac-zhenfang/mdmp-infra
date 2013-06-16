package com.mdmp.infra.cache;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.mdmp.infra.bean.Report;
import com.mdmp.infra.handler.MessageHandler;
import com.mdmp.infra.handler.sql.FilterHandler;
import com.mdmp.infra.handler.sql.SelectHandler;

public class OperatorCache extends AbstractCache{

	Map<String, MessageHandler> opCache = new ConcurrentHashMap<String, MessageHandler>();
	
	public MessageHandler getHandler(String reportId) {
		FilterHandler filter = new FilterHandler();
		SelectHandler oper = new SelectHandler();
		filter.addChinldHanlder(oper);
		String logic = "SELECT location, age*2 WHERE age > 10";
		filter.init(logic);
		return filter;
	}

	@Override
	public void putValue(Object key, Object value) {
		String k = (String) key;
		MessageHandler op = (MessageHandler)value;
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
