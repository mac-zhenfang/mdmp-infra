package com.mdmp.infra.messager;

public interface TextMessage extends Message {
	public abstract String getType();

	public abstract void setType(String type);

	public abstract void setText(String text);

	public abstract String getText();
}
