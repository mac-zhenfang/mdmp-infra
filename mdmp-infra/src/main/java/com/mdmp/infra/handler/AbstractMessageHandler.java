package com.mdmp.infra.handler;

import java.util.ArrayList;
import java.util.List;


import com.mdmp.infra.expression.Expression;
import com.mdmp.infra.message.Message;

/**
 * @author dream
 * 
 */
public abstract class AbstractMessageHandler implements MessageHandler {
	private List<MessageHandler> childHandler = new ArrayList<MessageHandler>();
	private boolean next = true;

	public AbstractMessageHandler() {

	}

	public AbstractMessageHandler(String param) {

	}

	public void submitMessage(Message message) throws Exception {
		Message nextMsg = null;
		try {
			nextMsg = processMessage(message);
			if (nextMsg == null) {
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (!next) {
				return;
			}
		}
		for (MessageHandler handler : childHandler) {
			handler.submitMessage(nextMsg);
		}
	}

	public void setNext(boolean next) {
		this.next = next;
	}

	@Override
	public void addChinldHanlder(MessageHandler handler) {
		if (handler != null) {
			childHandler.add(handler);
		}
	}

	public List<MessageHandler> getChinldHanlder() {
		return childHandler;
	}
	
	public void init(String logic) {
		initInternal(logic);
		for (MessageHandler handler : childHandler) {
			handler.init(logic);
		}
	}

	public abstract void initInternal(String logic);


}