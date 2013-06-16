package com.mdmp.infra.cache;

public class CacheManager {
	static ReportCache rCache;
	static HandlerCache opCache;
	static MongodbCache mongoCache;
	static {
		rCache = new ReportCache();
		opCache = new HandlerCache();
		mongoCache = new MongodbCache();
	}

	public static ReportCache getReportCacheInstance() {
		return rCache;
	}
	
	public static HandlerCache getHandlerCacheInstance() {
		return opCache;
	}
	
	public static MongodbCache getMongodbCacheInstance() {
		return mongoCache;
	}
}
