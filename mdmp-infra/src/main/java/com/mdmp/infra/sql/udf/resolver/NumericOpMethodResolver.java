package com.mdmp.infra.sql.udf.resolver;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.mdmp.common.exception.UDFArgumentException;
import com.mdmp.infra.sql.FunctionRegistry;
import com.mdmp.infra.sql.udf.UDF;
import com.mdmp.infra.sql.udf.UDFMethodResolver;

public class NumericOpMethodResolver implements UDFMethodResolver {

	  /**
	   * The udfclass for which resolution is needed.
	   */
	  Class<? extends UDF> udfClass;

	  /**
	   * Constuctor.
	   */
	  public NumericOpMethodResolver(Class<? extends UDF> udfClass) {
	    this.udfClass = udfClass;
	  }

	  /*
	   * (non-Javadoc)
	   * 
	   * @see
	   * org.apache.hadoop.hive.ql.exec.UDFMethodResolver#getEvalMethod(java.util
	   * .List)
	   */
	  @Override
	  public Method getEvalMethod(List argTypeInfos) throws UDFArgumentException {
	    assert (argTypeInfos.size() == 2);

	    List pTypeInfos = null;
	    List modArgTypeInfos = new ArrayList();

	    // If either argument is a string, we convert to a double or decimal because a number
	    // in string form should always be convertible into either of those
	    if (argTypeInfos.get(0).equals(TypeInfoFactory.stringTypeInfo)
	        || argTypeInfos.get(1).equals(TypeInfoFactory.stringTypeInfo)) {
	      
	      // Default is double, but if one of the sides is already in decimal we 
	      // complete the operation in that type.
	      if (argTypeInfos.get(0).equals(TypeInfoFactory.decimalTypeInfo)
	          || argTypeInfos.get(1).equals(TypeInfoFactory.decimalTypeInfo)) {
	        modArgTypeInfos.add(TypeInfoFactory.decimalTypeInfo);
	        modArgTypeInfos.add(TypeInfoFactory.decimalTypeInfo);
	      } else {
	        modArgTypeInfos.add(TypeInfoFactory.doubleTypeInfo);
	        modArgTypeInfos.add(TypeInfoFactory.doubleTypeInfo);
	      }
	    } else {
	      // If it's a void, we change the type to a byte because once the types
	      // are run through getCommonClass(), a byte and any other type T will
	      // resolve to type T
	      for (int i = 0; i < 2; i++) {
	        if (argTypeInfos.get(i).equals(TypeInfoFactory.voidTypeInfo)) {
	          modArgTypeInfos.add(TypeInfoFactory.byteTypeInfo);
	        } else {
	          modArgTypeInfos.add(argTypeInfos.get(i));
	        }
	      }
	    }

	    TypeInfo commonType = FunctionRegistry.getCommonClass(modArgTypeInfos
	        .get(0), modArgTypeInfos.get(1));

	    if (commonType == null) {
	      throw new UDFArgumentException("Unable to find a common class between"
	          + "types " + modArgTypeInfos.get(0).getTypeName() + " and "
	          + modArgTypeInfos.get(1).getTypeName());
	    }

	    pTypeInfos = new ArrayList();
	    pTypeInfos.add(commonType);
	    pTypeInfos.add(commonType);

	    Method udfMethod = null;

	    for (Method m : Arrays.asList(udfClass.getMethods())) {
	      if (m.getName().equals("evaluate")) {

	        List argumentTypeInfos = TypeInfoUtils.getParameterTypeInfos(
	            m, pTypeInfos.size());
	        if (argumentTypeInfos == null) {
	          // null means the method does not accept number of arguments passed.
	          continue;
	        }

	        boolean match = (argumentTypeInfos.size() == pTypeInfos.size());

	        for (int i = 0; i < pTypeInfos.size() && match; i++) {
	          TypeInfo accepted = argumentTypeInfos.get(i);
	          if (!accepted.equals(pTypeInfos.get(i))) {
	            match = false;
	          }
	        }

	        if (match) {
	          if (udfMethod != null) {
	            throw new AmbiguousMethodException(udfClass, argTypeInfos, 
	                Arrays.asList(new Method[]{udfMethod, m}));
	          } else {
	            udfMethod = m;
	          }
	        }
	      }
	    }
	    return udfMethod;
	  }
	}