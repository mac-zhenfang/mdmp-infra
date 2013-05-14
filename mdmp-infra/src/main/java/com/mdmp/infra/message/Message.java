package com.mdmp.infra.message;

public abstract class Message {
	public static final int MSG_DEFAULT = 0;
	public static final int MSG_JSON = 1;
	public static final int MSG_UNHANDLED = -1;
	private int _status = MSG_UNHANDLED;
	private String _dsId = null;

	public Message(String datasourceId) {
		_dsId = datasourceId;
	}

	public int getType() {
		return MSG_DEFAULT;
	}

	public String getDataSourceId() {
		return _dsId;
	}

	public String getTimeStamp() {
		return null;
	}

	public int getStatus() {
		return _status;
	}

	public void setStatus(int status) {
		_status = status;
	}
}
