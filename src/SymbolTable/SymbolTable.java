package SymbolTable;

import java.util.HashMap;

import Lexical.Id;
import Lexical.Tag;

public class SymbolTable {

    private HashMap<Id, Integer> table; // Tabela guarda Id -> int (level)
    private int level;

    public SymbolTable() {

        this.table = new HashMap<Id, Integer>();
        this.level = 1;

        // Reserved words put
        this.put(new Id(Tag.START, "start", Types.VOID));
        this.put(new Id(Tag.EXIT, "exit", Types.VOID));
        this.put(new Id(Tag.END, "end", Types.VOID));
        this.put(new Id(Tag.INT, "int", Types.VOID));
        this.put(new Id(Tag.FLOAT, "float", Types.VOID));
        this.put(new Id(Tag.STRING, "string", Types.VOID));
        this.put(new Id(Tag.IF, "if", Types.VOID));
        this.put(new Id(Tag.THEN, "then", Types.VOID));
        this.put(new Id(Tag.ELSE, "else", Types.VOID));
        this.put(new Id(Tag.DO, "do", Types.VOID));
        this.put(new Id(Tag.WHILE, "while", Types.VOID));
        this.put(new Id(Tag.SCAN, "scan", Types.VOID));
        this.put(new Id(Tag.PRINT, "print", Types.VOID));
    }

    public Id put(Id key) {
        if (!this.hasSymbol(key.lexeme)) {
            this.table.put(key, this.level);
            return key;
        }
        return this.get(key.getLexeme());
    }

    public Id get(String lexeme) {
        for (Id key : table.keySet()) {
            if(key.lexeme.equals(lexeme)) {
                return key;
            }
        }
        return new Id(0, "", Types.ERROR); // Error
    }

    public Boolean hasSymbol(String lexeme) {
        for (Id key : table.keySet()) {
            if (key.lexeme.equals(lexeme)) {
                return true;
            }
        }
        return false;
    }

    public void remove() {

    }

    public void printTable() {
        System.out.println("SYMBOL TABLE");
        for (Id key : this.table.keySet()) {
            System.out.println(key.lexeme);
        }
    }
}
