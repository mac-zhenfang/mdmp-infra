package com.mdmp.infra.handler;

import com.mdmp.infra.message.Message;

public interface MessageHandler{

	public Message processMessage(Message message) throws Exception;
	
	public void submitMessage(Message message) throws Exception;
	
	public void addChinldHanlder(MessageHandler handler);
	
	public void init(String logic);

}