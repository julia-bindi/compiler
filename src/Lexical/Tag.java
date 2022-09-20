package Lexical;

import java.util.List;

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
        NE = 272,              // <>
        GREATER = 273,         // >
        GE = 274,              // >=
        LESS = 275,            // <
        LE = 276,              // <=
        AND = 277,             // &&
        OR = 278,              // ||
        ADD = 279,             // +
        SUB = 280,             // -
        MULT = 281,            // *
        DIV = 282,             // /
        //Symbols
        OPEN_PAR = 283,        // (
        CLOSE_PAR = 284,       // )
        OPEN_CUR = 285,        // {
        CLOSE_CUR = 286,       // }
        SEMICOLON = 287,       // ;
        COLON = 288,           // ,
        DOT = 289,             // .
        DOUBLE_QUOTES = 290,   // "
        //Special
        ID = 291,
        EOF = 292,
        NUMERIC = 293;

}
