/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cp2;
/*
Ahmad Abdel Naser
 * 2013
 * Sterio007
 */


import java.util.Stack;

/**
LL1

S -> STS
STS > ST S' 
S' : # | ; STS 
ST :id = EXP
EXP : TE E'
E' : + TE E' | - TE E' | #
TE : FAC T'
T' :  * FAC T' / FAC T' | #
FAC : FIN F'
F' : ^ FIN F' | #
FIN : ( EXP ) | id | INT
INT : plus num | minus num | num

 
 
 
 
 =========================================
         a        +           *           (           )         $        
--------------------------------------------------------------------
G| G->E$     |         |         | G->E$    |          |       |                
--------------------------------------------------------------------
E| E->TK     |         |         |  E->TK  |          |       |
--------------------------------------------------------------------
K|           | K->+TK  |         |          | K->     | K->  |  
--------------------------------------------------------------------
T|    T->FH  |         |         |  T->FH  |          |       | 
--------------------------------------------------------------------
H|           |   H->   | H->*FH  |          |    H->  | H->  |                                                  
--------------------------------------------------------------------
F|    F->a   |         |         | F->(E)    |         |       |                                                  
--------------------------------------------------------------------
===============================================================================
 */
public class Test {
//input
   public String input="";//"i*i$"
    private int indexOfInput=-1;
    //Stack
    Stack <String> strack=new Stack<String>();
    //Table of rules
    String [][] table=
    {
     // { $,  num,  (,   ) ,  *,   + ,  - , plus ,/ , ; ,  id ,  = , ^ , minus}
        {null,null,null,null,null,null,null,null,null,null,"NS'",null,null,null}
            ,
        {null,null,null,null,null,null,null,null,null,null,"i=E",null,null,null}
            ,
        {null,"CT'","CT'",null,null,null,null,"CT'",null,null,"CT'",null,null,"CT'"}
            ,
        {"",null,null,"","","","",null,"","",null,null,"^FF'",null}
            ,
        {"",null,null,"",null,"+T'","-T'",null,null,"",null,null,null,null}
            ,
        {null,null,null,null,null,null,null,null,null,null,"M'",null,null,null}
            ,
        {"",null,null,"",null,"*CT'","","",null,"/CT'","",null,null,null}
    ,
        {"",null,null,null,null,null,null,null,null,";M",null,null,null,null}
            ,
        {null,"FF'","FF'",null,null,null,null,"FF'",null,null,"FF'",null,null,"FF'"}
            ,
        {null,"I","(E)",null,null,null,null,"I",null,null,"i",null,null,"I"}
            ,
         {null,"TE'","TE'",null,null,null,null,"TE'",null,null,"TE'",null,null,"TE'"}
            ,
          {null,"n",null,null,null,null,null,"pn",null,null,null,null,null,"mm"}
    };
    String [] nonTers={"M","N","T","F'","E'","S","T'","S,","C","F","E","I"};
String [] terminals={"$","n","(",")","*","+","-","p","/",";","i","=","^","m"};


public Test(String in)
{
this.input=in;
}

private  void pushRule(String rule)
{
for(int i=rule.length()-1;i>=0;i--)
{
char ch=rule.charAt(i);
String str=String.valueOf(ch);
push(str);
}
}

    //algorithm
public void algorithm    ()
{

    
    push(this.input.charAt(0)+"");//
    push("S");
    //Read one token from input
    
    String token=read();
    String top=null;
    
    do
    {
        top=this.pop();
        //if top is non-terminal
        if(isNonTerminal(top))
        {
        String rule=this.getRule(top, token);
        this.pushRule(rule);
        }
        else if(isTerminal(top))
        {
        if(!top.equals(token))
{
error("this token is not corrent , By Grammer rule . Token : ("+token+")");
}
else
{
    System.out.println("Matching: Terminal :( "+token+" )");
token =read();
//top=pop();

}
        }
        else
        {
        error("Never Happens , Because top : ( "+top+" )");
        }
        if(token.equals("$"))
        {
        break;
        }
        //if top is terminal
    
    }while(true);//out of the loop when $
    
    //accept
    if(token.equals("$"))
        {
         System.out.println("Input is Accepted by LL1");   
        }
    else
    {
     System.out.println("Input is not Accepted by LL1");   
    }
}

    private boolean isTerminal(String s) {
               for(int i=0;i<this.terminals.length;i++)
        {
        if(s.equals(this.terminals[i]))
        {
        return true;
        }

        }
              return false;
    }

    private boolean isNonTerminal(String s) {
        for(int i=0;i<this.nonTers.length;i++)
        {
        if(s.equals(this.nonTers[i]))
        {
        return true;
        }

        }
              return false;
    }

    private String read() {
        indexOfInput++;
        char ch=this.input.charAt(indexOfInput);
String str=String.valueOf(ch);

        return str;
    }

    private void push(String s) {
     this.strack.push(s);   
    }
        private String pop() {
   return this.strack.pop();   
    }

    private void error(String message) {
        System.out.println(message);
        throw new RuntimeException(message);
    }
    public String getRule(String non,String term)
    {
        
    int row=getnonTermIndex(non);
    int column=getTermIndex(term);
    String rule=this.table[row][column];
    if(rule==null)
    {
    error("There is no Rule by this , Non-Terminal("+non+") ,Terminal("+term+") ");
    }
    return rule;
    }

    private int getnonTermIndex(String non) {
       for(int i=0;i<this.nonTers.length;i++)
       {
       if(non.equals(this.nonTers[i]))
       {
       return i;
       }
       }
       error(non +" is not NonTerminal");
       return -1;
    }

    private int getTermIndex(String term) {
              for(int i=0;i<this.terminals.length;i++)
       {
       if(term.equals(this.terminals[i]))
       {
       return i;
       }
       }
       error(term +" is not Terminal");
       return -1;
    }
    
        //main
    public static void main(String[] args) {
        // TODO code application logic here
        
        Test parser=new Test("i=pn$");//i*i+(i+i)$
        parser.algorithm();
  
    }

}
/*
 
 */