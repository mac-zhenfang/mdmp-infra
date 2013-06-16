package com.mdmp.infra.handler.sink;

import com.mdmp.infra.handler.AbstractMessageHandler;
import com.mdmp.infra.handler.MessageHandler;
import com.mdmp.infra.message.Message;

/**
 * Forward message to another storage system, not change it.
 * It will return null
 * @author dream
 *
 */
public abstract class BasicSink extends AbstractMessageHandler {
	
	@Override
	public Message processMessage(Message message) throws Exception {
		forwardMessage(message);
		return null;
	}

	public void init(String logic) {
		initInternal(logic);
	}
	
	public abstract void forwardMessage(Message msg);
}
