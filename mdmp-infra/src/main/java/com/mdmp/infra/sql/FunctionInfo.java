package com.mdmp.infra.sql;

import java.util.List;

import com.mdmp.infra.sql.udf.UDF;
import com.mdmp.infra.sql.udf.generic.GenericUDF;
import com.mdmp.infra.sql.udf.generic.GenericUDFBaseCompare;
import com.mdmp.infra.sql.udf.generic.GenericUDFBridge;

public class FunctionInfo {
	boolean isNative;
	String name;
	Class<?  extends UDF> udfClass;
	Class<?  extends GenericUDF> genericUdfClass;
	List<Object> paraType;
	
	public FunctionInfo(boolean isNative2, String functionName,
			GenericUDF newInstance) {
		
	}

	public FunctionInfo(String functionName, GenericUDFBridge genericUDFBridge) {
		// TODO Auto-generated constructor stub
	}

	public boolean isNative() {
		return isNative;
	}

	public GenericUDF getGenericUDF() throws InstantiationException, IllegalAccessException {
		return genericUdfClass.newInstance();
	}
}
