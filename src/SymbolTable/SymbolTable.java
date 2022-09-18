package SymbolTable;

import java.util.HashMap;

public class SymbolTable {

    private HashMap<String, Integer> table;
    
    public SymbolTable() {
        this.table = new HashMap<String, Integer>();
    }

    public void put(String key, Integer value){
        this.table.put(key, value);
    }
    
    public void get(){
        
    }

    public Boolean hasSymbol(String t){
        return false;
    }

    public void remove(){

    }

}
