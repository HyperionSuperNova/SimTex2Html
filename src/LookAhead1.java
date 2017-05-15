import java.io.*;
public class LookAhead1 {
    private Token current;
    Lexer lexer;

    public LookAhead1(Lexer l) throws Exception{
        lexer = l;
        current = lexer.yylex();
    }

    public boolean check(Sym s) throws Exception {
        return (current.symbol() == s);
    }

    public void eat(Sym s) throws Exception {
        if (!check(s)) {
            throw new Exception("\nCan't eat "+s+" current being "+current.symbol());
        }
        current=lexer.yylex();
    }

    public String getValue() throws Exception {
        if (current instanceof MotToken) {
            MotToken t = (MotToken) current;
            return t.getValue();
        }else if (current instanceof ItemToken){
            ItemToken t = (ItemToken) current;
            return t.getValue();
        }
        throw new Exception("LookAhead error: get value from a non-valued token");
    }

    public String getString(){
        return current.toString();
    }
}
