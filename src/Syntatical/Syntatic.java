package Syntatical;

import Lexical.Lexer;
import Lexical.Token;
import Lexical.TokenBuildException;

public class Syntatic {

    private Lexer lexical;
    
    public Syntatic(Lexer l){
        this.lexical = l;
    };

    private void eat(Token t){

    };

    public void executeSyntatical(){

        try{
            
            Token t;
            do {

                t = this.lexical.getNextToken();
                System.out.println(t.toString());

            } while (t.tag != 292);

        } catch (TokenBuildException tbe) {
            System.out.println(tbe.getMessage() + "\n");
        }

    }

}
