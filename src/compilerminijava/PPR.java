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
public class PPR extends Parser {

    PPR(String nomeArq) {
        super(nomeArq);
    }

    @Override
    public void parse() {
        lexer.lt.forEach(token -> System.out.println(token.tipo+"/"+token.lexema));		
        analisaMain();
    }

    public boolean erro(String input) {
        System.out.println(input);
        return false;
    }

    private boolean analisaMain() {
        buscaToken();
        if (t.tipo == Tipo.CLASS) {
            System.out.println(t.tipo + ": " + t.lexema);
            buscaToken();
            if (t.tipo == Tipo.IDENTIFIER) {
                System.out.println(t.tipo + ": " + t.lexema + ' ');
                ts.ts.put(t.lexema, "nomeprograma");
                buscaToken();
                if (t.tipo == Tipo.LCURLY) {
                    System.out.println(t.tipo + ": " + t.lexema);
                    buscaToken();
                    if (t.tipo == Tipo.PUBLIC) {
                        System.out.println(t.tipo + ": " + t.lexema);
                        buscaToken();
                        if (t.tipo == Tipo.STATIC) {
                            System.out.println(t.tipo + ": " + t.lexema);
                            buscaToken();
                            if (t.tipo == Tipo.VOID) {
                                System.out.println(t.tipo + ": " + t.lexema);
                                buscaToken();
                                if (t.tipo == Tipo.MAIN) {
                                    System.out.println(t.tipo + ": " + t.lexema);
                                    buscaToken();
                                    if (t.tipo == Tipo.LPAREN) {
                                        System.out.println(t.tipo + ": " + t.lexema);
                                        buscaToken();
                                        if (t.tipo == Tipo.STRING) {
                                            System.out.println(t.tipo + ": " + t.lexema);
                                            buscaToken();
                                            if (t.tipo == Tipo.LBRACKET) {
                                                System.out.println(t.tipo + ": " + t.lexema);
                                                buscaToken();
                                                if (t.tipo == Tipo.RBRACKET) {
                                                    System.out.println(t.tipo + ": " + t.lexema);
                                                    buscaToken();
                                                    if (t.tipo == Tipo.IDENTIFIER) {
                                                        System.out.println(t.tipo + ": " + t.lexema + ' ');
                                                        ts.ts.put(t.lexema, "args");
                                                        buscaToken();
                                                        if (t.tipo == Tipo.RPAREN) {
                                                            System.out.println(t.tipo + ": " + t.lexema);
                                                            buscaToken();
                                                            if (t.tipo == Tipo.LCURLY) {
                                                                System.out.println(t.tipo + ": " + t.lexema);
                                                                buscaToken();
                                                                analisaVarD();
                                                                while(t.tipo != Tipo.RCURLY){
                                                                analisaStatment();   
                                                                }                                                                
                                                                System.out.println(t.tipo + ": " + t.lexema);                                                                                                                                
                                                                buscaToken();
                                                                if(t.tipo== Tipo.RCURLY){
                                                                System.out.println(t.tipo + ": " + t.lexema);                                                                    
                                                                }else{
                                                                    System.out.println("Chaves esperada");
                                                                }
                                                            } else {
                                                                System.out.println("Chaves esperada");
                                                                return false;
                                                            }
                                                        } else {
                                                            System.out.println("Parenteses esperado");
                                                            return false;
                                                        }
                                                    } else {
                                                        System.out.println("Identificador esperado");
                                                        return false;
                                                    }
                                                } else {
                                                    System.out.println("Colchetes esperado");
                                                    return false;
                                                }
                                            } else {
                                                System.out.println("Colchetes esperado");
                                                return false;
                                            }
                                        } else {
                                            System.out.println("String esperada");
                                            return false;
                                        }
                                    } else {
                                        System.out.println("Parenteses esperado");
                                        return false;
                                    }
                                } else {
                                    System.out.println("Main esperado");
                                    return false;
                                }
                            } else {
                                System.out.println("Void esperado");
                                return false;
                            }
                        } else {
                            System.out.println("Static esperado");
                            return false;
                        }
                    } else {
                        System.out.println("Public esperado");
                        return false;
                    }
                } else {
                    System.out.println("Chaves esperado");
                    return false;
                }
            } else {
                System.out.println("Identifcador esperado");
                return false;
            }
        } else {
            System.out.println("Class esperado");
            return false;
        }
        return true;
    }

    private void analisaVarD() {
        boolean tipo; //true = int, false = boolean
        while (t.tipo == Tipo.INT || t.tipo == Tipo.BOOLEAN) {
            tipo = t.tipo == Tipo.INT;
            System.out.println(t.tipo + ": " + t.lexema);
            buscaToken();
            if (t.tipo == Tipo.IDENTIFIER) {
                ts.ts.putIfAbsent(t.lexema, "var");
                System.out.println(t.tipo + ": " + t.lexema);
                buscaToken();
                if (t.tipo == Tipo.SEMICOLON) {
                    System.out.println(t.tipo + ": " + t.lexema);
                    buscaToken();
                } else {
                    System.out.println("Ponto-virgula esperado");
                }
            } else {
                System.out.println("Identificador esperado");
            }
        }
    }

