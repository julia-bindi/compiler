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

    public Id put(Id key) {
        if (!this.hasSymbol(key)) {
            this.table.put(key, this.level);
            return key;
        }
        return this.get(key);
    }

    public Id get(Id t) {
        for (Id key : table.keySet()) {
            if (key.lexeme.equals(t.lexeme)) {
                return key;
            }
        }
        return new Id(0, ""); // Error
    }

    public Boolean hasSymbol(Id t) {
        for (Id key : table.keySet()) {
            if (key.lexeme.equals(t.lexeme)) {
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
