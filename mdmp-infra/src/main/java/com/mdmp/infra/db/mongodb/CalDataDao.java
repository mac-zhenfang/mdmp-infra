/**
 * 
 */
package com.mdmp.infra.db.mongodb;

import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author zhefang
 *  
 *  
 * Package Mongo. 
 * 
 * This is to get the data that cal from mdmp-infra
 * 
 * @collection: reportId
 * 
 * list all the k/v for the report, each BSON OBject is this format
 * 
 * {
 * 	ts: 1251221452,
 *  metrics : {
 *  	output1: val,
 *  	output2: val,
 *  	output3: val
 *  }
 * },
 * {
 * 	ts: 1251221452,
 *  metrics : {
 *  	output1: val,
 *  	output2: val,
 *  	output3: val
 *  }
 * }
 * 
 * 
 * 
 * http://www.quora.com/Time-Series/What-is-the-best-way-to-store-time-series-data-in-MongoDB
 */
public interface CalDataDao extends MongoRepository<Metrics, ObjectId>{
	
	static final String TIMESTAMP = "ts";
	
	static final String METRICS = "metrics";
	
	public List<Metrics> listByTimestamp(String collection, long start, long end);
	
	public String getMetric(String collection, long timestamp, String key) throws DataNotFoundException;
	
	public List<Metrics> listAll(String collection);
	
	public void put(String collection, String timestamp, Map<String, Object> kv);
}
