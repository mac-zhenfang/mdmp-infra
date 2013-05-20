package com.mdmp.infra.sql;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

//import com.drap.select.Condition;
import com.mdmp.common.util.StringUtils;
import com.mdmp.infra.message.JsonMessage;
import com.mdmp.infra.message.Message;
import com.mdmp.infra.operator.JsonMessageOperator;
import com.mdmp.infra.sql.udf.generic.GenericUDAFEvaluator;
import com.mdmp.infra.sql.udf.generic.GenericUDAFResolver;
import com.mdmp.infra.sql.udf.generic.GenericUDFBaseCompare;

public class FilterOperator extends JsonMessageOperator {
	public static final String AND = "and";
	static Map<String, GenericUDFBaseCompare> mFunctions = new LinkedHashMap<String, GenericUDFBaseCompare>();

	@Override
	public Message processMessage(JsonMessage message) throws Exception {
		String whereString = ((String) message.getValue("where")).toLowerCase();
		if (StringUtils.isEmpty(whereString)) {
			return message;
		}
		for (String k : mFunctions.keySet()) {
			GenericUDFBaseCompare func = mFunctions.get(k);
			/*GenericUDAFResolver resolver = FunctionRegistry.getGenericUDAFResolver(k);
			GenericUDAFEvaluator evaluator = resolver.getEvaluator(parameters);*/
			Boolean pass = (Boolean) func.evaluate("");
			if (!pass) {
				break;
			}
		}
		return null;
	}

	@Override
	public void init(String logic) {
		parse(logic);
	}

	public void parse(String exp) {
		exp = exp.toLowerCase();
		String[] sExp = exp.split("and");
		String[] para;
		for (String e : sExp) {
			if (e.contains(">=")) {
				String functionName = ">=";
				FunctionInfo fI = FunctionRegistry
						.getFunctionInfo(functionName);
				GenericUDFBaseCompare func = null; 
				String[] args = e.split(functionName);
				func.initialize(arguments);
				mFunctions.put(functionName, func);
			} else if (e.contains("<=")) {
				para = e.split("<=");
			} else if (e.contains(">")) {
				para = e.split(">");
			} else if (e.contains("<")) {
				para = e.split("<");
			} else if (e.contains("=")) {
				para = e.split("=");
			} else if (e.contains("!=")) {
				para = e.split("!=");
			}
		}
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

}
