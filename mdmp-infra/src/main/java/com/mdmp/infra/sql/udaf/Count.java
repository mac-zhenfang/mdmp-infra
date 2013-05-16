package com.mdmp.infra.sql.udaf;

public class Count implements UDAF {
	private long count;

	@Override
	public void calculate(String v) {
		count++;
	}

	@Override
	public String toString() {
		return Long.toString(count);
	}
}
