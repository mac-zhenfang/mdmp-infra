package com.mdmp.infra.handler.forward;

import com.mdmp.infra.handler.AbstractMessageHandler;
import com.mdmp.infra.handler.MessageHandler;
import com.mdmp.infra.messager.Message;

/**
 * Forward message to another storage system, not change it.
 * It will return null
 * @author dream
 *
 */
public abstract class ForwardMessageHandler extends AbstractMessageHandler {
	
	@Override
	public Message processMessage(Message message) throws Exception {
		forwardMessage(message);
		return null;
	}

	public abstract void forwardMessage(Message msg);
}
