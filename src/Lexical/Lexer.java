package Lexical;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Lexer {

    // Compiler info variables
    private int line; // Current line in the analysis file
    private char ch; // Current character read

    // Scanning variables
    private String file_path;
    private BufferedReader file_scanner;
    private String current_scanning_line;
    private int current_index;

    public Lexer(String file) {

        line = 1;
        ch = ' ';

        // Instanciate scanner
        file_path = file;
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
                current_scanning_line = file_scanner.readLine();
                if(current_scanning_line == null) {
                    this.ch = 3; // EOF
                }
            } catch(IOException ioex) {
                System.out.println(ioex.getMessage()); 
            }
            current_index = 0;
            char result = current_scanning_line.charAt(current_index);
            current_index++;
            this.ch = result;
        }
    }
    
    private boolean nextChar(char ch){
        // return nextChar == ch
        try {
            this.ch = current_scanning_line.charAt(current_index);
            current_index++;
            return this.ch == ch;
        } catch(IndexOutOfBoundsException iobex) { // if out of string, read next line and next char
            try {
                current_scanning_line = file_scanner.readLine();
                if(current_scanning_line == null) {
                    return false; // EOF
                }
            } catch(IOException ioex) {
                System.out.println(ioex.getMessage()); 
            }
            current_index = 0;
            this.ch = current_scanning_line.charAt(current_index);
            current_index++;
            return this.ch == ch;
        }
    }

    public Token getNextToken() {
        this.nextChar();

        return new Token(' ');
    }
    
}
