package com.mdmp.infra.messager;

import org.apache.commons.lang.StringUtils;

public class JsonMessage extends TextMessage{
	private String text = StringUtils.EMPTY;
	private String type;
	
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
