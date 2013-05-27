package com.mdmp.infra.rule;

import com.googlecode.aviator.lexer.ExpressionLexer;
import com.googlecode.aviator.lexer.token.Token;

public class TestEL {
	static Token<?> xxx;

	public static void main(String[] args) {
		ExpressionLexer el = new ExpressionLexer("5+count(30)+cos(60)+sum(age)");
		xxx = el.scan();
		while (xxx != null) {
			System.out.println(xxx);
			xxx = el.scan();
		}
		
	}
}
