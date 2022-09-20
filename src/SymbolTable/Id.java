package SymbolTable;

public class Id {

    private int level;   //level 1, 2, 3... defined by {}
    private String type; //double, int, func...

    public Id(int l, String t){
        this.level = l;
        this.type = t;
    }
    
}
