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

%%

"\\begindoc"          {return new Token(Sym.BEGINDOC);}
"\\enddoc"            {return new Token(Sym.ENDDOC);}
"\\linebreak"         {return new  LineBreakToken(Sym.LINEBREAK,yytext());}
//"\\set"               {return new SetCouleurToken(Sym.BEGINCOUL,yytext());}
//"\\couleur"           {return new CouleurToken(Sym.COULEUR,yytext());}
//"\\constante_couleur" {return new ValCoulToken(Sym.VALCOUL,yytext());}
{mot}                 {return new MotToken(Sym.MOT,yytext());}
"{"                   {return new Token(Sym.AG);}
"}"                   {return new Token(Sym.AD);}
"\\bf"                {return new Token(Sym.BFBEG);}
"\\it"                {return new Token(Sym.ITBEG);}
"\\beginenum"         {return new Token(Sym.BEGINENUM);}
"\\item"              {return new ItemToken(Sym.ITEM,yytext());}
"\\endenum"           {return new Token(Sym.ENDENUM);}
{blanc}               {}
<<EOF>>		          {return new Token(Sym.EOF);}