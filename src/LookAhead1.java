import java.io.*;
public class LookAhead1 {
    private Token current;
    Lexer lexer;

    public LookAhead1(Lexer l) throws Exception{
        lexer = l;
        current = lexer.yylex();
    }

    public boolean check(Sym s)
            throws Exception {
        /* check whether the first character is of type s*/
        return (current.symbol() == s);
    }

    public void eat(Sym s) throws Exception {
        /* consumes a token of type s from the stream,
           exception when the contents does not start on s.   */
        if (!check(s)) {
            throw new Exception("\nCan't eat "+s+" current being "+current.symbol());
        }
        System.out.println(current.symbol()); //for debug only!
        current=lexer.yylex();
        System.out.println("abc"+ current.symbol());
    }

    public String getValue() throws Exception {
        // it gives the value of the ValuedToken, or it rises an exception if not ValuedToken
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
