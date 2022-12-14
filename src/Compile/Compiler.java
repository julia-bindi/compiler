package Compile;

import java.io.File;

import Lexical.Lexer;
import Lexical.Token;
import Lexical.TokenBuildException;
import SymbolTable.SymbolTable;
import Syntatical.Syntatic;

public class Compiler {

    private Lexer lexical;
    private Syntatic syntatical;
    private SymbolTable ST;

    private String option = "lexical"; //lexical, syntatical or semantical

    public Compiler(String file, String option){

        this.ST = new SymbolTable();
        
        this.option = option;
        
        String projectPath = new File("").getAbsolutePath();
        this.lexical = new Lexer(projectPath.concat("/tests/" + file), ST);

        this.syntatical = new Syntatic(this.lexical, this.ST);
        
    }

    public void executeCompile(){

        // Control the type of the compilation
        if(option=="lexical"){
            try {
                Token t;
                System.out.println("TOKENS");
                do {
                    t = this.lexical.getNextToken();
                    System.out.println(t.toString());
                } while (t.tag != 292);
                System.out.println("Info: Lexer test ended!\n");
            } catch (TokenBuildException tbe) {
                System.out.println(tbe.getMessage() + "\n");
            }

            ST.printTable();
        }else if(option=="syntatical"){
            try {
                this.syntatical.executeSyntatical();
            } catch (Exception e) {
                System.out.println("Erro na linha " + this.lexical.line);
                System.out.println(e.getMessage());
                this.syntatical.error = true;
            }
            if(this.syntatical.error)
                System.out.println("Análise falhou, erro encontrado");
            else
                System.out.println("Análise concluída!");
        }

    }
    
}
