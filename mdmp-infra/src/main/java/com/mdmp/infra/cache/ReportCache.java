package com.mdmp.infra.cache;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.mdmp.infra.bean.Report;

public class ReportCache extends AbstractCache {
	Map<String, Report> reportBuf = new ConcurrentHashMap<String, Report>();

	public Report getReport(Object key) {
		return reportBuf.get(key);
	}

	@Override
	public void putValue(Object key, Object value) {
		String k = (String) key;
		Report v = (Report) value;
		reportBuf.put(k, v);
	}

	@Override
	public void remove(Object key) {
		reportBuf.remove(key);
	}

	@Override
	public Set getKeySet() {
		return reportBuf.keySet();
	}

}