    private boolean analisaStatment() {
        switch (t.tipo) {
            case LCURLY:
                System.out.println(t.tipo + ": " + t.lexema);
                buscaToken();
                analisaStatment();
                if(t.tipo == Tipo.RCURLY){
                    return true;                
                }else{
                    System.out.println("Chaves esperado");
                }
                break;                
            case IF:
                System.out.println(t.tipo + ": " + t.lexema);
                buscaToken();
                if (t.tipo == Tipo.LPAREN) {
                    if (analisaExpression()) {
                  
                        
                        if (t.tipo == Tipo.RPAREN) {
                            System.out.println(t.tipo + ": " + t.lexema);
                            buscaToken();
                            analisaStatment();
                            if (t.tipo == Tipo.ELSE) {
                                System.out.println(t.tipo + ": " + t.lexema);
                                buscaToken();
                                analisaStatment();
                                return true;
                            } else {
                                System.out.println("Else esperado");
                            }
                        } else {
                            System.out.println("Parenteses esperado");
                        }
                }else {
                            System.out.println("Expressao invalida");
                        }
                } else {
                    System.out.println("Parenteses esperado");
                }
                break;
            case WHILE:
                System.out.println(t.tipo + ": " + t.lexema);
                buscaToken();
                if (t.tipo == Tipo.LPAREN) {
                    if (analisaExpression()) {
                        
                        if (t.tipo == Tipo.RPAREN) {
                            System.out.println(t.tipo + ": " + t.lexema);
                            buscaToken();
                            analisaStatment();         
                            return true;
                        } else {
                            System.out.println("Parenteses esperado");
                        }
                    }else {
                     System.out.println("Expressao invalida");
                    }
                } else {
                    System.out.println("Parenteses esperado");
                }                                        
                break;
            case IDENTIFIER:                
                    System.out.println(t.tipo + ": " + t.lexema);
                    buscaToken();
                    if (t.tipo == Tipo.ASSIGN) {
                        System.out.println(t.tipo + ": " + t.lexema);
                        buscaToken();
                        if (analisaExpression()) {
                            
                            if(t.tipo == Tipo.SEMICOLON){
                                System.out.println(t.tipo + ": " + t.lexema);
                                buscaToken();
                                return true;
                            }else{
                                System.out.println("Ponto-virgula esperado");
                            }
                        }else {
                            System.out.println("Expressao invalida");
                        }
                    } else {
                        System.out.println("Atribuição esperado");
                    }                
                break;
            case PRINT:
                System.out.println(t.tipo + ": " + t.lexema);
                buscaToken();
                if (t.tipo == Tipo.LPAREN) {
                    System.out.println(t.tipo + ": " + t.lexema);
                    buscaToken();
                    if (analisaExpression()) {
                        
                        if (t.tipo == Tipo.RPAREN) {
                            System.out.println(t.tipo + ": " + t.lexema);
                            buscaToken();
                            if(t.tipo == Tipo.SEMICOLON){
                                System.out.println(t.tipo + ": " + t.lexema);
                                buscaToken();
                                return true;
                            }                                
                            else{
                                        System.out.println("Ponto-virgula esperado");
                                        }
                        } else {
                            System.out.println("Parenteses esperado");
                        }
                    }else {
                     System.out.println("Expressao invalida");
                    }
                } else {
                    System.out.println("Parenteses esperado");
                }          
             break;
            
        }
        return true;
    }

    private boolean analisaExpression() {
            switch(t.tipo){
                case NUMBER:
                    System.out.println(t.tipo + ": " + t.lexema);
                    buscaToken();   
                    if(t.tipo==Tipo.PLUS || t.tipo==Tipo.MINUS || t.tipo==Tipo.DIV || t.tipo==Tipo.TIMES){
                        analisaExpression();
                    }
                    return true;                
                case TRUE:
                    System.out.println(t.tipo + ": " + t.lexema);
                    buscaToken();
                    return true;
                case FALSE:
                    System.out.println(t.tipo + ": " + t.lexema);
                    buscaToken();
                    return true;
                case IDENTIFIER:
                    System.out.println(t.tipo + ": " + t.lexema);
                    buscaToken();
                    return true;
                case NOT:
                    System.out.println(t.tipo + ": " + t.lexema);
                    buscaToken();
                    analisaExpression();
                    return true;
                case PLUS:
                    System.out.println(t.tipo + ": " + t.lexema);
                    buscaToken();
                    analisaExpression();
                    break;
                    case DIV:
                    System.out.println(t.tipo + ": " + t.lexema);
                    buscaToken();
                    analisaExpression();
                    break;
                    case MINUS:
                    System.out.println(t.tipo + ": " + t.lexema);
                    buscaToken();
                    analisaExpression();
                    break;
                    case TIMES:
                    System.out.println(t.tipo + ": " + t.lexema);
                    buscaToken();
                    analisaExpression();
                    break;
            }
        return true;
    }
}
