%%
%public
%class Lexer
%unicode
%standalone
%type Token
%line
%column

%{
%}


blanc = [ \t\n]
mot = [a-zA-Z0-9:;.,]+
valcol = [A-Z0-9][A-Z0-9][A-Z0-9][A-Z0-9][A-Z0-9][A-Z0-9]

%%

"\\begindoc"          {return new Token(Sym.BEGINDOC);}
"\\enddoc"            {return new Token(Sym.ENDDOC);}
"\\linebreak"         {return new  LineBreakToken(Sym.LINEBREAK,yytext());}
{mot}                 {return new MotToken(Sym.MOT,yytext());}
"{"                   {return new Token(Sym.AG);}
"}"                   {return new Token(Sym.AD);}
"\\bf"                {return new Token(Sym.BFBEG);}
"\\it"                {return new Token(Sym.ITBEG);}
"\\beginenum"         {return new Token(Sym.BEGINENUM);}
"\\item"              {return new ItemToken(Sym.ITEM,yytext());}
"\\endenum"           {return new Token(Sym.ENDENUM);}
"\\set"               {return new Token(Sym.SETCOL);}
{valcol}              {return new ValColToken(Sym.VALCOL, yytext());}
"\\couleur"           {return new Token(Sym.COULEUR);}
{blanc}               {}
<<EOF>>		          {return new Token(Sym.EOF);}