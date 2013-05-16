package com.mdmp.infra.sql.udaf;

public class Sum implements UDAF {
	private long value;

	@Override
	public void calculate(String v) {
		if (v != null) {
			try {
				calculate(Long.valueOf(v));
			} catch (NumberFormatException e) {
				calculate(0);
			}
		}
	}

	private void calculate(long v) {
		value += v;
	}

	@Override
	public String toString() {
		return Long.toString(value);
	}
}
