package com.mdmp.infra.sql.udf;

import com.mdmp.common.exception.ArgumentsMismatchException;
import com.mdmp.infra.expression.tokens.DataType;
import com.mdmp.infra.expression.tokens.Valuable;

public class AndOperator extends BinaryOperator {

	public AndOperator() {
		super("AND");
	}

	@Override
	public Object operate(Valuable[] arguments)
			throws ArgumentsMismatchException {
		Object result = null;
		Valuable a1 = arguments[0];
		Valuable a2 = arguments[1];
		if (a1.getDataType() == DataType.BOOLEAN
				&& a2.getDataType() == DataType.BOOLEAN) {
			result = a1.getBooleanValue() && a2.getBooleanValue();
		} else {
			throw new ArgumentsMismatchException(arguments, "&&");
		}
		return result;
	}

}
