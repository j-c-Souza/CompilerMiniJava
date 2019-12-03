/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilerminijava;

/**
 *
 * @author Home
 */
public abstract class Parser {
    TabelaSimbolos ts;
    Lexico lexer;
    Token t;    
    private int pos = 0;
    
    public Parser(String nomeArq){
        ts = new TabelaSimbolos();
        lexer = new Lexico();
        lexer.analisa(nomeArq);
    }
    
    public abstract void parse();
    
    public void buscaToken(){
        t = lexer.lt.get(pos);
        pos++;
    }
}
