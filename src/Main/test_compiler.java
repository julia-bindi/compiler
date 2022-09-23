package Main;

import Lexical.Lexer;
import Lexical.Token;
import Lexical.TokenBuildException;
import SymbolTable.SymbolTable;

import java.io.File;

public class test_compiler {

    public static void main(String[] args) {

        SymbolTable ST = new SymbolTable();
        String projectPath = new File("").getAbsolutePath();
        Lexer lexical = new Lexer(projectPath.concat("/tests/test1.txt"), ST);

        try {

            Token t;
            System.out.println("Tokens:");
            do {
                t = lexical.getNextToken();
                System.out.println(t.toString());
            } while (t.tag != 292);

        } catch (TokenBuildException tbe) {
            System.out.println(tbe.getMessage());
        }

        System.out.println("Info: Lexer test ended!");

        ST.printTable();
    }

}
