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

import com.mdmp.common.exception.MDMPException;
import com.mdmp.infra.message.Message;

/**
 * GenericUDF Class for operation EQUAL.
 */
public class GenericUDFOPEqual extends GenericUDFBaseCompare {
	public GenericUDFOPEqual() {
		this.opName = "EQUAL";
		this.opDisplayName = "=";
	}

	@Override
	public Object evaluate(Message msg) throws MDMPException {
		/*Object o0, o1;
		o0 = arguments[0];
		if (o0 == null) {
			return null;
		}
		o1 = arguments[1];
		if (o1 == null) {
			return null;
		}*/
		if(msg == null){
			return null;
		}

		switch (compareType) {
		case COMPARE_INT:
			result = ioi0.getValue(msg) == ioi1.getValue(msg);
			break;
		case COMPARE_LONG:
			result = loi0.getValue(msg) == loi1.getValue(msg);
			break;
		/*case COMPARE_BYTE:
			result=byoi0.getValue()== byoi1.getValue();
			break;
		case COMPARE_BOOL:
			result=boi0.getValue() == boi1.getValue();
			break;*/
		case COMPARE_STRING:
			result = soi0.getValue(msg).equals(soi1.getValue(msg));
			break;
		case SAME_TYPE:
			/*result=ObjectInspectorUtils.compare(o0, argumentOIs[0], o1,
					argumentOIs[1]) == 0;*/
			break;
		default:
			/*Object converted_o0 = converter0.convert(o0);
			if (converted_o0 == null) {
				return null;
			}
			Object converted_o1 = converter1.convert(o1);
			if (converted_o1 == null) {
				return null;
			}
			result=ObjectInspectorUtils.compare(converted_o0, compareOI,
					converted_o1, compareOI) == 0;*/
		}
		return result;
	}

}
