package SymbolTable;

import java.util.HashMap;

import Lexical.Token;
import Lexical.Id;
import Lexical.Tag;

public class SymbolTable {

    private HashMap<Token, Integer> table; // Tabela guarda Id -> int (level)
    private int level;

    public SymbolTable() {

        this.table = new HashMap<Token, Integer>();
        this.level = 1;

        // Reserver words put
        this.put(new Id(Tag.START, "start"));
        this.put(new Id(Tag.EXIT, "exit"));
        this.put(new Id(Tag.END, "end"));
        this.put(new Id(Tag.INT, "int"));
        this.put(new Id(Tag.FLOAT, "float"));
        this.put(new Id(Tag.STRING, "string"));
        this.put(new Id(Tag.IF, "if"));
        this.put(new Id(Tag.THEN, "then"));
        this.put(new Id(Tag.ELSE, "else"));
        this.put(new Id(Tag.DO, "do"));
        this.put(new Id(Tag.WHILE, "while"));
        this.put(new Id(Tag.SCAN, "scan"));
        this.put(new Id(Tag.PRINT, "print"));
    }

    public Token put(Token key){
        if(!this.hasSymbol(key))
            this.table.put(key, this.level);
        return key;
    }
    
    public void get(){
        
    }

    public Boolean hasSymbol(Token t){
        return this.table.containsKey(t);
    }

    public void remove(){

    }

}
