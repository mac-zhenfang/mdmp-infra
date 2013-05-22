package com.mdmp.infra.sql.args;

import com.mdmp.infra.message.JsonMessage;
import com.mdmp.infra.message.Message;

public class PrimitiveIntArgument extends AbstractArgument {
	private int _value;
	
	@Override
	public Object getValue() {
		return _value;
	}

	@Override
	public Object getValue(Message msg) {
		if (isColumn() && getName() != null && msg.getType() == Message.MSG_JSON) {
			JsonMessage jmsg = (JsonMessage)msg;
			return jmsg.getValue(getName());
		}
		return _value;
	}

	@Override
	public void setValue(Object value) {
		if(value == null || !(value instanceof Integer)){
			return;
		}
		_value = (Integer) value;
	}

	@Override
	public ArgumentType getType() {
		return ArgumentType.INT;
	}

}
