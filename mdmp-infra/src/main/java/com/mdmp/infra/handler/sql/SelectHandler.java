package com.mdmp.infra.handler.sql;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.mdmp.infra.cache.CacheManager;
import com.mdmp.infra.expression.Expression;
import com.mdmp.infra.handler.AbstractMessageHandler;
import com.mdmp.infra.message.JsonMessage;
import com.mdmp.infra.message.Message;

public class SelectHandler  extends AbstractMessageHandler {
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
		String value = "NONE";
		for (Expression exp : expressionList) {
			exp.initVariable(variable);
			// FIXME variable type?
			value = exp.evaluate().getStringValue();
			answer.add(value);
			//System.out.println(value + "   ");
		}
		CacheManager.getMongodbCacheInstance().putValue("aaa", answer);
		return null;
	}

	public static void main(String[] args) {
		FilterHandler filter = new FilterHandler();
		SelectHandler oper = new SelectHandler();
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
