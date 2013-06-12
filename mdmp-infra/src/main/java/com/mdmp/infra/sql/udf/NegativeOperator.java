package com.mdmp.infra.sql.udf;

import java.math.BigDecimal;

import com.mdmp.infra.expression.ArgumentsMismatchException;
import com.mdmp.infra.expression.tokens.DataType;
import com.mdmp.infra.expression.tokens.Valuable;



public class NegativeOperator extends UnaryOperator {

	public NegativeOperator() {
		super("NEGATIVE");
	}

	@Override
	public Object operate(Valuable[] arguments)
			throws ArgumentsMismatchException {
		Object result = null;
		Valuable argument = arguments[0];
		if (argument.getDataType() == DataType.NUMBER) {
			result = new BigDecimal("0").subtract(argument.getNumberValue());
		} else {
			throw new ArgumentsMismatchException(arguments, "-");
		}
		return result;
	}

}
