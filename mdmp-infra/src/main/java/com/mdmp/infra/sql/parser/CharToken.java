package com.mdmp.infra.sql.parser;

import java.util.Map;


public class CharToken extends AbstractToken<Character> {
    private final char ch;
    private int startIndex;


    public char getCh() {
        return ch;
    }


    public CharToken(char peek, int startIndex) {
        super(startIndex, String.valueOf(peek));
        this.ch = peek;
    }


    public TokenType getType() {
        return TokenType.Char;
    }


    public Character getValue(Map<String, Object> env) {
        return ch;
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ch;
        result = prime * result + startIndex;
        return result;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        CharToken other = (CharToken) obj;
        if (ch != other.ch) {
            return false;
        }
        if (startIndex != other.startIndex) {
            return false;
        }
        return true;
    }

}

