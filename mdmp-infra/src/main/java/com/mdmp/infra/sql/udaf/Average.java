package com.mdmp.infra.sql.udaf;

public class Average implements UDAF {
	private long sum;
	private long count;

	@Override
	public void calculate(String v) {
		if (v == null) {
			calculate(0);
		} else {
			try {
				calculate(Long.valueOf(v));
			} catch (NumberFormatException e) {
				calculate(0);
			}
		}
	}

	private void calculate(long v) {
		sum += v;
		count ++;
	}

	@Override
	public String toString() {
		return String.format("%.2f", (double) sum / count);
	}
}
