package Main;

import Lexical.Lexer;
import Lexical.Token;
import SymbolTable.SymbolTable;

public class test_compiler {
    
    public static void main(String[] args) {

        SymbolTable ST = new SymbolTable();

        Lexer lexical = new Lexer("C:\\Users\\Arthur\\Desktop\\Cefet\\2022.2\\Compiladores\\compiler\\tests\\test6.txt",
                                  ST);
                                  
        Token t;
        do{
            t = lexical.getNextToken();
            System.out.println(t.toString());
        }while(t.tag != 292);

    }

}
