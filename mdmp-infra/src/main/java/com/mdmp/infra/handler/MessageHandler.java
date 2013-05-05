package com.mdmp.infra.handler;

import com.mdmp.infra.messager.Message;

public interface MessageHandler{

	public Message processMessage(Message message) throws Exception;
	
	public void submitMessage(Message message) throws Exception;
	
}