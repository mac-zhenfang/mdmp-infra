package com.mdmp.infra.sql;

import com.mdmp.infra.message.JsonMessage;
import com.mdmp.infra.message.Message;
import com.mdmp.infra.operator.JsonMessageOperator;

public class GroupbyOperator extends JsonMessageOperator {
	public static final String Delimiter = ",";
	private static String groupKeys;

	@Override
	public Message processMessage(JsonMessage message) throws Exception {
		boolean pass = true;
		for (String k : groupKeys.split(Delimiter)) {
			pass = message.getValue(k) == null ? false : true;
			if (!pass) {
				break;
			}
		}
		if (pass) {
			message.putValue("GroupByKey", groupKeys);
			return message;
		}
		return null;
	}

	@Override
	public void initInternal(String logic) {
		groupKeys = logic;
	}

	public static String[] getGroupKey() {
		return groupKeys.split(Delimiter);
	}

	public static void main(String[] args) {
		GroupbyOperator oper = new GroupbyOperator();
		oper.init("location");
		JsonMessage msg = new JsonMessage(
				"001",
				"{\"name\":\"news\",\"age\":15,\"dsId\":001,\"ip\":\"10.224.192.166\",\"no\":10,\"location\":\"china\"}");
		try {
			System.out.println(oper.processMessage(msg));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
