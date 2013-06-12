package com.mdmp.infra.handler;

public class OperatorBean {
	private String name;
	private String param;
	private int index;
	private int[] children;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getParam() {
		return param;
	}
	public void setParam(String param) {
		this.param = param;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public int[] getChildren() {
		return children;
	}
	public void setChildren(int[] children) {
		this.children = children;
	}
}
