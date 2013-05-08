package com.mdmp.infra.messager;

public abstract class Message {
	public static final int MSG_JSON = 1;
	
	String datasourceId = null;
	public abstract int getType();
//	public abstract void setType(int type);

	public String getDataSourceId() {
		return datasourceId;
	}
	public String getTimeStamp() {
		// TODO Auto-generated method stub
		return null;
	}
}
