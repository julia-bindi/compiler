package Syntatical;

import Lexical.Tag;

import Lexical.Id;
import Lexical.Lexer;
import Lexical.Token;
import Lexical.TokenBuildException;

public class Syntatic {

    private Lexer lexical;
    private Token t;
    
    public Syntatic(Lexer l){
        this.lexical = l;
        this.nextToken();
    };

    private void nextToken(){
        try{
            this.t = this.lexical.getNextToken();
        }catch (TokenBuildException tbe) {
            System.out.println(tbe.getMessage() + "\n");
        }
    }

    private void error(){
        // Implementar melhor o erro depois
        System.out.println("Erro na linha " + this.lexical.line);
    }

    private void eat(int toEat){
        if(toEat==this.t.tag)
        {
            if(toEat!=Tag.EOF)
                this.nextToken();
        } else {
            this.error();
        }
    };

    private void program(){
        this.eat(Tag.START);
        //declList() é OPICIONAL
        this.declList();
        this.stmtList();
        this.eat(Tag.EXIT);
        this.eat(Tag.EOF);
    }

    private void declList(){
        this.decl();
        switch(this.t.tag){
            case Tag.INT:
            case Tag.FLOAT:
            case Tag.STRING:
                while(this.t.tag == Tag.INT ||
                      this.t.tag == Tag.FLOAT || 
                      this.t.tag == Tag.STRING)
                    this.decl();
                break;
            default:
                break;
        }
    }

    private void decl(){
        this.type();
        this.identList();
        this.eat(Tag.SEMICOLON);
    }

    private void type(){
        switch(this.t.tag){
            case Tag.INT:
                this.eat(Tag.INT);
                break;
            case Tag.FLOAT:
                this.eat(Tag.FLOAT);
                break;
            case Tag.STRING:
                this.eat(Tag.STRING);
                break;
            default:
                this.error();
                break;
        }
    }

    private void identList(){
        this.eat(Tag.ID);
        switch(this.t.tag){
            case Tag.COLON:
                while(this.t.tag == Tag.COLON){
                    this.eat(Tag.COLON);
                    this.eat(Tag.ID);
                }
                break;
        }
    }

    public void executeSyntatical(){

        this.program();

    }

}
