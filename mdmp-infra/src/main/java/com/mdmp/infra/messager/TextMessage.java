package com.mdmp.infra.messager;

public abstract class TextMessage extends Message {
	public abstract String getType();

	public abstract void setType(String type);

	public abstract void setText(String text);

	public abstract String getText();
}
