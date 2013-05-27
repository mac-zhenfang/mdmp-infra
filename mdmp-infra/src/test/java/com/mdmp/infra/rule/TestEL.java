package com.mdmp.infra.rule;

<<<<<<< HEAD
import java.io.OutputStream;

import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.code.CodeGenerator;
import com.googlecode.aviator.code.OptimizeCodeGenerator;
import com.googlecode.aviator.code.asm.ASMCodeGenerator;
import com.googlecode.aviator.lexer.ExpressionLexer;
import com.googlecode.aviator.lexer.token.Token;
import com.googlecode.aviator.parser.AviatorClassLoader;
import com.googlecode.aviator.parser.ExpressionParser;

public class TestEL {
	static Token<?> xxx;

	public static void main(String[] args) {
		// ExpressionLexer el = new
		// ExpressionLexer("5+count(30)+cos(60)+sum(age)");
		// xxx = el.scan();
		// while (xxx != null) {
		// System.out.println(xxx);
		// xxx = el.scan();
		// }
		// ExpressionLexer lexer= new
		// ExpressionLexer("5+count(30)+cos(60)+sum(age)");
		// CodeGenerator codeGenerator = new ASMCodeGenerator(null, null, true);
		// ExpressionParser parser = new ExpressionParser(lexer, codeGenerator);
		//float result =  (Float) AviatorEvaluator.execute("5+count(30)+cos(60)");
		//System.out.println(AviatorEvaluator.execute("now()"));
		System.out.println(AviatorEvaluator.execute("string.contains('aaaab','aa') "));
}
