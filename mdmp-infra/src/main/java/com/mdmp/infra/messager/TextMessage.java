package com.mdmp.infra.messager;

public abstract class TextMessage extends Message {

	public abstract void setText(String text);

	public abstract String getText();
}
