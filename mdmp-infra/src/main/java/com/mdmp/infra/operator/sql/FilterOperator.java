package com.mdmp.infra.operator.sql;


import java.util.ArrayList;
import java.util.List;

//import com.drap.select.Condition;
import com.mdmp.common.util.StringUtils;
import com.mdmp.infra.message.JsonMessage;
import com.mdmp.infra.message.Message;
import com.mdmp.infra.operator.JsonMessageOperator;

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
	
	@Override
	public void init(String logic) {
		// TODO Auto-generated method stub
		
	}


    /**
     * Checks if the object is of the same class and passes all the conditions.
     * @param objects
     * @param conditions
     * @param objectClass
     * @param list
     */
    private boolean checkClassAndApplyConditions(Object[] objects, Condition[] conditions,
            Class objectClass) {
        List list = new ArrayList();
        for (int i = 0; i < objects.length; i++) {
            Object o = objects[i];
            if (o == null) {
                throw new IllegalArgumentException("Object in data at position " + i + " is null");
            }
            checkClass(objectClass, i, o);
        }
        return passesAllConditions(o, conditions);
    }

    /**
     * @param o
     * @param conditions
     * @return true if all conditions return true when invoking the passes() method with the 
     * object as parameter
     */
    private boolean passesAllConditions(Object o, Condition[] conditions) {
        boolean passedAllConditions = true;
        if (conditions != null && conditions.length > 0) {
            for (int e = 0; e < conditions.length; e++) {
                Condition condition = conditions[e];
                if (condition == null) {
                    throw new IllegalArgumentException("Condition at position " + e + " is null");
                }
                if (!condition.passes(o)) {
                    passedAllConditions = false;
                    break;
                }
            }
        }
        return passedAllConditions;
    }

}
