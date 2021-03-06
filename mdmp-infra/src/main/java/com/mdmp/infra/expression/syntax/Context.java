package com.mdmp.infra.expression.syntax;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.mdmp.infra.expression.tokens.Valuable;


/**
 * 上下文
 * @author shanxuecheng
 *
 */
public class Context {
	
	/**
	 * 有效标志（对应上下文所在分支结构的条件）
	 */
	private boolean effective;
	
	/**
	 * 上下文中的变量和其对应的值
	 */
	private Map<String, Valuable> variableTable = new HashMap<String, Valuable>();
	
	/**
	 * 上下文的开始位置
	 */
	private int startIndex = -1;
	
	public Context(boolean effective, Map<String, Valuable> variableTable, int startIndex) {
		this.effective = effective;
		if(variableTable == null)
			variableTable = new HashMap<String, Valuable>();
		this.variableTable.putAll(variableTable);
		this.startIndex = startIndex;
	}

	public boolean isEffective() {
		return effective;
	}
	
	public Map<String, Valuable> getVariableTable() {
		return this.variableTable;
	}
	
	public int getStartIndex() {
		return startIndex;
	}
	
	public Valuable getVariableValue(String variableName) {
		return variableTable.get(variableName);
	}
	
	public void setVariableValue(String variableName, Valuable value) {
		variableTable.put(variableName, value);
	}
	
	/**
	 * 基于本上下文创建新上下文
	 * @param effective
	 * @param startIndex
	 * @return
	 */
	public Context constructUpon(boolean effective, int startIndex) {
		//将本上下文的变量全部复制到新上下文
		return new Context(effective, variableTable, startIndex);
	}
	
	/**
	 * 更新本上下文
	 * @param context
	 */
	public void update(Context context) {
		Set<String> variableNames = variableTable.keySet();
		Set<String> targetVariableNames = context.getVariableTable().keySet();
		//如果参数context中的某变量在本上下文中也有定义，则将该变量的值覆盖到本上下文中
		for(String variableName : variableNames) 
			if(targetVariableNames.contains(variableName))
				setVariableValue(variableName, context.getVariableValue(variableName));
	}
}
