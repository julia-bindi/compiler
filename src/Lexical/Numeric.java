package Lexical;

public class Numeric extends Token {
    
    private String value;

    public Numeric(int tag, String value){
        super(tag);
        this.value = value;
    }
    
    public String toString(){
        return "<" + translatedTag + ", " + this.value + ">";
    }

    public String getValue(){
        return this.value;
    }

}
