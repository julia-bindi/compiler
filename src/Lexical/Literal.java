package Lexical;

public class Literal extends Token {
    
    private String literal;

    public Literal(int tag, String literal){
        super(tag);
        this.literal = literal;
    }
    
    public String toString(){
        return "<" + translatedTag + ", " + this.literal + ">";
    }

}
