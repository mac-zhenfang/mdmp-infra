package com.mdmp.infra.message;

public abstract class TextMessage extends Message {
	public TextMessage(String datasourceId) {
		super(datasourceId);
	}

	public abstract String getText();
}
