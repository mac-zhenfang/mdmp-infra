package com.mdmp.infra.operator.sink;

import javax.annotation.Resource;

import com.mdmp.infra.db.mongodb.CalDataDao;
import com.mdmp.infra.message.Message;
/**
 * Forward message to another storage system, not change it.
 * @author dream
 *
 */
public class MongoSink extends BasicSink {
	@Resource(name="mongodbService")
	private CalDataDao mongodb;
	
	public void forwardMessage(Message msg){
		//mongodb.put(msg.getDataSourceId(), msg.getTimeStamp(), msg);
	}

	@Override
	public void init(String logic) {
		// TODO Auto-generated method stub
		
	}
}
