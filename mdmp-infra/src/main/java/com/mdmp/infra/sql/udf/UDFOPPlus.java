package com.mdmp.infra.sql.udf;

public class UDFOPPlus extends UDFBaseNumericOp {

	public UDFOPPlus() {
	}

	@Override
	public Byte evaluate(Byte a, Byte b) {
		// LOG.info("Get input " + a.getClass() + ":" + a + " " + b.getClass() +
		// ":"
		// + b);
		if ((a == null) || (b == null)) {
			return null;
		}
		return (byte) (a + b);
	}

	@Override
	public Short evaluate(Short a, Short b) {
		// LOG.info("Get input " + a.getClass() + ":" + a + " " + b.getClass() +
		// ":"
		// + b);
		if ((a == null) || (b == null)) {
			return null;
		}

		return (short) (a + b);
	}

	@Override
	public int evaluate(int a, int b) {
		// LOG.info("Get input " + a.getClass() + ":" + a + " " + b.getClass() +
		// ":"
		// + b);
		return a + b;
	}

	@Override
	  public Long evaluate(Long a, Long b) {
	    // LOG.info("Get input " + a.getClass() + ":" + a + " " + b.getClass() + ":"
	    // + b);
	    if ((a == null) || (b == null)) {
	      return null;
	    }

	    return a + b;
	  }

	@Override
	  public Float evaluate(Float a, Float b) {
	    // LOG.info("Get input " + a.getClass() + ":" + a + " " + b.getClass() + ":"
	    // + b);
	    if ((a == null) || (b == null)) {
	      return null;
	    }

	    return a + b;
	  }

	@Override
	  public Double evaluate(Double a, Double b) {
	    // LOG.info("Get input " + a.getClass() + ":" + a + " " + b.getClass() + ":"
	    // + b);
	    if ((a == null) || (b == null)) {
	      return null;
	    }

	    return a + b;
	  }
}
