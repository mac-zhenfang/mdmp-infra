package com.mdmp.infra.operator.sink;

import com.mdmp.infra.message.Message;

/**
 * Forward message to another storage system, not change it.
 * 
 * @author dream
 * 
 */
public class MysqlSink extends BasicSink {

	public void forwardMessage(Message msg) {

	}

	@Override
	public void initInternal(String logic) {
		// TODO Auto-generated method stub
		
	}
}
