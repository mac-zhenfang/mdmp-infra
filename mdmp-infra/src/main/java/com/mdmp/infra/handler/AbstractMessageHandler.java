package com.mdmp.infra.handler;

import com.mdmp.infra.messager.Message;

/**
 * @author dream
 *
 */
public abstract class AbstractMessageHandler implements MessageHandler {
	private AbstractMessageHandler nextHandler = null;
	private boolean next = true;

	public void submitMessage(Message message) throws Exception {
		Message nextMsg = null;
		try {
			nextMsg = processMessage(message);
			if (nextMsg == null) {
				return;
			}
		} catch (Exception e) {
			if (!next) {
				return;
			}
		}
		this.nextHandler.submitMessage(nextMsg);
	}

	public void setNextHandler(AbstractMessageHandler nextHandler) {
		this.nextHandler = nextHandler;
	}

	public void setNext(boolean next) {
		this.next = next;
	}
}