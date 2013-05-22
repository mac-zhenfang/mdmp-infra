package com.mdmp.infra.sql;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.mdmp.common.util.StringUtils;
import com.mdmp.infra.message.JsonMessage;
import com.mdmp.infra.message.Message;
import com.mdmp.infra.operator.JsonMessageOperator;
import com.mdmp.infra.sql.args.Argument;
import com.mdmp.infra.sql.args.ArgumentFactory;
import com.mdmp.infra.sql.udf.generic.GenericUDFBaseCompare;

public class FilterOperator extends JsonMessageOperator {
	public static final String AND = "and";
	static Map<String, GenericUDFBaseCompare> compareFuncList = new LinkedHashMap<String, GenericUDFBaseCompare>();

	@Override
	public Message processMessage(JsonMessage message) throws Exception {
//		String whereString = ((String) message.getValue("where")).toLowerCase();
//		if (StringUtils.isEmpty(whereString)) {
//			return message;
//		}
		Boolean pass = true;
		for (String k : compareFuncList.keySet()) {
			GenericUDFBaseCompare func = compareFuncList.get(k);
			pass = (Boolean) func.evaluate(message);
			if (!pass) {
				break;
			}
		}
		if(pass){
			return message;
		}
		return null;
	}

	@Override
	public void init(String logic) {
		try {
			parse(logic);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	public void parse(String exp) throws InstantiationException,
			IllegalAccessException {
		exp = exp.toLowerCase();
		String[] sExp = exp.split("and");
		for (String e : sExp) {
			if (e.contains(">=")) {
				parseInternal(">=", e);
			} else if (e.contains("<=")) {
				parseInternal(">=", e);
			} else if (e.contains(">")) {
				parseInternal(">", e);
			} else if (e.contains("<")) {
				parseInternal("<", e);
			} else if (e.contains("=")) {
				parseInternal("=", e);
			} else if (e.contains("!=")) {
				parseInternal("!=", e);
			}
		}
	}
	
	private void parseInternal(String functionName, String e) throws InstantiationException, IllegalAccessException{
		FunctionInfo fI = FunctionRegistry.getFunctionInfo(functionName);
		GenericUDFBaseCompare compareFunc = (GenericUDFBaseCompare) fI.getGenericUDF();
		List<Argument> arguments = new ArrayList<Argument>();
		String[] args = e.split(functionName);
		for (String arg : args) {
			Argument newArg = ArgumentFactory.parseArgument(arg);
			arguments.add(newArg);
		}
		compareFunc.initialize(arguments.toArray(new Argument[arguments.size()]));
		compareFuncList.put(functionName, compareFunc);
	}

	/**
	 * Checks if the object is of the same class and passes all the conditions.
	 * 
	 * @param objects
	 * @param conditions
	 * @param objectClass
	 * @param list
	 */
	/*
	 * private boolean checkClassAndApplyConditions(Object[] objects,
	 * Condition[] conditions, Class objectClass) { List list = new ArrayList();
	 * for (int i = 0; i < objects.length; i++) { Object o = objects[i]; if (o
	 * == null) { throw new
	 * IllegalArgumentException("Object in data at position " + i + " is null");
	 * } checkClass(objectClass, i, o); } return passesAllConditions(o,
	 * conditions); }
	 *//**
	 * @param o
	 * @param conditions
	 * @return true if all conditions return true when invoking the passes()
	 *         method with the object as parameter
	 */
	/*
	 * private boolean passesAllConditions(Object o, Condition[] conditions) {
	 * boolean passedAllConditions = true; if (conditions != null &&
	 * conditions.length > 0) { for (int e = 0; e < conditions.length; e++) {
	 * Condition condition = conditions[e]; if (condition == null) { throw new
	 * IllegalArgumentException("Condition at position " + e + " is null"); } if
	 * (!condition.passes(o)) { passedAllConditions = false; break; } } } return
	 * passedAllConditions; }
	 */
	public static void main(String[] args) {
		FilterOperator oper = new FilterOperator();
		oper.init("age = 15");
		JsonMessage msg = new JsonMessage("001", "{age:15}");
		try {
			oper.processMessage(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
