package com.mdmp.infra.sql;

import org.apache.commons.lang.StringUtils;

import com.mdmp.infra.expression.Expression;
import com.mdmp.infra.handler.AbstractMessageOperator;
import com.mdmp.infra.message.Message;

public class FilterOperator extends AbstractMessageOperator {
	Expression expression;

	@Override
	public Message processMessage(Message message) throws Exception {
		expression.initVariable(message.toMap());
		Boolean pass = expression.evaluate().getBooleanValue();
		if (pass) {
			return message;
		}
		return null;
	}

	@Override
	public void initInternal(String logic) {
		logic = logic.substring(logic.indexOf("WHERE") + 5);
		logic = StringUtils.trim(logic) + ";";
		expression = new Expression(logic);
		expression.lexicalAnalysis();
	}
	
	/*public static void main(String[] args) {
		FilterOperator oper = new FilterOperator();
		oper.init("age > 15");
		JsonMessage msg = new JsonMessage("001", "{age:16}");
		try {
			oper.processMessage(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/
}
