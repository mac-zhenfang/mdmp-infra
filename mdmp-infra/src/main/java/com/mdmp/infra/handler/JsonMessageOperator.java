package com.mdmp.infra.handler;

import com.mdmp.infra.message.JsonMessage;
import com.mdmp.infra.message.Message;

/**
 * @author dream
 *
 */
public abstract class JsonMessageOperator extends AbstractMessageHandler {
	
//	public void submitMessage(Message message) throws Exception {
//		if(Message.MSG_JSON != message.getType()){
//			return;
//		}
//		JsonMessage jsonMsg = (JsonMessage) message;
//		Message nextMsg = null;
//		try {
//			nextMsg = processMessage(jsonMsg);
//			if (nextMsg == null) {
//				return;
//			}
//		} catch (Exception e) {
//			if (!next) {
//				return;
//			}
//		}
//		for(MessageOperator handler : getChinldHanlder()){
//			handler.submitMessage(nextMsg);
//		}
//	}
//	
	@Override
	public Message processMessage(Message message) throws Exception{
		if(Message.MSG_JSON != message.getType()){
			return message;
		}
		return processMessage((JsonMessage) message);
	}
	
	public abstract Message processMessage(JsonMessage message) throws Exception;
}