package Syntatical;

import SymbolTable.Types;

public class SemanticException extends Exception {

    public SemanticException(Types t1, Types t2){
        super("Erro compilando semanticamente: " + t1.name() + " != " + t2.name());
    }

    public SemanticException(String e){
        super("Erro compilando semanticamente: " + e);
    }
}
