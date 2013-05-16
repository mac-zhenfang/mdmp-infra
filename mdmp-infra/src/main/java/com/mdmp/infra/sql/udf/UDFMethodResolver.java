package com.mdmp.infra.sql.udf;

import java.lang.reflect.Method;
import java.util.List;

public interface UDFMethodResolver {

	  /**
	   * Gets the evaluate method for the UDF given the parameter types.
	   * 
	   * @param argClasses
	   *          The list of the argument types that need to matched with the
	   *          evaluate function signature.
	   */
	  Method getEvalMethod(List argClasses) throws Exception;
	}
