package Main;

import Compile.Compiler;

import java.io.File;
import java.util.ArrayList;

public class test_compiler {
    public final static ArrayList<String> optionsToCompile = new ArrayList<String>(7);

    public static void fillHowToCompile() {
        File folder = new File("./tests");
        File[] tests = folder.listFiles();
        for(int i = 0; i < tests.length; i++){
            String test = tests[i].getName();
            if(!test.contains("fix")) {
                test_compiler.optionsToCompile.add("Test " + test.charAt(4));
            } else {
                if(test.charAt(10) == '.') // enable fixes beyound 10
                    test_compiler.optionsToCompile.add("Test " + test.charAt(4) + " with fix " + test.charAt(9));
                else
                    test_compiler.optionsToCompile.add("Test " + test.charAt(4) + " with fix " + test.charAt(9) + test.charAt(10));
            }
            System.out.println(tests[i].getName());
        }
        test_compiler.optionsToCompile.add("All tests");
    }

    public static void printHowToCompile() {
        System.out.println("To compile, run the following command in the terminal: java -jar compiler.jar ARG");
        System.out.println("Where ARG can be:");
        for (String option : optionsToCompile)
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
