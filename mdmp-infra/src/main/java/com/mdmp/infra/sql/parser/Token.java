package com.mdmp.infra.sql.parser;
import java.util.Map;

import com.googlecode.aviator.lexer.token.Token.TokenType;


/**
 * Lex token interface
 * 
 * @author dennis
 * 
 * @param <T>
 */
public interface Token<T> {
    public enum TokenType {
        String,
        Variable,
        Number,
        Char,
        Operator,
        Pattern,
        Delegate
    }


    public T getValue(Map<String, Object> env);


    public TokenType getType();


    public String getLexeme();


    public int getStartIndex();
}
