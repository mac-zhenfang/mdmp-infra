package com.mdmp.infra.operator.sql.udaf;

public class Actual implements UDAF {
	private String value;

	@Override
	public void calculate(String v) {
		value = v;
	}

	@Override
	public String toString() {
		return value;
	}
}
