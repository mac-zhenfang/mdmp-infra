package com.mdmp.infra.processer.forward;

import com.mdmp.infra.messager.Message;
import com.mdmp.infra.processer.MessageProcessor;

public abstract class ForwardMessageProcessor implements MessageProcessor {
	
	@Override
	public void processMessage(Message message) throws Exception {
		forwardMessage(message);
	}

	public abstract void forwardMessage(Message msg);
}
