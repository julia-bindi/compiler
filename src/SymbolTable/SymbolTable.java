package SymbolTable;

import java.util.HashMap;

import Lexical.Token;

public class SymbolTable {

    private HashMap<Token, Id> table;
    
    public SymbolTable() {
        this.table = new HashMap<Token, Id>();
    }

    public void put(Token key, Id value){
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
