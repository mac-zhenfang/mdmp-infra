package com.mdmp.infra.operator.sql.udaf;

import org.codehaus.jackson.annotate.JsonValue;

public interface UDAF {
	public void calculate(String t);

	@JsonValue
	public String toString();
}
