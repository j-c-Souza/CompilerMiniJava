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
public enum Tipo {      
      IDENTIFIER,
      NUMBER  ,

    // Operators
      GREATER   ,
      LESS      ,
      EQUAL     ,
      LEQUAL    ,
      GEQUAL    ,
      NOTEQUAL  ,
      AND       ,
      OR        ,
      NOT       ,
      PLUS      ,
      MINUS     ,
      TIMES     ,
      DIV       ,

      ASSIGN    ,

    // Keywords
      CLASS     ,
      RETURN    ,
      PUBLIC    ,
      PRIVATE   ,
      STATIC    ,
      INT       ,
      BOOLEAN   ,
      VOID      ,
      THIS      ,
      IF        ,
      ELSE      ,
      WHILE     ,
      TRUE      ,
      FALSE     ,
      NEW       ,
      MAIN      ,
      STRING    ,

    // Punctuatio     
      COMMA     ,
      SEMICOLON ,

    // Brackets
      LPAREN    ,
      RPAREN    ,
      LBRACKET  ,
      RBRACKET  ,
      LCURLY    ,
      RCURLY    ,
    // Special tos      
      ERROR     ,
      PRINT     ;
}
