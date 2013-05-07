/**
 * 
 */
package com.mdmp.infra.db.mongodb;

import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;

/**
 * @author zhefang
 * 
 */
@Service("mongodbService")
public class CalDataDaoImpl implements CalDataDao {

	MongoTemplate mongoTemplate;

	public void setMongoTemplate(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	@Override
	public List<Metrics> listByTimestamp(String collection, long start, long end) {
		return this.mongoTemplate.find(new Query(new Criteria()
				.where(TIMESTAMP).gte(start).lte(end)), Metrics.class,
				collection);
	}

	@Override
	public String getMetric(String collection, long timestamp, String key)
			throws DataNotFoundException {
		Metrics metrics = this.mongoTemplate.findOne(new Query(new Criteria()
				.where(TIMESTAMP).gte(timestamp).lte(timestamp)),
				Metrics.class, collection);
		String retMet = null;
		for (String k : metrics.getMetric().keySet()) {
			if (k.equals(key)) {
				retMet = metrics.getMetric().get(k);
			}
		}
		if (retMet == null) {
			throw new DataNotFoundException("no metric found");
		}
		return retMet;
	}

	@Override
	public List<Metrics> listAll(String collection) {
		return this.mongoTemplate.findAll(Metrics.class, collection);
	}

	@Override
	public List<Metrics> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Metrics> findAll(Sort arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Metrics> List<S> save(Iterable<S> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Metrics> findAll(Pageable arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long count() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void delete(ObjectId arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Metrics arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Iterable<? extends Metrics> arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean exists(ObjectId arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Iterable<Metrics> findAll(Iterable<ObjectId> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Metrics findOne(ObjectId arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Metrics> S save(S arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void put(String collection, String timestamp, Map<String, Object> kv) {
		DBCollection caldata = db.getCollection("caldata");
		for (int i = 0; i < 1000; i++) {
			BasicDBObject metrics = new BasicDBObject();
			metrics.put("ts", System.currentTimeMillis());
			BasicDBObject metric = new BasicDBObject();
			metric.put("key1", 1);
			metric.put("key2", 1);
			metric.put("key3", 1);
			metric.put("key4", 1);
			metrics.put("metric", metric);
			caldata.insert(metrics);
		}
	}
}
