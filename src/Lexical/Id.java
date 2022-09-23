package Lexical;

public class Id extends Token {

    public String lexeme;

    public Id(int tag, String lexeme) {
        super(tag);
        this.lexeme = lexeme;
    }

    public String toString() {
        return "<" + translatedTag + ", " + this.lexeme + ">";
    }
}
