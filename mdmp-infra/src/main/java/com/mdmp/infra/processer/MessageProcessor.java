package com.mdmp.infra.processer;

import com.mdmp.infra.messager.Message;

public interface MessageProcessor{

	public void processMessage(Message message) throws Exception;
	
}