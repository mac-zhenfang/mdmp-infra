package com.mdmp.infra.sql.args;

import com.mdmp.infra.message.JsonMessage;
import com.mdmp.infra.message.Message;

public class PrimitiveDoubleArgument extends AbstractArgument {
	private double _value;
	@Override
	public Object getValue() {
		return _value;
	}

	@Override
	public Object getValue(Message msg) {
		if (msg.getType() == Message.MSG_JSON) {
			JsonMessage jmsg = (JsonMessage) msg;
			return jmsg.getValue(getName());
		}
		return null;
	}

	@Override
	public void setValue(Object value) {
		_value = (Double) value;
	}

	@Override
	public ArgumentType getType() {
		return ArgumentType.INT;
	}

}
