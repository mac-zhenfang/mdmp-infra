package com.mdmp.infra.sql.args;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import com.mdmp.infra.util.ReflectionUtils;

public class ArgumentFactory {

	public static final String PATTERN_FLOAT = "[\\d]*(\\.?)[\\d]*";
	public static final String PATTERN_INT = "[\\d]+";
	public static final String PATTERN_COLUMN = "[a-zA-Z]+";
	public static final char QUOTATION = '\'';
	static Pattern patFloat = Pattern.compile(PATTERN_FLOAT);
	static Pattern patInt = Pattern.compile(PATTERN_INT);
	static Pattern patChar = Pattern.compile(PATTERN_COLUMN);
	static Map<String, Class<? extends Argument>> argumentsMap = new HashMap();
	static{
		argumentsMap.put("int", PrimitiveIntArgument.class);
	}
	public static Argument parseArgument(String arg) {
		arg = arg.trim();
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
			tmpArg = getColumnArgument(arg);
			tmpArg.setColumn(true);
			tmpArg.setName(arg);
		}
		tmpArg.setValue(value);
		tmpArg.setLength(length);
		return tmpArg;
	}

	private static Argument getColumnArgument(String columnName) {
		// TODO Get column type from cache
		String type = "int";
		
		//
		return ReflectionUtils.newInstance(argumentsMap.get(type));
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
