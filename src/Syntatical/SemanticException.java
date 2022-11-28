package Syntatical;

import SymbolTable.Types;

public class SemanticException extends Exception {

    public SemanticException(Types t1, Types t2){
        super("Error compiling semantical: " + t1.name() + " != " + t2.name());
    }

    public SemanticException(String e){
        super("Error compiling semantical: " + e);
    }
}
