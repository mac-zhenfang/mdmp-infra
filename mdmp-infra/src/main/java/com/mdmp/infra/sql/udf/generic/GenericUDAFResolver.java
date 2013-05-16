package com.mdmp.infra.sql.udf.generic;

import com.mdmp.common.exception.MDMPException;
import com.mdmp.infra.sql.TypeInfo;

/**
 * A Generic User-defined aggregation function (GenericUDAF) for the use with
 * Hive.
 *
 * GenericUDAFResolver is used at compile time. We use GenericUDAFResolver to
 * find out the GenericUDAFEvaluator for the parameter types.
 *
 * @deprecated Use {@link GenericUDAFResolver2} instead.
 */
public interface GenericUDAFResolver {

  /**
   * Get the evaluator for the parameter types.
   *
   * The reason that this function returns an object instead of a class is
   * because it is possible that the object needs some configuration (that can
   * be serialized). In that case the class of the object has to implement the
   * Serializable interface. At execution time, we will deserialize the object
   * from the plan and use it to evaluate the aggregations.
   * <p>
   * If the class of the object does not implement Serializable, then we will
   * create a new instance of the class at execution time.
   * </p>
   * @param parameters
   *          The types of the parameters. We need the type information to know
   *          which evaluator class to use.
   * @throws SemanticException
   */
  GenericUDAFEvaluator getEvaluator(TypeInfo[] parameters)
    throws MDMPException;
}
