import java.io.*;

class Parser{
    /*
    Grammaire:

    DOCUMENTS         ->     DECLARATIONS CORPS
    DECLARATIONS      ->     \set{ID} {VALEUR} DECLARATIONS | e
    CORPS             ->     \begindoc SUITE_ELEMENTS \enddoc
    SUITE_ELEMENTS    ->     ELEMENTS SUITE_ELEMENTS | e
    ELEMENT           ->     MOT | LINEBREAK | \bf{SUITE_ELEMENTS} | \it{SUITE_ELEMENTS} | ENUMERATION | \couleur{VAL_COL}{SUITE_ELEMENTS}
    ENUMERATION       ->     \beginenum SUITE_ITEMS \endenum
    SUITE_ITEMS       ->     ITEM SUITE_ITEMS | e
    ITEM              ->     \item SUITE-ELEMENTS
    VAL_COL           ->     \constante_couleur | ID
*/
    protected LookAhead1 reader;

    public Parser(LookAhead1 r) {
        reader=r;
    }

    /*
    public Declarations debDeclaration() throws Exception {
        reader.eat(Sym.BEGINCOUL);
        return declarations();
    }

    public Declarations declarations() throws Exception {
        ValeurCouleur vc = null;
        if (reader.check(Sym.AG)) {
            reader.eat(Sym.AG);
            vc = new ValeurCouleur(reader.getString());
            return vc;
        } else if (reader.check(Sym.AD)) {
            reader.eat(Sym.AD);
            return new ConstructDeclarations(reader.getString(),vc);
        }else throw new Exception("don't know what to do for now");
    }
    */

    public Corps document() throws Exception {
        reader.eat(Sym.BEGINDOC);
        Corps corps = new ConstructCorps(suitelem());
        reader.eat(Sym.ENDDOC);
        reader.eat(Sym.EOF);
        return corps;
    }

    public SuiteElements suitelem() throws Exception{
        if(reader.check(Sym.ENDDOC)){
            return null;
        }
        return new ConstructSuiteElem(elem(),suitelem());

        /*if(reader.check(Sym.ITEM)) {
            return new ConstructSuiteItems(null, (SuiteItems)suiteItem());
        }else{
            return null;
        }*/
    }

    public Element elem() throws Exception{
        if(reader.check(Sym.MOT)){
            return mot();
        }else if(reader.check(Sym.LINEBREAK)){
            return linebreak();
        }else if(reader.check(Sym.BFBEG)){
            return bfbeg();
        }else if(reader.check(Sym.ITBEG)) {
            return itbeg();
        }else if(reader.check(Sym.AD)) {
            reader.eat(Sym.AD);
            return null;
        }else if(reader.check(Sym.BEGINENUM)) {
            return enumerate();
        }else if(reader.check(Sym.ENDENUM)) {
            reader.eat(Sym.ENDENUM);
            return null;
        }else{
            return null;
        }
    }

    public Element mot() throws Exception{
        String s = reader.getValue();
        reader.eat(Sym.MOT);
        return new Mot(s);
    }

    public Element linebreak() throws Exception{
        reader.eat(Sym.LINEBREAK);
        return new Linebreak("\n");
    }

    public Element bfbeg() throws Exception{
        reader.eat(Sym.BFBEG);
        if(reader.check(Sym.AG)) {
            reader.eat(Sym.AG);
            return new Bf(suitelem());
        }else throw new Exception("AG of Bf cannot be reduce");
    }

    public Element itbeg() throws Exception {
        reader.eat(Sym.ITBEG);
        if (reader.check(Sym.AG)) {
            reader.eat(Sym.AG);
            return new It(suitelem());
        } else throw new Exception("AG of It cannot be reduce");
    }

    public SuiteItems suiteItem() throws Exception{
        if(reader.check(Sym.ITEM)) {
            reader.eat(Sym.ITEM);
            return new ConstructSuiteItems(new Item((SuiteElements) suitelem()), (SuiteItems) suiteItem());
        } else return null;
    }

    public Enumeration enumerate() throws Exception{
        reader.eat(Sym.BEGINENUM);
        return new Enumeration(this.suiteItem());
    }
}