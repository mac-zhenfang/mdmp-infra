package com.mdmp.infra.expression.tokens;

import com.mdmp.infra.sql.udaf.Function;

/**
 * 函数符号
 * @author shanxuecheng
 *
 */
public final class FunctionToken extends TerminalToken {

	/**
	 * 函数定义
	 */
	private final Function function;
	
	public FunctionToken(TokenBuilder builder) {
		super(builder);
		function = builder.getFunction();
	}
	
	public Function getFunction() {
		return function;
	}
	
	public TokenType getTokenType() {
		return TokenType.FUNCTION;
	}

	@Override
	public boolean equalsInGrammar(TerminalToken target) {
		return target instanceof FunctionToken;
	}

}
