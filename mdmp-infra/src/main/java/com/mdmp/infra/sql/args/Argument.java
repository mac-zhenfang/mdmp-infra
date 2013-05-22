package com.mdmp.infra.sql.args;

import com.mdmp.infra.message.Message;

public interface Argument {
	public boolean isColumn();
	
	public int getLength();
	
	public void setLength(int length);
	
	public String getName();

	public void setName(String name);
	
	public Object getValue();
	
	public Object getValue(Message msg);
	
	public void setValue(Object value);
	
	public ArgumentType getType();
	
	public void setColumn(boolean isColumn);
}
