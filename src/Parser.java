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
        Corps corps = new ConstructCorps((SuiteElements)suitelem());
        reader.eat(Sym.ENDDOC);
        reader.eat(Sym.EOF);
        return corps;
    }

    public Corps suitelem() throws Exception{
        if(reader.check(Sym.MOT)){
            return mot();
        }else if(reader.check(Sym.LINEBREAK)){
            return linebreak();
        }else if(reader.check(Sym.BFBEG)){
            return bfbeg();
        }else if(reader.check(Sym.ITBEG)) {
            return itbeg();
        }else if(reader.check(Sym.BEGINENUM)) {
            reader.eat(Sym.BEGINENUM);
            return new Enumeration((SuiteItems)this.suiteItem());
        }else if(reader.check(Sym.ENDENUM)) {
            reader.eat(Sym.ENDENUM);
            return new ConstructSuiteElem(null, suitelem());
        }else if(reader.check(Sym.ITEM)) {
            return new ConstructSuiteItems(null, (SuiteItems)suiteItem());
        }else{
            return null;
        }
    }

    public Corps mot() throws Exception{
        String s = reader.getValue();
        reader.eat(Sym.MOT);
        return new ConstructSuiteElem(new Mot(s), (SuiteElements)suitelem());
    }

    public Corps linebreak() throws Exception{
        reader.eat(Sym.LINEBREAK);
        return new ConstructSuiteElem(new Linebreak("\n"), (SuiteElements)suitelem());
    }

    public Corps bfbeg() throws Exception{
        reader.eat(Sym.BFBEG);
        if(reader.check(Sym.AG)) {
            reader.eat(Sym.AG);
            Corps c = new ConstructSuiteElem(new Bf((SuiteElements)suitelem()), (SuiteElements) suitelem());
            reader.eat(Sym.AD);
            return c;
        }else throw new Exception("AG of Bf cannot be reduce");
    }

    public Corps itbeg() throws Exception {
        reader.eat(Sym.ITBEG);
        if (reader.check(Sym.AG)) {
            reader.eat(Sym.AG);
            Corps c = new ConstructSuiteElem(new It((SuiteElements) suitelem()), suitelem());
            reader.eat(Sym.AD);
            return c;
        } else throw new Exception("AG of It cannot be reduce");
    }

    public Corps suiteItem() throws Exception{
        if(reader.check(Sym.ITEM)) {
            reader.eat(Sym.ITEM);
            return new ConstructSuiteItems(new Item((SuiteElements) suitelem()), (SuiteItems) suiteItem());
        } else return suitelem();
    }
}