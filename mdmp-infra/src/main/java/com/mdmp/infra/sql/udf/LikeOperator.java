package com.mdmp.infra.sql.udf;

import com.mdmp.infra.expression.ArgumentsMismatchException;
import com.mdmp.infra.expression.tokens.DataType;
import com.mdmp.infra.expression.tokens.Valuable;

public class LikeOperator extends BinaryOperator {

	public LikeOperator() {
		super("LIKE");
	}

	@Override
	public Object operate(Valuable[] arguments)
			throws ArgumentsMismatchException {
		Object result = null;
		Valuable a1 = arguments[0];
		Valuable a2 = arguments[1];
		if (a1.getDataType() == DataType.STRING
				&& a2.getDataType() == DataType.STRING) {
			String str = a1.getStringValue().toLowerCase();
		    String expr = a2.getStringValue().toLowerCase(); // ignoring locale for now
		    expr = expr.replace(".", "\\."); // "\\" is escaped to "\" (thanks, Alan M)
		    // ... escape any other potentially problematic characters here
		    expr = expr.replace("?", ".");
		    expr = expr.replace("%", ".*");
		    result = str.matches(expr);
		} else {
			throw new ArgumentsMismatchException(arguments, "like");
		}
		return result;
	}

}
