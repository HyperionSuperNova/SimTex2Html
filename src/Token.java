class Token{
    protected Sym symbol;

    public Token(Sym s){
        this.symbol = s;
    }

    public Sym symbol(){
        return symbol;
    }

}

class MotToken extends Token{
    private String mot;

    public MotToken(Sym s, String mot){
        super(s);
        this.mot = mot;
    }

    public String getValue(){
        return mot;
    }

}

class ItemToken extends Token{
    private String item;

    public ItemToken(Sym s, String item){
        super(s);
        this.item = item;
    }

    public String getValue(){
        return item;
    }
}
class LineBreakToken extends Token{
    private String lineBreak;

    public LineBreakToken(Sym s, String lineBreak){
        super(s);
        this.lineBreak = lineBreak;
    }

    public String getValue(){
        return lineBreak;
    }
}

class ValColToken extends Token{
    private String couleur;
    public ValColToken(Sym s, String couleur) {
        super(s);
        this.couleur = couleur;
    }

    public String getValue(){ return couleur; }
}

class AbbMotToken extends Token{
    private String texte;
    public AbbMotToken(Sym s, String texte){
        super(s);
        this.texte = texte;
    }
}