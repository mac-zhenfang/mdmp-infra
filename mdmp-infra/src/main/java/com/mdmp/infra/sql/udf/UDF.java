package com.mdmp.infra.sql.udf;

import com.mdmp.infra.sql.udf.resolver.DefaultUDFMethodResolver;

public class UDF {

	  /**
	   * The resolver to use for method resolution.
	   */
	  private UDFMethodResolver rslv;

	  /**
	   * The constructor.
	   */
	  public UDF() {
	    rslv = new DefaultUDFMethodResolver(this.getClass());
	  }

	  /**
	   * The constructor with user-provided UDFMethodResolver.
	   */
	  protected UDF(UDFMethodResolver rslv) {
	    this.rslv = rslv;
	  }

	  /**
	   * Sets the resolver.
	   *
	   * @param rslv
	   *          The method resolver to use for method resolution.
	   */
	  public void setResolver(UDFMethodResolver rslv) {
	    this.rslv = rslv;
	  }

	  /**
	   * Get the method resolver.
	   */
	  public UDFMethodResolver getResolver() {
	    return rslv;
	  }

	}