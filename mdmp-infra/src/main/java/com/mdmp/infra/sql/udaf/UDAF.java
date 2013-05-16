package com.mdmp.infra.sql.udaf;

import org.codehaus.jackson.annotate.JsonValue;

public interface UDAF {
	public void calculate(String t);

	@JsonValue
	public String toString();
}
