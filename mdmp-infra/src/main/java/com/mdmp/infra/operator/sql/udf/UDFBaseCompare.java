package com.mdmp.infra.operator.sql.udf;

public abstract class UDFBaseCompare extends UDF {

	  /**
	   * This constructor sets the resolver to be used for comparison operators. See
	   * {@link org.apache.hadoop.hive.ql.exec.UDFMethodResolver}
	   */
	  public UDFBaseCompare() {
	    super(null);
	    setResolver(new ComparisonOpMethodResolver(this.getClass()));
	  }

	  public abstract BooleanWritable evaluate(DoubleWritable a, DoubleWritable b);
	}