package Lexical;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import SymbolTable.SymbolTable;
import SymbolTable.Types;

public class Lexer {

    public SymbolTable ST;

    // Compiler info variables
    public int line; // Current line in the analysis file
    private char ch; // Current character read

    // Scanning variables
    private String file_path;
    private BufferedReader file_scanner;
    private String current_scanning_line;
    private int current_index;

    private String prev_scanning_line;
    private int prev_index;

    public Lexer(String file, SymbolTable ST) {

        this.line = 1;
        this.ch = ' ';

        this.ST = ST;

        // Instanciate scanner
        this.file_path = file;
        try {
            FileReader f = new FileReader(file_path);
            file_scanner = new BufferedReader(f);
            current_scanning_line = file_scanner.readLine();
            current_index = 0;
        } catch(FileNotFoundException ex) {
            System.out.println("File not found at path " + file_path);
        } catch(IOException ex) {
            System.out.println(ex.getMessage()); 
        }

    }

    private void nextChar(){
        // read next char
        try {
            this.ch = current_scanning_line.charAt(current_index);
            current_index++;
        } catch(IndexOutOfBoundsException iobex) { // if out of string, read next line and next char
            try {
                prev_scanning_line = current_scanning_line;
                prev_index = current_index;
                current_scanning_line = file_scanner.readLine();
                this.line++;
                while(current_scanning_line != null && current_scanning_line.length() == 0) { // deal with empty lines
                    current_scanning_line = file_scanner.readLine();
                    this.line++;
                }
                if(current_scanning_line == null) {
                    this.ch = 3; // EOF
                }
                else {
                    current_index = 0;
                    char result = current_scanning_line.charAt(current_index);
                    current_index++;
                    this.ch = result;
                }
            } catch(IOException ioex) {
                System.out.println(ioex.getMessage()); 
            }
        }
    }

    private void nextLine(){
        try {
            prev_scanning_line = current_scanning_line;
            prev_index = current_index;
            current_scanning_line = file_scanner.readLine();
            this.line++;
            while(current_scanning_line != null && current_scanning_line.length() == 0) { // deal with empty lines
                current_scanning_line = file_scanner.readLine();
                this.line++;
                if(current_scanning_line == null) {
                    break;
                }
            }
            if(current_scanning_line == null) {
                this.ch = 3; // EOF
            } else {
                current_index = 0;
            }
        }
        catch(IOException ioex){
            System.out.println(ioex.getMessage()); 
        }
    }
    
    private boolean nextChar(char ch){
        this.nextChar();
        if(this.ch == ch){
            this.ch = ' ';
            return true;
        }
        this.unRead();
        return false;
    }

    private void unRead(){
        if(this.current_index != 0)
            this.current_index--;
        else{
            this.current_index = this.prev_index;
            this.current_scanning_line = this.prev_scanning_line;
        }
    }

    public Token getNextToken() throws TokenBuildException {

        if(this.ch == 3)
            return new Token(Tag.EOF); //EOF, não continuar lendo

        this.nextChar();
        
        while(true){ // necessário para, no caso dos comentários, reler o prox token
            
            // Ignorar espaços
            while(this.ch == ' ')
                this.nextChar();

            switch(this.ch){
                case ';':
                    return new Token(Tag.SEMICOLON);
                case '!':
                    return new Token(Tag.NOT);
                case '+':
                    return new Token(Tag.ADD);
                case '-':
                    return new Token(Tag.SUB);
                case '*':
                    return new Token(Tag.MULT);
                case '/':
                    if(this.nextChar('/')){
                        this.nextLine(); //comentário de uma linha
                        continue;
                    }
                    if(this.nextChar('*')){ //comentário multilinha
                        while(this.ch != '*' && !this.nextChar('/')){
                            if(this.ch == 3) // EOF junto com comentario multilinha
                                throw new TokenBuildException(this.line, 
                                                              "Multiline Comment Not Closed");
                            this.nextChar();
                        }
                        this.nextChar(); // ignorar o '*' no final
                        this.nextChar(); // ignorar o '/' no final
                        continue;
                    }                    
                    return new Token(Tag.DIV);
                case '(':
                    return new Token(Tag.OPEN_PAR);
                case ')':
                    return new Token(Tag.CLOSE_PAR);
                case ',':
                    return new Token(Tag.COLON);
                case '.':
                    return new Token(Tag.DOT);
                case '{':
                    String literal = "{";
                    this.nextChar();
                    while(this.ch != '}'){
                        literal += this.ch;
                        this.nextChar();
                    }
                    literal += "}";
                    return new Literal(Tag.LITERAL, literal);
                case '=':
                    if(this.nextChar('='))
                        return new Token(Tag.EQ);
                    return new Token(Tag.ASSIGN);
                case '<':
                    if(this.nextChar('>'))
                        return new Token(Tag.NE);
                    else if(this.nextChar('='))
                        return new Token(Tag.LE);
                    return new Token(Tag.LESS);
                case '>':
                    if(this.nextChar('='))
                        return new Token(Tag.GE);
                    return new Token(Tag.GREATER);
                case '&':
                    if(this.nextChar('&'))
                        return new Token(Tag.AND);
                    throw new TokenBuildException(this.line);
                case '|':
                    if(this.nextChar('|'))
                        return new Token(Tag.OR);
                    throw new TokenBuildException(this.line);
                case 3:
                    return new Token(Tag.EOF);
                default:
                    if(Character.isLetter(this.ch) || this.ch == '_'){ // probable Id
                        String lexem = "" + this.ch;
                        this.nextChar();
                        int beginLine = this.line, endLine = this.line; //Fix multiline var
                        while(Character.isLetterOrDigit(this.ch) && beginLine == endLine){
                            lexem += this.ch;
                            this.nextChar();
                            endLine = this.line;
                        }
                        this.unRead();
                        return ST.put(new Id(Tag.ID, lexem, Types.VOID));
                    }else if(Character.isDigit(this.ch)){ // Probable Int or Float
                        String value = "" + this.ch;
                        this.nextChar();
                        int beginLine = this.line, endLine = this.line; //Fix multiline var
                        while(Character.isDigit(this.ch) && beginLine == endLine){
                            value += this.ch;
                            this.nextChar();
                            endLine = this.line;
                        }
                        this.unRead();
                        if(this.nextChar('.')){ // Probable float
                            value += '.';
                            this.nextChar();
                            while(Character.isDigit(this.ch) && beginLine == endLine){
                                value += this.ch;
                                this.nextChar();
                                endLine = this.line;
                            }
                            this.unRead();
                        }
                        return new Numeric(Tag.NUMERIC, value);
                    }
                throw new TokenBuildException(this.line);
            }
        }
    }
    
}
