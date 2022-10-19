package Main;

import Compile.Compiler;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class test_compiler {
    public final static HashMap<String, String> optionsToCompile = new HashMap<String, String>(7);

    public static void fillHowToCompile() {
        File folder = new File("./tests");
        File[] tests = folder.listFiles();
        for(int i = 0; i < tests.length; i++){
            String test = tests[i].getName();
            if(!test.contains("fix")) {
                test_compiler.optionsToCompile.put("Test " + test.charAt(4), test);
            } else {
                if(test.charAt(10) == '.') // enable fixes beyound 10
                    test_compiler.optionsToCompile.put("Test " + test.charAt(4) + " with fix " + test.charAt(9), test);
                else
                    test_compiler.optionsToCompile.put("Test " + test.charAt(4) + " with fix " + test.charAt(9) + test.charAt(10), test);
            }
        }
        test_compiler.optionsToCompile.put("All tests", "All");
    }

    public static void printHowToCompile() {
        System.out.println("To compile, run the following command in the terminal: java -jar compiler.jar ARG");
        System.out.println("Where ARG can be:");
        for (String option : optionsToCompile.keySet())
            System.out.println("- " + option);
    }

    public static void compileOneFileTest(String filename) {

        Compiler comp = new Compiler(filename, "syntatical");
        System.out.println("--- Testando o arquivo " + filename + " ---\n");

        comp.executeCompile();
    }

    public static void main(String[] args) {
        fillHowToCompile();

        if (args.length > 0) {
            String ARG = args[0];

            if (!optionsToCompile.keySet().contains(ARG)) {
                System.out.println(
                        "The given argument is not one of the available options! See the build instructions again below: \n");
                printHowToCompile();
                System.exit(1);
            } else if (ARG.contains("All")) {
                ArrayList<String> optionsToTest = new ArrayList<>(optionsToCompile.values());
                optionsToTest.sort(String::compareToIgnoreCase);
                for (String option : optionsToTest) {
                    if(option != "All")
                    {
                        compileOneFileTest(option);
                        System.out.println();
                    }
                }
            } else {
                compileOneFileTest(optionsToCompile.get(ARG));
            }
        } else {
            printHowToCompile();
            System.exit(1);
        }
    }

}
