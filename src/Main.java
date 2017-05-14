import java.io.*;

class Main {

    public static void main(String[] args) throws Exception {
        if (args.length < 1) {
            System.out.println("java Main <filename>");
            System.exit(1);
        }

        String filename = args[0];
        FileReader reader = new FileReader(filename);
        Lexer lexer = new Lexer(reader);
        LookAhead1 look = new LookAhead1(lexer);
        Parser parser = new Parser(look);
        try {
            Corps corps = parser.document();
            System.out.println(corps.toString());
            System.out.println(corps.transform());

            File f = new File("./projet.html");
            BufferedWriter writer = new BufferedWriter(new FileWriter(f,true));
            writer.write(corps.transform());
            writer.close();

            System.out.println("The expression is correct");

        }
        catch (Exception e){
            System.out.println("The expression is not correct.");
            System.out.println(e);
        }
    }
}
