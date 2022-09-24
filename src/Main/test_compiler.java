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
        test_compiler.optionsToCompile.add("Test 1");
        test_compiler.optionsToCompile.add("Test 2");
        test_compiler.optionsToCompile.add("Test 3");
        test_compiler.optionsToCompile.add("Test 4");
        test_compiler.optionsToCompile.add("Test 5");
        test_compiler.optionsToCompile.add("Test 6");
        test_compiler.optionsToCompile.add("Test 2 with fix 1");
        test_compiler.optionsToCompile.add("Test 4 with fix 1");
        test_compiler.optionsToCompile.add("Test 4 with fix 2");
        test_compiler.optionsToCompile.add("All tests");
    }

    public static void printHowToCompile() {
        System.out.println("To compile, run the following command in the terminal: java -jar compiler.jar ARG");
        System.out.println("Where ARG can be:");
        for (String option : optionsToCompile)
            System.out.println("- " + option);
    }

    public static void compileOneFileTest(String filename) {
        SymbolTable ST = new SymbolTable();
        String projectPath = new File("").getAbsolutePath();
        Lexer lexical = new Lexer(projectPath.concat("/tests/" + filename + ".txt"), ST);

        System.out.println("--- Testando o arquivo " + filename + " ---\n");

        try {
            Token t;
            System.out.println("TOKENS");
            do {
                t = lexical.getNextToken();
                System.out.println(t.toString());
            } while (t.tag != 292);
            System.out.println("Info: Lexer test ended!\n");
        } catch (TokenBuildException tbe) {
            System.out.println(tbe.getMessage() + "\n");
        }

        ST.printTable();
    }

    public static void main(String[] args) {
        fillHowToCompile();

        if (args.length > 0) {
            String ARG = args[0];

            if (!optionsToCompile.contains(ARG)) {
                System.out.println(
                        "The given argument is not one of the available options! See the build instructions again below: \n");
                printHowToCompile();
                System.exit(1);
            } else if (ARG.contains("All")) {
                var optionsToTest = optionsToCompile.subList(0, optionsToCompile.size() - 1);
                for (String option : optionsToTest) {
                    char numberTest = option.charAt(5);
                    String fileName = "test" + numberTest;
                    if (option.length() >= 7) {
                        char numberFix = option.charAt(option.length() - 1);
                        fileName += "_fix" + numberFix;
                    }

                    compileOneFileTest(fileName);
                    System.out.println();
                }
            } else {
                String fileName;
                char numberTest = ARG.charAt(5);
                fileName = "test" + numberTest;

                if (ARG.length() >= 7) {
                    char numberFix = ARG.charAt(ARG.length() - 1);
                    fileName += "_fix" + numberFix;
                }

                compileOneFileTest(fileName);
            }
        } else {
            printHowToCompile();
            System.exit(1);
        }
    }

}
