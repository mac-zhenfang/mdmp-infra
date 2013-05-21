package com.mdmp.infra.sql.args;

public abstract class AbstractArgument implements Argument {
	private String _name;
	private int _length;

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		_name = name;
	}

	public int getLength() {
		return _length;
	}

	public void setLength(int length) {
		_length = length;
	}
}
