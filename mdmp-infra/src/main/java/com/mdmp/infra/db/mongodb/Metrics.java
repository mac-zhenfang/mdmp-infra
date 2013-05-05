/**
 * 
 */
package com.mdmp.infra.db.mongodb;

import java.util.Map;

/**
 * @author zhefang
 * a set of metric
 */
public class Metrics {
	
	Map<String, String> metric;
	

	public Map<String, String> getMetric() {
		return metric;
	}

	public void setMetric(Map<String, String> metric) {
		this.metric = metric;
	}

	private long ts;

	public long getTs() {
		return ts;
	}

	public void setTs(long ts) {
		this.ts = ts;
	}
}
