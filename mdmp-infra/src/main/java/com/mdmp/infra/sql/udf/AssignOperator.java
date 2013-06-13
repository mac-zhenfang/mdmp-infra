package com.mdmp.infra.sql.udf;

import com.mdmp.common.exception.ArgumentsMismatchException;
import com.mdmp.infra.expression.tokens.Valuable;
import com.mdmp.infra.expression.tokens.VariableToken;

public class AssignOperator extends BinaryOperator {

	public AssignOperator() {
		super("ASSIGN");
	}

	@Override
	public Object operate(Valuable[] arguments)
			throws ArgumentsMismatchException {
		VariableToken variable = (VariableToken) arguments[0];
		Valuable value = arguments[1];
		//若变量未定义，直接赋值，若已定义，则先判断数据类型是否匹配再赋值
		if (variable.getValue() == null) {
			variable.assignWith(value);
		} else if (variable.getDataType() == value.getDataType()) {
			variable.assignWith(value);
		} else
			throw new ArgumentsMismatchException(
					"Type mismatch in assignment: cannot convert from "
							+ value.getDataType().name() + " to "
							+ variable.getDataType().name() + ".");
		return variable.getValue();
	}

}
