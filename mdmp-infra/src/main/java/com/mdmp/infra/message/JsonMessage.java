package com.mdmp.infra.message;

import java.util.Map;

import net.sf.json.JSONObject;

import com.mdmp.common.util.JsonUtils;

public class JsonMessage extends TextMessage {
	private JSONObject _json;
	private String _jsonStr;
	public JsonMessage(String datasourceId) {
		super(datasourceId);
	}

	public JsonMessage(String datasourceId, JSONObject json) {
		super(datasourceId);
		_json = json;
	}

	public JsonMessage(String datasourceId, String jsonStr) {
		this(datasourceId, JSONObject.fromObject(jsonStr));
		_jsonStr = jsonStr;
	}

	public Object getValue(Object key) {
		if(_json == null){
			
		}
		return _json.get(key);
	}

	public void putValue(Object key, Object value) {
		_json.put(key, value);
	}

	public boolean containsKey(Object key) {
		return _json.containsKey(key);
	}

	public Object remove(Object key) {
		return _json.remove(key);
	}

	@Override
	public int getType() {
		return Message.MSG_JSON;
	}

	@Override
	public String getText() {
		return _json.toString();
	}

	public static JsonMessage newInstance(Message msg) {
		return new JsonMessage(msg.getDataSourceId(), "");
	}
	
	public Map<String, Object> toMap() throws Exception{
		return JsonUtils.toMap(_json);
	} 

	@Override
	public String toString() {
		return "JsonMessage [" + _json + "]";
	}
}
