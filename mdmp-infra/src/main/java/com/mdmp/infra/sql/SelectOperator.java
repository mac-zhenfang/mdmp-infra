package com.mdmp.infra.sql;

import neu.sxc.expression.Expression;

import com.mdmp.infra.message.JsonMessage;
import com.mdmp.infra.message.Message;
import com.mdmp.infra.operator.JsonMessageOperator;

public class SelectOperator  extends JsonMessageOperator {
	Expression expression;
	@Override
	public void init(String logic) {
		expression = new Expression(logic);
	}

	@Override
	public Message processMessage(JsonMessage message) throws Exception {
		expression.initVariable(message.toMap());
		expression.evaluate();
		return null;
	}

	public static void main(String[] args) {
		SelectOperator oper = new SelectOperator();
		oper.init("location");
		JsonMessage msg = new JsonMessage(
				"001",
				"{\"name\":\"news\",\"age\":15,\"dsId\":001,\"ip\":\"10.224.192.166\",\"no\":10,\"location\":\"china\", \"GroupByKey\":\"location\"}");
		try {
			System.out.println(oper.processMessage(msg));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
