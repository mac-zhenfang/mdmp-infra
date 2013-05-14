package com.mdmp.infra.operator.sink;

import com.mdmp.infra.message.Message;
import com.mdmp.infra.operator.AbstractMessageOperator;
import com.mdmp.infra.operator.MessageOperator;

/**
 * Forward message to another storage system, not change it.
 * It will return null
 * @author dream
 *
 */
public abstract class BasicSink extends AbstractMessageOperator {
	
	@Override
	public Message processMessage(Message message) throws Exception {
		forwardMessage(message);
		return null;
	}

	public abstract void forwardMessage(Message msg);
}
