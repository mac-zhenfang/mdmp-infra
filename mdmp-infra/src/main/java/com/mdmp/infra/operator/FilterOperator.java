package com.mdmp.infra.operator;


import com.mdmp.common.util.StringUtils;
import com.mdmp.infra.message.JsonMessage;
import com.mdmp.infra.message.Message;

public class FilterOperator extends JsonMessageOperator {
	public static final String AND = "and";
	@Override
	public Message processMessage(JsonMessage message) throws Exception {
		String whereString = ((String) message.getValue("where")).toLowerCase();
		if(StringUtils.isEmpty(whereString)){
			return message;
		}
		String[] whereList = whereString.split(AND);
		
		return null;
	}

}
