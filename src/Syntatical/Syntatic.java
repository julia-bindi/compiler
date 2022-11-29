package Syntatical;

import Lexical.Tag;
import Lexical.Lexer;
import Lexical.Token;
import Lexical.TokenBuildException;

import SymbolTable.SymbolTable;
import SymbolTable.Types;

public class Syntatic {

    private Lexer lexical;
    private SymbolTable ST;

    private Token t;

    public Boolean error;
    
    public Syntatic(Lexer l, SymbolTable ST){
        this.lexical = l;
        this.ST = ST;

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

    private void program()throws Exception{
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

    private void stmtList() throws Exception{
        // stmt-list -> stmt {stmt}
        do{
            this.stmt();
        } while(this.t.tag == Tag.ID ||
                this.t.tag == Tag.IF ||
                this.t.tag == Tag.DO ||
                this.t.tag == Tag.SCAN ||
                this.t.tag == Tag.PRINT);
    }

    private void stmt() throws Exception{
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

    private void assignStmt() throws Exception{
        // assign-stmt -> identifier = simple-expr
        this.eat(Tag.ID);
        this.eat(Tag.ASSIGN);
        this.simpleExpr();
    }

    private void ifStmt() throws Exception{
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

    private void condition() throws Exception{
        // condition -> expression
        Types t = this.expression();
        if(t != Types.BOOLEAN)
            throw new SemanticException(t, Types.BOOLEAN);
    }

    private void whileStmt() throws Exception{
        // while-stmt -> do stmt-list stmt-sufix
        this.eat(Tag.DO);
        this.stmtList();
        this.stmtSufix();
    }

    private void stmtSufix() throws Exception{
        // stmt-sufix -> while condition end
        this.eat(Tag.WHILE);
        this.condition();
        this.eat(Tag.END);
    }

    private void readStmt() throws Exception{
        // read-stmt -> scan ( identifier )
        this.eat(Tag.SCAN);
        this.eat(Tag.OPEN_PAR);
        this.eat(Tag.ID);
        this.eat(Tag.CLOSE_PAR);
    }

    private void writeStmt() throws Exception{
        // write-stmt -> print ( writable )
        this.eat(Tag.PRINT);
        this.eat(Tag.OPEN_PAR);
        this.writable();
        this.eat(Tag.CLOSE_PAR);
    }

    private void writable() throws Exception{
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

    private Types expression() throws Exception{
        // expressin -> simple-expr expression-aux
        Types t = this.simpleExpr();
        return this.expressionAux(t);
    }

    private Types expressionAux(Types t) throws Exception{
        // expression-aux -> [relop expression]
        switch(this.t.tag){
            case Tag.EQ:
                this.eat(Tag.EQ);
                this.expression();
                return Types.BOOLEAN;
            case Tag.GREATER:
                this.eat(Tag.GREATER);
                this.expression();
                return Types.BOOLEAN;
            case Tag.GE:
                this.eat(Tag.GE);
                this.expression();
                return Types.BOOLEAN;
            case Tag.LESS:
                this.eat(Tag.LESS);
                this.expression();
                return Types.BOOLEAN;
            case Tag.LE:
                this.eat(Tag.LE);
                this.expression();
                return Types.BOOLEAN;
            case Tag.NE:
                this.eat(Tag.NE);
                this.expression();
                return Types.BOOLEAN;
            // expression-aux -> lambda
            default:
                return t;
        }
    }

    private Types simpleExpr() throws Exception{
        // simple-expr -> term simple-expr-aux
        Types t = this.term();
        return this.simpleExprAux(t);
    }

    private Types simpleExprAux(Types t) throws Exception{
        // simple-expr-aux -> [addop simple-expr]
        switch(this.t.tag){
            case Tag.ADD:
                if(!(t.equals(Types.INT) || t.equals(Types.FLOAT)))
                    throw new SemanticException(t, Types.NUMERIC);
                this.eat(Tag.ADD);
                Types t1 = this.simpleExpr();
                if(!(t1.equals(Types.INT) || t1.equals(Types.FLOAT)))
                    throw new SemanticException(t, Types.NUMERIC);
                return t1;
            case Tag.SUB:
                if(!(t.equals(Types.INT) || t.equals(Types.FLOAT)))
                    throw new SemanticException(t, Types.NUMERIC);
                this.eat(Tag.SUB);
                Types t2 = this.simpleExpr();
                if(!(t2.equals(Types.INT) || t2.equals(Types.FLOAT)))
                    throw new SemanticException(t, Types.NUMERIC);
                return t2;
            case Tag.OR:
                if(!t.equals(Types.BOOLEAN))
                    throw new SemanticException(t, Types.BOOLEAN);
                this.eat(Tag.OR);
                Types t3 = this.simpleExpr();
                if(!t3.equals(Types.BOOLEAN))
                    throw new SemanticException(t, Types.BOOLEAN);
                return t3;
            // simple-expr-aux -> lambda
            default:
                return t;
        }
    }

    private Types term() throws Exception{
        // term -> dactor-a term-aux
        Types t = this.factorA();
        return this.termAux(t);
    }

    private Types termAux(Types t) throws Exception{
        // term-aux -> [mulop term]
        switch(this.t.tag){
            case Tag.MULT:
                this.eat(Tag.MULT);
                Types t1 = this.term();
                if(!(t == t1 && (t == Types.INT || t == Types.FLOAT)))
                    throw new SemanticException(t1, Types.NUMERIC);
                return t1;
            case Tag.DIV:
                this.eat(Tag.DIV);
                Types t2 = this.term();
                if(!(t == t2 && (t == Types.INT || t == Types.FLOAT)))
                    throw new SemanticException(t2, Types.NUMERIC);
                return Types.FLOAT;
            case Tag.AND:
                this.eat(Tag.AND);
                Types t3 = this.term();
                if(!(t == t3 && (t == Types.BOOLEAN)))
                    throw new SemanticException(t3, Types.NUMERIC);
                return Types.BOOLEAN;
            // term-aux -> lambda
            default:
                return t;
        }
    }

    private Types factorA() throws Exception{
        switch(this.t.tag){
            // factor-a -> ! factor
            case Tag.NOT:
                this.eat(Tag.NOT);
                Types t1 = this.factor();
                if(!t1.equals(Types.BOOLEAN))
                    throw new SemanticException(t1, Types.BOOLEAN);
                return t1;
            // factor-a -> - factor
            case Tag.SUB:
                this.eat(Tag.SUB);
                Types t2 = this.factor();
                if(!t2.equals(Types.INT) || !t2.equals(Types.FLOAT))
                    throw new SemanticException(t2, Types.NUMERIC);
                return t2;
            // factor-a -> factor
            case Tag.ID:
            case Tag.NUMERIC:
            case Tag.LITERAL:
            case Tag.OPEN_PAR:
                return this.factor();
            default:
                this.error();
                return Types.ERROR;
        }
    }

    private Types factor() throws Exception{
        switch(this.t.tag){
            // factor -> identifier
            case Tag.ID:
                Types t1 = this.ST.get(this.t.getLexeme()).type;
                this.eat(Tag.ID);
                return t1;
            // factor -> constant -> integer_const | numeric_const | literal
            case Tag.NUMERIC:
                String num = this.t.getValue();
                Types t2 = Types.ERROR;
                try {
                    Integer.parseInt(num);
                    t2 = Types.INT;
                } catch(NumberFormatException e) {
                    // Not int
                }
                // Check if float
                try {
                    Float.parseFloat(num);
                    t2 = Types.FLOAT;
                } catch(NumberFormatException e) {
                    // Not float
                }
                this.eat(Tag.NUMERIC);
                return t2;
            case Tag.LITERAL:
                this.eat(Tag.LITERAL);
                return Types.STRING;
            // factor -> ( expression )
            case Tag.OPEN_PAR:
                this.eat(Tag.OPEN_PAR);
                Types t3 = this.expression();
                this.eat(Tag.CLOSE_PAR);
                return t3;
            default:
                this.error();
                return Types.ERROR;
        }
    }

    private void declList() throws Exception{
        // decl-list -> decl {decl}
        do{
            this.decl();
        } while(this.t.tag == Tag.FLOAT ||
                this.t.tag == Tag.INT ||
                this.t.tag == Tag.STRING);
    }

    private void decl() throws Exception{
        // decl -> type ident-list ;
        Types t = this.type();
        this.identList(t);
        this.eat(Tag.SEMICOLON);
    }

    private Types type() throws Exception{
        switch(this.t.tag){
            // type -> int
            case Tag.INT:
                this.eat(Tag.INT);
                return Types.INT;
            // type -> float
            case Tag.FLOAT:
                this.eat(Tag.FLOAT);
                return Types.FLOAT;
            // type -> string
            case Tag.STRING:
                this.eat(Tag.STRING);
                return Types.STRING;
            default:
                this.error();
                return Types.ERROR;
        }
    }

    private void identList(Types t) throws Exception{
        // ident-list -> identifier {, identifier}
        if(this.ST.get(this.t.getLexeme()).type != Types.VOID)
            throw new SemanticException("Variable declared twice");
        this.ST.changeType(this.t.getLexeme(), t);
        this.eat(Tag.ID);
        while(this.t.tag == Tag.COLON){
            this.eat(Tag.COLON);
            if(this.ST.get(this.t.getLexeme()).type != Types.VOID)
                throw new SemanticException("Variable declared twice");
            this.ST.changeType(this.t.getLexeme(), t);
            this.eat(Tag.ID);
        }
    }

    public void executeSyntatical() throws Exception{

        this.program();

    }

}
