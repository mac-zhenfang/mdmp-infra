package com.mdmp.infra.messager;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

public class JsonMessage extends TextMessage{
	private String text = StringUtils.EMPTY;
	private JSONObject _json;
	
	public JsonMessage(String jsonStr){
		this(JSONObject.fromObject(jsonStr));
	}
	
	public JsonMessage(JSONObject json){
		_json = json;
	}
	
	public Object getValue(Object key){
		return _json.get(key);
	}
	
	public Object putValue(Object key, Object value){
		return _json.put(key, value);
	}
	
	@Override
	public int getType() {
		return Message.MSG_JSON;
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
