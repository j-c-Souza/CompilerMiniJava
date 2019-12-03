/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilerminijava;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PushbackReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Home
 */
public class Lexico {
    int lin=1;
    int col=0;
    ArrayList<Token> lt = new ArrayList<Token>();
    
    public void analisa(String nomeArq){        
        char at, nt;                
        PushbackReader src = null;
        try {
            src = new PushbackReader (new BufferedReader(new InputStreamReader(
                    new FileInputStream(nomeArq), "US-ASCII")));
        } catch (UnsupportedEncodingException | FileNotFoundException ex) {
            Logger.getLogger(Lexico.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            while(src.ready()){
                at=(char)src.read();                
                while((at==' ' || at=='\n'|| at=='\r' || at=='\t') && src.ready()){                                        
                  if(at=='\n'){
                      lin++;
                      col=0;
                  }else if(at==' '){
                      col++;
                  }                  
                  at=(char)src.read();                                                          
                }
                if(src.ready()){    
                    col++;
                    lt.add(catchToken(at,src));
                }
            }        
        } catch (IOException ex) {
            Logger.getLogger(Lexico.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }

    
    private Token catchToken(char at, PushbackReader src){
        Token token = new Token();  
        int colI = col;
        char nt = ' ';
        if(Character.isDigit(at)){
            
            token = trataDigit(at,src);
            token.coluna=colI;
        }else{
            if(Character.isLetter(at)){
            
            token = trataID(at,src);
            token.coluna=colI;
            }else{
                try {
                    nt = (char)src.read();
                } catch (IOException ex) {
                    Logger.getLogger(Lexico.class.getName()).log(Level.SEVERE, null, ex);
                }
                switch(at){
                    case '>':
                        if(nt=='='){
                            token.tipo = Tipo.GEQUAL;
                            token.lexema = ">=";
                            token.coluna=col;
                            col++;
                        }else{
                            token.tipo = Tipo.GREATER;
                            token.lexema = ">";
                            token.coluna=col;
                        }
                    break;
                    case '<':
                        if(nt=='='){
                            token.tipo = Tipo.LEQUAL;
                            token.lexema = "<=";
                            token.coluna = col;
                            col++;
                        }else{
                            token.tipo = Tipo.LESS;
                            token.lexema = "<";
                            token.coluna=col;
                        }
                    break;
                    case '=':
                        if(nt=='='){
                            token.tipo = Tipo.EQUAL;
                            token.lexema = "==";
                            token.coluna=col;
                            col++;
                        }else{
                            token.tipo = Tipo.ASSIGN;
                            token.lexema = "=";
                            token.coluna=col;
                        }
                    break;
                    case '!':
                        if(nt=='='){
                            token.tipo = Tipo.NOTEQUAL;
                            token.lexema = "!=";
                            token.coluna=col;
                            col++;
                        }else{
                            token.tipo = Tipo.NOT;
                            token.lexema = "!";
                            token.coluna=col;
                        }
                    break;
                    case '&':
                        if(nt=='&'){
                            token.tipo = Tipo.AND;
                            token.lexema = "&&";
                            token.coluna=col;
                            col++;
                        }else{
                            token.tipo = Tipo.ERROR;
                            token.lexema = "ERROR";
                            token.coluna=col;
                        }
                    break;
                    case '|':
                        if(nt=='|'){
                            token.tipo = Tipo.OR;
                            token.lexema = "||";
                            token.coluna=col;
                            col++;
                        }else{
                            token.tipo = Tipo.ERROR;
                            token.lexema = "ERROR";
                            token.coluna=col;
                        }
                    break;
                    case '+':
                        token.tipo = Tipo.PLUS;
                        token.lexema = "+";
                        token.coluna=col;
                    break;
                    case '-':
                        token.tipo = Tipo.MINUS;
                        token.lexema = "-";
                        token.coluna=col;
                    break;
                    case '*':
                        token.tipo = Tipo.TIMES;
                        token.lexema = "*";
                        token.coluna=col;
                    break;
                    case '/':
                        token.tipo = Tipo.DIV;
                        token.lexema = "/";
                        token.coluna=col;
                    break;                    
                    case ',':
                        token.tipo = Tipo.COMMA;
                        token.lexema = ",";
                        token.coluna=col;
                    break;
                    case ';':
                        token.tipo = Tipo.SEMICOLON;
                        token.lexema = ";";
                        token.coluna=col;
                    break;
                    case '{':
                        token.tipo = Tipo.LCURLY;
                        token.lexema = "{";
                        token.coluna=col;
                    break;
                    case '}':
                        token.tipo = Tipo.RCURLY;
                        token.lexema = "}";
                        token.coluna=col;
                    break;
                    case '(':
                        token.tipo = Tipo.LPAREN;
                        token.lexema = "(";
                        token.coluna=col;
                    break;
                    case ')':
                        token.tipo = Tipo.RPAREN;
                        token.lexema = ")";
                        token.coluna=col;
                    break;                    
                    case '[':
                        token.tipo = Tipo.LBRACKET;
                        token.lexema = "[";
                        token.coluna=col;
                    break;                    
                    case ']':
                        token.tipo = Tipo.RBRACKET;
                        token.lexema = "]";
                        token.coluna=col;
                    break;                    
                    
                    default:
                        token.tipo = Tipo.ERROR;
                        token.lexema = "ERROR";
                        token.coluna=col;
                }
                try {
                src.unread(nt);
            } catch (IOException ex) {
                Logger.getLogger(Lexico.class.getName()).log(Level.SEVERE, null, ex);
            }
            }
        }        
        token.linha=lin;
        return token;
    }
    
    private Token trataDigit(char at, PushbackReader src){
        StringBuilder num = new StringBuilder(); 
        Token token = new Token();
        while(Character.isDigit(at)){
                num.append(at);
                col++;
            try {
                at = (char) src.read();                
            } catch (IOException ex) {
                Logger.getLogger(Lexico.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
            try {
                src.unread(at);
            } catch (IOException ex) {
                Logger.getLogger(Lexico.class.getName()).log(Level.SEVERE, null, ex);
            }            
            token.tipo=Tipo.NUMBER;
            token.lexema=num.toString();
            return token;
    }
    
    private Token trataID(char at, PushbackReader src){
        StringBuilder id = new StringBuilder();        
        while(Character.isDigit(at) || Character.isLetter(at) || at == '_' || at == '.'){
                id.append(at);
                col++;
            try {
                at = (char) src.read();                
            } catch (IOException ex) {
                Logger.getLogger(Lexico.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
            try {
                src.unread(at);
            } catch (IOException ex) {
                Logger.getLogger(Lexico.class.getName()).log(Level.SEVERE, null, ex);
            }
            Token token = new Token();
                switch(id.toString()){
                    case "class":token.tipo=Tipo.CLASS;
                    break;
                    case "return":token.tipo=Tipo.RETURN;
                    break;
                    case "public":token.tipo=Tipo.PUBLIC;
                    break;
                    case "private":token.tipo=Tipo.PRIVATE;
                    break;
                    case "static":token.tipo=Tipo.STATIC;
                    break;
                    case "int":token.tipo=Tipo.INT;
                    break;
                    case "boolean":token.tipo=Tipo.BOOLEAN;
                    break;
                    case "void":token.tipo=Tipo.VOID;
                    break;
                    case "this":token.tipo=Tipo.THIS;
                    break;
                    case "if":token.tipo=Tipo.IF;
                    break;
                    case "else":token.tipo=Tipo.ELSE;
                    break;
                    case "while":token.tipo=Tipo.WHILE;
                    break;
                    case "true":token.tipo=Tipo.TRUE;
                    break;
                    case "false":token.tipo=Tipo.FALSE;
                    break;
                    case "new":token.tipo=Tipo.NEW;
                    break;
                    case "main":token.tipo=Tipo.MAIN;
                    break;
                    case "String":token.tipo=Tipo.STRING;
                    break;
                    case "System.out.println":token.tipo=Tipo.PRINT;
                    break;
                    default:
                        token.tipo=Tipo.IDENTIFIER;
                }                                  
                token.lexema = id.toString();
            return token;
    }
    
    
}
