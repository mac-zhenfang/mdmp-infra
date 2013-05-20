/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mdmp.infra.sql.udf.generic;

import com.mdmp.common.exception.UDFArgumentException;

/**
 * GenericUDF Base Class for operations.
 */
public abstract class GenericUDFBaseCompare extends GenericUDF {
	public enum CompareType {
		// Now only string, text, int, long, byte and boolean comparisons are
		// treated as special cases.
		// For other types, we reuse Utils.compare()
		COMPARE_STRING, COMPARE_TEXT, COMPARE_INT, COMPARE_LONG, COMPARE_BYTE, COMPARE_BOOL, SAME_TYPE, NEED_CONVERT
	}

	protected String opName;
	protected String opDisplayName;

	protected Object[] argumentOIs;

	// protected Return Resolver conversionHelper = null;
	protected Object compareOI;
	protected CompareType compareType;
	// protected Converter converter0, converter1;
	protected String soi0, soi1;
	protected Integer ioi0, ioi1;
	protected Long loi0, loi1;
	protected Byte byoi0, byoi1;
	protected Boolean boi0, boi1;
	protected boolean result = false;

	@Override
	public Object initialize(Object[] arguments) throws UDFArgumentException {

		if (arguments.length != 2) {
			throw new UDFArgumentException(opName + " requires two arguments.");
		}

		argumentOIs = arguments;

		/*
		 * for (int i = 0; i < arguments.length; i++) { Category category =
		 * arguments[i].getCategory(); if (category != Category.PRIMITIVE) {
		 * throw new UDFArgumentTypeException(i, "The " +
		 * GenericUDFUtils.getOrdinal(i + 1) + " argument of " + opName +
		 * "  is expected to a " + Category.PRIMITIVE.toString().toLowerCase() +
		 * " type, but " + category.toString().toLowerCase() + " is found"); } }
		 */
		if (arguments[0].getClass().isInstance(String.class)
				&& arguments[1].getClass().isInstance(String.class)) {
			soi0 = (String) arguments[0];
			soi1 = (String) arguments[1];
			compareType = CompareType.COMPARE_STRING;
		} else if (arguments[0] instanceof Integer
				&& arguments[1] instanceof Integer) {
			compareType = CompareType.COMPARE_INT;
			ioi0 = (Integer) arguments[0];
			ioi1 = (Integer) arguments[1];
		} else if (arguments[0] instanceof Long && arguments[1] instanceof Long) {
			compareType = CompareType.COMPARE_LONG;
			loi0 = (Long) arguments[0];
			loi1 = (Long) arguments[1];
		} else if (arguments[0] instanceof Byte && arguments[1] instanceof Byte) {
			compareType = CompareType.COMPARE_BYTE;
			byoi0 = (Byte) arguments[0];
			byoi1 = (Byte) arguments[1];
		} else if (arguments[0] instanceof Boolean
				&& arguments[1] instanceof Boolean) {
			compareType = CompareType.COMPARE_BOOL;
			boi0 = (Boolean) arguments[0];
			boi1 = (Boolean) arguments[1];
		} else {
			// TODO type convert
			/*
			 * TypeInfo oiTypeInfo0 = TypeInfoUtils.getTypeInfoFrom
			 * (arguments[0]); TypeInfo oiTypeInfo1 =
			 * TypeInfoUtils.getTypeInfoFrom (arguments[1]);
			 * 
			 * if (oiTypeInfo0 != oiTypeInfo1) { compareType =
			 * CompareType.NEED_CONVERT; compareOI =
			 * TypeInfoUtils.getStandardWritable FromTypeInfo(
			 * TypeInfoFactory.doubleTypeInfo); converter0 =
			 * Converters.getConverter(arguments[0], compareOI); converter1 =
			 * Converters.getConverter(arguments[1], compareOI); } else {
			 * compareType = CompareType.SAME_TYPE; }
			 */
		}
		return null;

	}

	@Override
	public String getDisplayString(String[] children) {
		assert (children.length == 2);
		return "(" + children[0] + " " + opDisplayName + " " + children[1]
				+ ")";

	}

}
