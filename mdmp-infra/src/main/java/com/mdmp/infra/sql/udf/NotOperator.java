package com.mdmp.infra.sql.udf;

import com.mdmp.infra.expression.ArgumentsMismatchException;
import com.mdmp.infra.expression.tokens.DataType;
import com.mdmp.infra.expression.tokens.Valuable;

public class NotOperator extends UnaryOperator {

	public NotOperator() {
		super("NOT");
	}

	@Override
	public Object operate(Valuable[] arguments)
			throws ArgumentsMismatchException {
		Object result = null;
		Valuable argument = arguments[0];
		if (argument.getDataType() == DataType.BOOLEAN) {
			result = !argument.getBooleanValue();
		} else {
			throw new ArgumentsMismatchException(arguments, "!");
		}
		return result;
	}

}
