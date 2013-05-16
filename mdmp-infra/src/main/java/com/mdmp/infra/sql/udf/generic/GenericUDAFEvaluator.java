package com.mdmp.infra.sql.udf.generic;

import com.mdmp.common.exception.MDMPException;

/**
 * A Generic User-defined aggregation function (GenericUDAF) for the use with
 * Hive.
 * 
 * New GenericUDAF classes need to inherit from this GenericUDAF class.
 * 
 * The GenericUDAF are superior to normal UDAFs in the following ways: 1. It can
 * accept arguments of complex types, and return complex types. 2. It can accept
 * variable length of arguments. 3. It can accept an infinite number of function
 * signature - for example, it's easy to write a GenericUDAF that accepts
 * array<int>, array<array<int>> and so on (arbitrary levels of nesting).
 */
public abstract class GenericUDAFEvaluator {

  /**
   * Mode.
   *
   */
  public static enum Mode {
    /**
     * PARTIAL1: from original data to partial aggregation data: iterate() and
     * terminatePartial() will be called.
     */
    PARTIAL1,
        /**
     * PARTIAL2: from partial aggregation data to partial aggregation data:
     * merge() and terminatePartial() will be called.
     */
    PARTIAL2,
        /**
     * FINAL: from partial aggregation to full aggregation: merge() and
     * terminate() will be called.
     */
    FINAL,
        /**
     * COMPLETE: from original data directly to full aggregation: iterate() and
     * terminate() will be called.
     */
    COMPLETE
  };

  Mode mode;

  /**
   * The constructor.
   */
  public GenericUDAFEvaluator() {
  }

  /**
   * Initialize the evaluator.
   * 
   * @param m
   *          The mode of aggregation.
   * @param parameters
   *          The ObjectInspector for the parameters: In PARTIAL1 and COMPLETE
   *          mode, the parameters are original data; In PARTIAL2 and FINAL
   *          mode, the parameters are just partial aggregations (in that case,
   *          the array will always have a single element).
   * @return The ObjectInspector for the return value. In PARTIAL1 and PARTIAL2
   *         mode, the ObjectInspector for the return value of
   *         terminatePartial() call; In FINAL and COMPLETE mode, the
   *         ObjectInspector for the return value of terminate() call.
   * 
   *         NOTE: We need ObjectInspector[] (in addition to the TypeInfo[] in
   *         GenericUDAFResolver) for 2 reasons: 1. ObjectInspector contains
   *         more information than TypeInfo; and GenericUDAFEvaluator.init at
   *         execution time. 2. We call GenericUDAFResolver.getEvaluator at
   *         compilation time,
   */
  public Object init(Mode m, Object[] parameters) throws MDMPException {
    // This function should be overriden in every sub class
    // And the sub class should call super.init(m, parameters) to get mode set.
    mode = m;
    return null;
  }

  /**
   * The interface for a class that is used to store the aggregation result
   * during the process of aggregation.
   * 
   * We split this piece of data out because there can be millions of instances
   * of this Aggregation in hash-based aggregation process, and it's very
   * important to conserve memory.
   * 
   * In the future, we may completely hide this class inside the Evaluator and
   * use integer numbers to identify which aggregation we are looking at.
   */
  public static interface AggregationBuffer {
  };

  /**
   * Get a new aggregation object.
   */
  public abstract AggregationBuffer getNewAggregationBuffer() throws MDMPException;

  /**
   * Reset the aggregation. This is useful if we want to reuse the same
   * aggregation.
   */
  public abstract void reset(AggregationBuffer agg) throws MDMPException;

  /**
   * This function will be called by GroupByOperator when it sees a new input
   * row.
   * 
   * @param agg
   *          The object to store the aggregation result.
   * @param parameters
   *          The row, can be inspected by the OIs passed in init().
   */
  public void aggregate(AggregationBuffer agg, Object[] parameters) throws MDMPException {
    if (mode == Mode.PARTIAL1 || mode == Mode.COMPLETE) {
      iterate(agg, parameters);
    } else {
      assert (parameters.length == 1);
      merge(agg, parameters[0]);
    }
  }

  /**
   * This function will be called by GroupByOperator when it sees a new input
   * row.
   * 
   * @param agg
   *          The object to store the aggregation result.
   */
  public Object evaluate(AggregationBuffer agg) throws MDMPException {
    if (mode == Mode.PARTIAL1 || mode == Mode.PARTIAL2) {
      return terminatePartial(agg);
    } else {
      return terminate(agg);
    }
  }

  /**
   * Iterate through original data.
   * 
   * @param parameters
   *          The objects of parameters.
   */
  public abstract void iterate(AggregationBuffer agg, Object[] parameters) throws MDMPException;

  /**
   * Get partial aggregation result.
   * 
   * @return partial aggregation result.
   */
  public abstract Object terminatePartial(AggregationBuffer agg) throws MDMPException;

  /**
   * Merge with partial aggregation result. NOTE: null might be passed in case
   * there is no input data.
   * 
   * @param partial
   *          The partial aggregation result.
   */
  public abstract void merge(AggregationBuffer agg, Object partial) throws MDMPException;

  /**
   * Get final aggregation result.
   * 
   * @return final aggregation result.
   */
  public abstract Object terminate(AggregationBuffer agg) throws MDMPException;

}
