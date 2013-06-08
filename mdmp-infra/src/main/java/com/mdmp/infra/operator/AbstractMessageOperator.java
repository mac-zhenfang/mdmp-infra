package com.mdmp.infra.operator;

import java.util.ArrayList;
import java.util.List;


import com.mdmp.infra.expression.Expression;
import com.mdmp.infra.message.Message;

/**
 * @author dream
 * 
 */
public abstract class AbstractMessageOperator implements MessageOperator {
	private List<MessageOperator> childHandler = new ArrayList<MessageOperator>();
	private boolean next = true;

	public AbstractMessageOperator() {

	}

	public AbstractMessageOperator(String param) {

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
		for (MessageOperator handler : childHandler) {
			handler.submitMessage(nextMsg);
		}
	}

	public void setNext(boolean next) {
		this.next = next;
	}

	@Override
	public void addChinldHanlder(MessageOperator handler) {
		if (handler != null) {
			childHandler.add(handler);
		}
	}

	public List<MessageOperator> getChinldHanlder() {
		return childHandler;
	}
	
	public void init(String logic) {
		initInternal(logic);
		for (MessageOperator handler : childHandler) {
			handler.init(logic);
		}
	}

	public abstract void initInternal(String logic);


}