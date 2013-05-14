package com.mdmp.infra.operator;

import com.mdmp.infra.message.Message;

public interface MessageOperator{

	public Message processMessage(Message message) throws Exception;
	
	public void submitMessage(Message message) throws Exception;
	
	public void addChinldHanlder(MessageOperator handler);
	
	public void init(String logic);

}