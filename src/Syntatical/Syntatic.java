package Syntatical;

import Lexical.Tag;

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
        throw new Error();
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
        switch(this.t.tag){
            case Tag.INT:
            case Tag.FLOAT:
            case Tag.STRING:
                this.declList();
                break;
        }
        this.stmtList();
        this.eat(Tag.EXIT);
        this.eat(Tag.EOF);
    }

    private void stmtList(){
        do{
            this.stmt();
        } while(this.t.tag == Tag.ID ||
                this.t.tag == Tag.IF ||
                this.t.tag == Tag.DO ||
                this.t.tag == Tag.SCAN ||
                this.t.tag == Tag.PRINT);
    }

    private void stmt(){
        switch(this.t.tag){
            case Tag.ID:
                this.assignStmt();
                this.eat(Tag.SEMICOLON);
                break;
            case Tag.IF:
                this.ifStmt();
                break;
            case Tag.DO:
                this.whileStmt();
                break;
            case Tag.SCAN:
                this.readStmt();
                this.eat(Tag.SEMICOLON);
                break;
            case Tag.PRINT:
                this.writeStmt();
                this.eat(Tag.SEMICOLON);
                break;
            default:
                this.error();
        }
    }

    private void assignStmt(){
        this.eat(Tag.ID);
        this.eat(Tag.ASSIGN);
        this.simpleExpr();
    }

    private void ifStmt(){
        this.eat(Tag.IF);
        this.condition();
        this.eat(Tag.THEN);
        this.stmtList();
        if(this.t.tag == Tag.ELSE){
            this.eat(Tag.ELSE);
            this.stmtList();
        }
        this.eat(Tag.END);
    }

    private void condition(){
        this.expression();
    }

    private void whileStmt(){
        this.eat(Tag.DO);
        this.stmtList();
        this.stmtSufix();
    }

    private void stmtSufix(){
        this.eat(Tag.WHILE);
        this.condition();
        this.eat(Tag.END);
    }

    private void readStmt(){
        this.eat(Tag.SCAN);
        this.eat(Tag.OPEN_PAR);
        this.eat(Tag.ID);
        this.eat(Tag.CLOSE_PAR);
    }

    private void writeStmt(){
        this.eat(Tag.PRINT);
        this.eat(Tag.OPEN_PAR);
        this.writable();
        this.eat(Tag.CLOSE_PAR);
    }

    private void writable(){
        switch(this.t.tag){
            case Tag.NOT:
            case Tag.SUB:
            case Tag.ID:
            case Tag.OPEN_PAR:
            case Tag.NUMERIC:
            case Tag.LITERAL:
                this.simpleExpr();
                break;
            default:
                this.error();
        }
    }

    private void expression(){
        this.simpleExpr();
        this.expressionAux();
    }

    private void expressionAux(){
        switch(this.t.tag){
            case Tag.EQ:
                this.eat(Tag.EQ);
                this.simpleExpr();
                break;
            case Tag.GREATER:
                this.eat(Tag.GREATER);
                this.simpleExpr();
                break;
            case Tag.GE:
                this.eat(Tag.GE);
                this.simpleExpr();
                break;
            case Tag.LESS:
                this.eat(Tag.LESS);
                this.simpleExpr();
                break;
            case Tag.LE:
                this.eat(Tag.LE);
                this.simpleExpr();
                break;
            case Tag.NE:
                this.eat(Tag.NE);
                this.simpleExpr();
                break;
            default: //lambda
                break;
        }
    }

    private void simpleExpr(){
        this.term();
        this.simpleExprAux();
    }

    private void simpleExprAux(){
        switch(this.t.tag){
            case Tag.ADD:
                this.eat(Tag.ADD);
                this.term();
                break;
            case Tag.SUB:
                this.eat(Tag.SUB);
                this.term();
                break;
            case Tag.OR:
                this.eat(Tag.OR);
                this.term();
                break;
            default: //lambda
                break;
        }
    }

    private void term(){
        this.factorA();
        this.termAux();
    }

    private void termAux(){
        switch(this.t.tag){
            case Tag.MULT:
                this.eat(Tag.MULT);
                this.term();
                break;
            case Tag.DIV:
                this.eat(Tag.DIV);
                this.term();
                break;
            case Tag.AND:
                this.eat(Tag.AND);
                this.term();
                break;
            default: //lambda
                break;
        }
    }

    private void factorA(){
        switch(this.t.tag){
            case Tag.NOT:
                this.eat(Tag.NOT);
                this.factor();
                break;
            case Tag.SUB:
                this.eat(Tag.SUB);
                this.factor();
                break;
            case Tag.ID:
            case Tag.NUMERIC:
            case Tag.LITERAL:
            case Tag.OPEN_PAR:
                this.factor();
                break;
            default:
                this.error();
        }
    }

    private void factor(){
        switch(this.t.tag){
            case Tag.ID:
                this.eat(Tag.ID);
                break;
            case Tag.NUMERIC:
                this.eat(Tag.NUMERIC);
                break;
            case Tag.LITERAL:
                this.eat(Tag.LITERAL);
                break;
            case Tag.OPEN_PAR:
                this.eat(Tag.OPEN_PAR);
                this.expression();
                this.eat(Tag.CLOSE_PAR);
                break;
            default:
                this.error();
        }
    }

    private void declList(){
        do{
            this.decl();
        } while(this.t.tag == Tag.FLOAT ||
                this.t.tag == Tag.INT ||
                this.t.tag == Tag.STRING);
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
