package com.mdmp.infra.cache;

public class CacheManager {
	static ReportCache rCache;
	static OperatorCache opCache;
	static {
		rCache = new ReportCache();
		opCache = new OperatorCache();
	}

	public static ReportCache getReportCacheInstance() {
		return rCache;
	}
	
	public static OperatorCache getHandlerCacheInstance() {
		return opCache;
	}
}
