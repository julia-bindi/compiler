package Syntatical;

import Lexical.Tag;

import Lexical.Lexer;
import Lexical.Token;
import Lexical.TokenBuildException;

public class Syntatic {

    private Lexer lexical;
    private Token t;

    public Boolean error;
    
    public Syntatic(Lexer l){
        this.lexical = l;
        this.error = false;
        this.nextToken();
    };

    private void nextToken(){
        try{
            this.t = this.lexical.getNextToken();
        }catch (TokenBuildException tbe) {
            System.out.println(tbe.getMessage() + "\n");
        }
    }

    private void error(int tok){
        // Implementar melhor o erro depois
        this.error = true;
        System.out.println("Erro na linha " + this.lexical.line + ", token esperado: " + Tag.translationArray[tok-256] +
                           " | token encontrado: " + this.t.translatedTag);
        if(tok!=Tag.EOF)
            this.nextToken();
    }
    
    private void error(){
        // Implementar melhor o erro depois
        this.error = true;
        if(this.t.tag!=Tag.EOF)
            this.nextToken();
    }

    private void eat(int toEat){
        if(toEat==this.t.tag)
        {
            if(toEat!=Tag.EOF)
                this.nextToken();
        } else {
            this.error(toEat);
        }
    };

    private void program(){
        // program -> start [decl-list] stmt-list exit
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
        // stmt-list -> stmt {stmt}
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
            // stmt -> assign-stmt ;
            case Tag.ID:
                this.assignStmt();
                this.eat(Tag.SEMICOLON);
                break;
            // stmt -> if-stmt
            case Tag.IF:
                this.ifStmt();
                break;
            // stmt -> while-stmt
            case Tag.DO:
                this.whileStmt();
                break;
            // stmt -> read-stmt ;
            case Tag.SCAN:
                this.readStmt();
                this.eat(Tag.SEMICOLON);
                break;
            // stmt -> write-stmt ;
            case Tag.PRINT:
                this.writeStmt();
                this.eat(Tag.SEMICOLON);
                break;
            default:
                this.error();
        }
    }

    private void assignStmt(){
        // assign-stmt -> identifier = simple-expr
        this.eat(Tag.ID);
        this.eat(Tag.ASSIGN);
        this.simpleExpr();
    }

    private void ifStmt(){
        // if-stmt -> if condition then stmt-list [else stmt-list] end
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
        // condition -> expression
        this.expression();
    }

    private void whileStmt(){
        // while-stmt -> do stmt-list stmt-sufix
        this.eat(Tag.DO);
        this.stmtList();
        this.stmtSufix();
    }

    private void stmtSufix(){
        // stmt-sufix -> while condition end
        this.eat(Tag.WHILE);
        this.condition();
        this.eat(Tag.END);
    }

    private void readStmt(){
        // read-stmt -> scan ( identifier )
        this.eat(Tag.SCAN);
        this.eat(Tag.OPEN_PAR);
        this.eat(Tag.ID);
        this.eat(Tag.CLOSE_PAR);
    }

    private void writeStmt(){
        // write-stmt -> print ( writable )
        this.eat(Tag.PRINT);
        this.eat(Tag.OPEN_PAR);
        this.writable();
        this.eat(Tag.CLOSE_PAR);
    }

    private void writable(){
        // writable -> simple-expr
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
        // expressin -> simple-expr expression-aux
        this.simpleExpr();
        this.expressionAux();
    }

    private void expressionAux(){
        // expression-aux -> [relop expression]
        switch(this.t.tag){
            case Tag.EQ:
                this.eat(Tag.EQ);
                this.expression();
                break;
            case Tag.GREATER:
                this.eat(Tag.GREATER);
                this.expression();
                break;
            case Tag.GE:
                this.eat(Tag.GE);
                this.expression();
                break;
            case Tag.LESS:
                this.eat(Tag.LESS);
                this.expression();
                break;
            case Tag.LE:
                this.eat(Tag.LE);
                this.expression();
                break;
            case Tag.NE:
                this.eat(Tag.NE);
                this.expression();
                break;
            // expression-aux -> lambda
            default:
                break;
        }
    }

    private void simpleExpr(){
        // simple-expr -> term simple-expr-aux
        this.term();
        this.simpleExprAux();
    }

    private void simpleExprAux(){
        // simple-expr-aux -> [addop simple-expr]
        switch(this.t.tag){
            case Tag.ADD:
                this.eat(Tag.ADD);
                this.simpleExpr();
                break;
            case Tag.SUB:
                this.eat(Tag.SUB);
                this.simpleExpr();
                break;
            case Tag.OR:
                this.eat(Tag.OR);
                this.simpleExpr();
                break;
            // simple-expr-aux -> lambda
            default:
                break;
        }
    }

    private void term(){
        // term -> dactor-a term-aux
        this.factorA();
        this.termAux();
    }

    private void termAux(){
        // term-aux -> [mulop term]
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
            // term-aux -> lambda
            default:
                break;
        }
    }

    private void factorA(){
        switch(this.t.tag){
            // factor-a -> ! factor
            case Tag.NOT:
                this.eat(Tag.NOT);
                this.factor();
                break;
            // factor-a -> - factor
            case Tag.SUB:
                this.eat(Tag.SUB);
                this.factor();
                break;
            // factor-a -> factor
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
            // factor -> identifier
            case Tag.ID:
                this.eat(Tag.ID);
                break;
            // factor -> constant -> integer_const | numeric_const | literal
            case Tag.NUMERIC:
                this.eat(Tag.NUMERIC);
                break;
            case Tag.LITERAL:
                this.eat(Tag.LITERAL);
                break;
            // factor -> ( expression )
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
        // decl-list -> decl {decl}
        do{
            this.decl();
        } while(this.t.tag == Tag.FLOAT ||
                this.t.tag == Tag.INT ||
                this.t.tag == Tag.STRING);
    }

    private void decl(){
        // decl -> type ident-list ;
        this.type();
        this.identList();
        this.eat(Tag.SEMICOLON);
    }

    private void type(){
        switch(this.t.tag){
            // type -> int
            case Tag.INT:
                this.eat(Tag.INT);
                break;
            // type -> float
            case Tag.FLOAT:
                this.eat(Tag.FLOAT);
                break;
            // type -> string
            case Tag.STRING:
                this.eat(Tag.STRING);
                break;
            default:
                this.error();
                break;
        }
    }

    private void identList(){
        // ident-list -> identifier {, identifier}
        this.eat(Tag.ID);
        while(this.t.tag == Tag.COLON){
            this.eat(Tag.COLON);
            this.eat(Tag.ID);
        }
    }

    public void executeSyntatical(){

        this.program();

    }

}
