package com.mdmp.infra.sql.args;

import java.util.regex.Pattern;

public class ArgumentFactory {

	public static final String PATTERN_FLOAT = "[\\d]*(\\.?)[\\d]*";
	public static final String PATTERN_INT = "[\\d]+";
	public static final String PATTERN_COLUMN = "[[a-z]|[A-Z]]+";
	public static final char QUOTATION = '\'';
	static Pattern patFloat = Pattern.compile(PATTERN_FLOAT);
	static Pattern patInt = Pattern.compile(PATTERN_INT);
	static Pattern patChar = Pattern.compile(PATTERN_INT);

	public static Argument parseArgument(String arg) {
		int length = arg.length();
		Object value = null;
		Argument tmpArg = null;
		if (QUOTATION == arg.charAt(0) && QUOTATION == arg.charAt(length - 1)) {
			tmpArg = new PrimitiveStringArgument();
		}
		if (isInteger(arg)) {
			try {
				value = Integer.parseInt(arg);
				tmpArg = new PrimitiveIntArgument();
			} catch (Exception e) {
				value = Long.parseLong(arg);
				tmpArg = new PrimitiveLongArgument();
			}
		} else if (isDecimal(arg)) {
			try {
				value = Float.parseFloat(arg);
				tmpArg = new PrimitiveFloatArgument();
			} catch (Exception e) {
				value = Double.parseDouble(arg);
				tmpArg = new PrimitiveDoubleArgument();
			}
		} else if(isColumn(arg)){
			tmpArg = new ColumnArgument();
			tmpArg.setName(arg);
		}
		tmpArg.setValue(value);
		tmpArg.setLength(length);
		return tmpArg;
	}

	public static boolean isDecimal(String str) {
		if (str == null || "".equals(str)) {
			return false;
		}
		return patFloat.matcher(str).matches();
	}

	public static boolean isInteger(String str) {
		if (str == null) {
			return false;
		}
		return patInt.matcher(str).matches();
	}
	
	public static boolean isColumn(String str) {
		if (str == null) {
			return false;
		}
		// FIXME Need to get metadata from cache
		// TODO
		return patChar.matcher(str).matches();
	}
}
