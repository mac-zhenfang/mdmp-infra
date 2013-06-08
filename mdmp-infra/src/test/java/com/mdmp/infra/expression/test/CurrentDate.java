package com.mdmp.infra.expression.test;

import java.util.Calendar;
import java.util.Date;

import com.mdmp.infra.expression.syntax.function.Function;
import com.mdmp.infra.expression.tokens.DataType;
import com.mdmp.infra.expression.tokens.Valuable;


public class CurrentDate extends Function {
	@Override
	public String getName() {
		return "getDate";
	}
	
	@Override
	public int getArgumentNum() {
		return 0;
	}
	
	@Override
	public DataType[] getArgumentsDataType() {
		return null;
	}
	
	@Override
	protected Object executeFunction(Valuable[] arguments) {
		Calendar date = Calendar.getInstance();
		date.setTime(new Date());
		return date;
	}
}
