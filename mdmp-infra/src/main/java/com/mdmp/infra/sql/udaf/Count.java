package com.mdmp.infra.sql.udaf;

import java.math.BigDecimal;

import com.mdmp.infra.expression.tokens.DataType;
import com.mdmp.infra.expression.tokens.Valuable;


public class Count extends Function {

	@Override
	public int getArgumentNum() {
		return -1;
	}

	@Override
	public Object executeFunction(Valuable[] arguments) {
		BigDecimal result;
		if(arguments.length == 0) {
			result = new BigDecimal("0");
		} else {
			result = arguments[0].getNumberValue();
			for(int i=1; i<arguments.length; i++)
				if(result.compareTo(arguments[i].getNumberValue()) < 0)
					result = arguments[i].getNumberValue();
		}
		return result;
	}

	@Override
	public String getName() {
		return "count";
	}

	@Override
	public DataType[] getArgumentsDataType() {
		return new DataType[]{DataType.NUMBER};
	}
}
