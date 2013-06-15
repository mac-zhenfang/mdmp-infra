package com.mdmp.infra.sql;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.mdmp.infra.expression.Expression;
import com.mdmp.infra.handler.AbstractMessageOperator;
import com.mdmp.infra.message.JsonMessage;
import com.mdmp.infra.message.Message;

public class SelectOperator  extends AbstractMessageOperator {
	List<Expression> expressionList;

	public void initInternal(String logic) {
		expressionList = new ArrayList<Expression>();
		logic = logic.substring(logic.indexOf("SELECT") + 6, logic.indexOf("WHERE"));
		String[] logics = logic.split(",");
		for(String miniLogic : logics){
			miniLogic = StringUtils.trim(miniLogic) + ";";
			Expression exp = new Expression(miniLogic);
			exp.lexicalAnalysis();
			expressionList.add(exp);
		}
	}

	@Override
	public Message processMessage(Message message) throws Exception {
		Map<String, Object> variable = message.toMap();
		List<String> answer = new ArrayList<String>();
		for (Expression exp : expressionList) {
			exp.initVariable(variable);
			// FIXME variable type?
			String value = exp.evaluate().toString();
			answer.add(value);
			System.out.println(value + "   ");
		}
		System.out.println();
		return null;
	}

	public static void main(String[] args) {
		FilterOperator filter = new FilterOperator();
		SelectOperator oper = new SelectOperator();
		filter.addChinldHanlder(oper);
		String logic = "SELECT location, age*2 WHERE age > 10";
		filter.init(logic);
		JsonMessage msg = new JsonMessage(
				"001",
				"{\"name\":\"news\",\"age\":15,\"dsId\":001,\"ip\":\"10.224.192.166\",\"no\":10,\"location\":\"china\", \"GroupByKey\":\"location\"}");
		try {
			filter.submitMessage(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
