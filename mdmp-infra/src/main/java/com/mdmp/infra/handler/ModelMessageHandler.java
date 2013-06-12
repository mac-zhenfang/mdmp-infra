package com.mdmp.infra.handler;

import com.mdmp.infra.message.Message;

public class ModelMessageHandler extends AbstractMessageOperator{

	@Override
	public Message processMessage(Message message) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public static String getTypeName() {
		return OperatorFactory.MODEL_HANDLER;
	}

	@Override
	public void initInternal(String logic) {
		// TODO Auto-generated method stub
		
	}
}
