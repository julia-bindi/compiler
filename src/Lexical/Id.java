package Lexical;

import SymbolTable.Types;

public class Id extends Token {

    public String lexeme;
    public Types type;

    public Id(int tag, String lexeme, Types type) {
        super(tag);
        this.lexeme = lexeme;
        this.type = type;
    }

    public String toString() {
        return "<" + translatedTag + ", " + this.lexeme + ">";
    }
    
    @Override 
    public String getLexeme(){
        return this.lexeme;
    }
}
