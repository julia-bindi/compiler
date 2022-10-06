package Lexical;

public class TokenBuildException extends Exception {

    public int line;

    public TokenBuildException(int line){
        super("Error building token at line " + line);
        this.line = line;
    }

    public TokenBuildException(int line, String text){
        super("Error building token at line " + line + "\n" + text);
        this.line = line;
    }

}
