package Main;

import Lexical.Lexer;
import Lexical.Token;
import Lexical.TokenBuildException;
import SymbolTable.SymbolTable;

import java.io.File;
import java.util.ArrayList;

public class test_compiler {
    public final static ArrayList<String> optionsToCompile = new ArrayList<String>(7);

    public static void fillHowToCompile() {
        test_compiler.optionsToCompile.add("Teste 01");
        test_compiler.optionsToCompile.add("Teste 02");
        test_compiler.optionsToCompile.add("Teste 03");
        test_compiler.optionsToCompile.add("Teste 04");
        test_compiler.optionsToCompile.add("Teste 05");
        test_compiler.optionsToCompile.add("Teste 06");
    }

    public static void printHowToCompile() {
        System.out.println("To compile, run the following command in the terminal: java -jar compiler.jar ARG");
        System.out.println("Where ARG can be:");
        for (String option : optionsToCompile)
            System.out.println(option);
        System.out.println("Todos");
    }

    public static void compileOneFileTest(String filename) {
        SymbolTable ST = new SymbolTable();
        String projectPath = new File("").getAbsolutePath();
        Lexer lexical = new Lexer(projectPath.concat(filename), ST);
        Token t;

        System.out.println("--- Testando o arquivo " + filename + " ---");
        System.out.println();
        System.out.println("TOKENS");

        try {
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

    public static void main(String[] args) {
        fillHowToCompile();

        if (args.length > 0) {
            String ARG = args[0];

            if (!ARG.equals("Todos") && !optionsToCompile.contains(ARG)) {
                printHowToCompile();
                System.exit(1);
            } else if (ARG.equals("Todos")) {
                for (String option : optionsToCompile) {
                    char numberTest = option.charAt(option.length() - 1);
                    String fileName = "/tests/test" + numberTest + ".txt";
                    compileOneFileTest(fileName);
                    System.out.println();
                    System.out.println();
                    System.out.println();
                    System.out.println();
                }
            } else {
                char numberTest = ARG.charAt(ARG.length() - 1);
                String fileName = "/tests/test" + numberTest + ".txt";
                compileOneFileTest(fileName);
            }
        } else {
            printHowToCompile();
            System.exit(1);
        }
    }

}
