package com.mdmp.infra.operator.sql.udf;
import java.lang.reflect.Method;
import java.util.List;
public class DefaultUDFMethodResolver implements UDFMethodResolver {

	  /**
	   * The class of the UDF.
	   */
	  private final Class<? extends UDF> udfClass;

	  /**
	   * Constructor. This constructor sets the resolver to be used for comparison
	   * operators. See {@link UDFMethodResolver}
	   */
	  public DefaultUDFMethodResolver(Class<? extends UDF> udfClass) {
	    this.udfClass = udfClass;
	  }

	  /**
	   * Gets the evaluate method for the UDF given the parameter types.
	   * 
	   * @param argClasses
	   *          The list of the argument types that need to matched with the
	   *          evaluate function signature.
	   */
	  @Override
	  public Method getEvalMethod(List argClasses) throws Exception {
	    return FunctionRegistry.getMethodInternal(udfClass, "evaluate", false,
	        argClasses);
	  }
	}