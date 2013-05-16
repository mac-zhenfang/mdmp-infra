package com.mdmp.infra.cache;

import java.util.Set;

public interface Cache {

//	public void init();
	
	public void putValue(Object key, Object value);

	public void remove(Object key);

	public Set getKeySet();
}
