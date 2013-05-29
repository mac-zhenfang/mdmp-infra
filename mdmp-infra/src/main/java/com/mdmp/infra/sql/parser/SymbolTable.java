package com.mdmp.infra.sql.parser;

import java.util.HashMap;
import java.util.Map;


public class SymbolTable {

    private final Map<String, Variable> table = new HashMap<String, Variable>();


    public SymbolTable() {
        reserve("true", Variable.TRUE);
        reserve("false", Variable.FALSE);
        reserve("nil", Variable.NIL);
    }


    /**
     * Reserve variable
     * 
     * @param name
     * @param value
     */
    public void reserve(String name, Variable value) {
        table.put(name, value);
    }


    /**
     * Check variable has been reserved?
     * 
     * @param name
     * @return
     */
    public boolean contains(String name) {
        return table.containsKey(name);
    }


    /**
     * Get symbold table
     * 
     * @return
     */
    public Map<String, Variable> getTable() {
        return table;
    }


    /**
     * Get variable by name
     * 
     * @param name
     * @return
     */
    public Variable getVariable(String name) {
        return table.get(name);
    }
}
