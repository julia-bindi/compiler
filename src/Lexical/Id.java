package Lexical;

public class Id extends Token{

    private String lexeme;

    public Id(int tag, String lexeme){
        super(tag);
        this.lexeme = lexeme;
    }
    
    public String toString(){
        return "<" + tag + ", " + this.lexeme + ">";
    }
    
}
