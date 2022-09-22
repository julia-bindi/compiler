package Lexical;

public class TokenBuildException extends Exception {

    public TokenBuildException(int line){
        super("Error building token at line " + line);
    }

}
