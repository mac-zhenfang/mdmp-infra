package com.mdmp.infra.sql.udf;

import com.mdmp.infra.sql.udf.resolver.NumericOpMethodResolver;

public abstract class UDFBaseNumericOp extends UDF {

	  /**
	   * Constructor. This constructor sets the resolver to be used for comparison
	   * operators. See {@link org.apache.hadoop.hive.ql.exec.UDFMethodResolver}
	   */
	  public UDFBaseNumericOp() {
	    super(null);
	    setResolver(new NumericOpMethodResolver(this.getClass()));
	  }

	  public abstract Byte  evaluate(Byte  a, Byte  b);

	  public abstract Short  evaluate(Short  a, Short  b);

	  public abstract int  evaluate(int  a, int  b);

	  public abstract Long  evaluate(Long  a, Long  b);

	  public abstract Float  evaluate(Float  a, Float  b);

	  public abstract Double  evaluate(Double  a, Double  b);

	}