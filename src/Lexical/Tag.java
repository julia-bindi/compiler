package Lexical;

public class Tag {
    public final static int
        //Keywords
        START = 256,           // start
        EXIT = 257,            // exit
        END = 258,             // end
        INT = 259,             // int
        FLOAT = 260,           // float
        STRING = 261,          // string
        IF = 262,              // if
        THEN = 263,            // then
        ELSE = 264,            // else
        DO = 265,              // do
        WHILE = 266,           // while
        SCAN = 267,            // scan
        PRINT = 268,           // print
        //Operators
        ASSIGN = 269,          // =
        EQ = 270,              // ==
        NOT = 271,             // !
        NE = 272,              // !=
        GRAETER = 273,         // >
        GE = 274,              // >=
        LESS = 275,            // <
        LE = 276,              // <=
        NAO_SEI = 277,         // <>
        AND = 278,             // &&
        OR = 279,              // ||
        ADD = 280,             // +
        SUB = 281,             // -
        MULT = 282,            // *
        DIV = 283,             // /
        //Symbols
        OPEN_PAR = 284,        // (
        CLOSE_PAR = 285,       // )
        OPEN_CUR = 286,        // {
        CLOSE_CUR = 287,       // }
        COLON = 288,           // ,
        SEMICOLON = 289,       // ;
        DOT = 290;             // .
}
