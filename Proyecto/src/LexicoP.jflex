
%%
%cup
%%
SEL			{ return new java_cup.runtime.Symbol(sym.SEL);} 
PRO			{ return new java_cup.runtime.Symbol(sym.PRO);}
UNI			{ return new java_cup.runtime.Symbol(sym.UNI);}
DIF			{ return new java_cup.runtime.Symbol(sym.DIF);}
x			{ return new java_cup.runtime.Symbol(sym.PROC);}
INT			{ return new java_cup.runtime.Symbol(sym.INT);}
AND		    { return new java_cup.runtime.Symbol(sym.AND);} 
NOT		    { return new java_cup.runtime.Symbol(sym.NOT);}
OR		    { return new java_cup.runtime.Symbol(sym.OR);} 
"="			{ return new java_cup.runtime.Symbol(sym.EQ);}
"!="		{ return new java_cup.runtime.Symbol(sym.DIFERENTE);}
">"			{ return new java_cup.runtime.Symbol(sym.MAYOR);} 
"<"			{ return new java_cup.runtime.Symbol(sym.MENOR);}
"("			{ return new java_cup.runtime.Symbol(sym.IPAREN);}
")"			{ return new java_cup.runtime.Symbol(sym.DPAREN);}
,			{ return new java_cup.runtime.Symbol(sym.COMA);}
\"		    { return new java_cup.runtime.Symbol(sym.COMILLAS);} 
"+"			{ return new java_cup.runtime.Symbol(sym.SUMA);}
"-"			{ return new java_cup.runtime.Symbol(sym.RESTA);}
"*"			{ return new java_cup.runtime.Symbol(sym.MULTI);}
[A-Za-z]+(_[a-z]+)*  { return new java_cup.runtime.Symbol(sym.CADENA, new String(yytext()));} 
[0-9][0-9]\/[0-9][0-9]\/[0-9][0-9][0-9][0-9]            { return new java_cup.runtime.Symbol(sym.FECHA, new String(yytext()));}
[0-9]+		{ return new java_cup.runtime.Symbol(sym.NUMERO, new Integer(yytext()));}
[ \n\t\r]+	{}