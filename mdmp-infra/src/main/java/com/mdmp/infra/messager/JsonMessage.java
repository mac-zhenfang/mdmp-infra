package com.mdmp.infra.messager;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

public class JsonMessage extends TextMessage{
	private String text = StringUtils.EMPTY;
	private String type;
	private JSONObject _json;
	
	public JsonMessage(JSONObject json){
		_json = json;
	}
	
	public Object getValue(Object key){
		return _json.get(key);
	}
	
	@Override
	public String getType() {
		return type;
	}

	@Override
	public void setType(String type) {
		this.type = type;
	}

	@Override
	public void setText(String text) {
		this.text = text;
	}

	@Override
	public String getText() {
		return text;
	}
}
