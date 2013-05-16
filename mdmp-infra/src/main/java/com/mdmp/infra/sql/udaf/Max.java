package com.mdmp.infra.sql.udaf;

public class Max implements UDAF {
	protected long value = Long.MIN_VALUE;

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
		if (v > value) {
			value = v;
		}
	}

	@Override
	public String toString() {
		return Long.toString(value);
	}
}
