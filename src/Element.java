import com.sun.javafx.css.Declaration;

import java.util.ArrayList;
import java.util.HashMap;

abstract class Corps extends Document{
    abstract public String transform();
}

abstract class Declarations extends Document{
    abstract public String transform();
}

abstract class Document{
    abstract public String transform();
}


class ConstructDocument extends Document{
    Declarations d;
    Corps c;
    public ConstructDocument(Declarations d, Corps c) {
        this.d = d;
        this.c = c;
    }

    @Override
    public String transform() {
        if(d == null && c != null) return "<!DOCTYPE html>\n<html>" + c.transform() +"\n</html>";
        else if(d!= null && c==null) return "<!DOCTYPE html>\n<html>" +d.transform()+ "\n</html>";
        else if(c != null && d !=null) return "<!DOCTYPE html>\n<html>\n<head>" + d.transform()+ "\n</head>"+ c.transform() + "\n</html>";
        else return "<!DOCTYPE html>\n<html> \n</html>";
    }
}

class ConstructDeclarations extends Declarations{
    Cons_Col cv;
    Declarations d;
    public ConstructDeclarations (Cons_Col cv, Declarations d){
        this.d =d;
        this.cv = cv;
    }
    @Override
    public String transform() {
        if(this.cv == null && this.d == null) return "";
        else if(this.d == null && this.cv != null) return cv.transform();
        else if(this.d != null && this.cv == null) return this.d.transform();
        return cv.transform() + this.d.transform(); }
}

class Cons_Col extends Declarations{
    private String id;
    private String valeur;

    public Cons_Col(String id, String valeur){
        this.valeur = valeur;
        this.id = id;
    }

    @Override
    public String transform() {
        Parser.color.put(this.id,this.valeur);
        return "";
    }
}

class ValCol extends Declarations{
    private String id;
    private String valeur;
    public ValCol (String id, String valeur){
        this.id = id;
        this.valeur = valeur;
    }
    @Override
    public String transform() { return "\n<abbr title="+ this.id +">" + this.valeur+ "</abbr>"; }
}

class ConstructCol extends Element{
    private String valeur;
    SuiteElements se;

    public ConstructCol(String valeur, SuiteElements se){
        this.valeur = valeur;
        this.se = se;
    }

    @Override
    public String transform() {
        return "<font color = #" + valeur + " > " +se.transform() + " </font>";
    }
}

class ConstructCorps extends Corps{
    SuiteElements se;
    public ConstructCorps(SuiteElements se){
        this.se = se;
    }
    @Override
    public String transform() {
        if(se == null)  return "";
        return "\n<body>\n" + se.transform() + "\n</body>";
    }
}

abstract class Element extends Corps{
    abstract public String transform();
}

class Mot extends Element{
    private String value;
    public Mot (String value){
        this.value = value;
    }
    @Override
    public String transform(){
        return this.value;
    }
}

class Linebreak extends Element{
    private String value;
    public Linebreak(String value){
        this.value = value;
    }

    @Override
    public String transform() { return " <br>"; }
}

class It extends Element{
    SuiteElements s;
    public It(SuiteElements s){
        this.s = s;
    }
    public String transform() {
        if(this.s == null) return "</i>";
        else return "<i>" + s.transform() + "</i>";
    }
}

class Abb extends Element{
    private String ab;
    private String valeur;

    public Abb(String ab, String valeur){
        this.valeur = valeur;
        this.ab = ab;
    }

    @Override
    public String transform() {
        return "\n<abbr title=" + valeur.substring(1) + ">" + ab.substring(1) + "</abbr>\n";
    }
}

class Bf extends Element{
    SuiteElements s;
    public Bf(SuiteElements s){
        this.s = s;
    }
    @Override
    public String transform() {
        if(this.s == null) return "</b>";
        else return "<b>" + s.transform() + "</b>";
    }
}

abstract class Enumeration extends Element{
    public abstract String transform();
}

class ConstructEnumeration extends Enumeration{
    SuiteItems se;
    public ConstructEnumeration(SuiteItems se){
        this.se = se;
    }
    public String transform() {
        if(this.se == null) return "\n" + "<ol>" + "</ol>";
        else return "\n" + "<ol>" + se.transform() + "</ol>";
    }
}

abstract class SuiteElements extends Corps{
    abstract public String transform();
    abstract public boolean isEmpty();
}

class ConstructSuiteElem extends SuiteElements{
    Element e;
    Corps se;

    public ConstructSuiteElem(Element e1, Corps se1){
        this.e = e1;
        this.se = se1;
    }
    @Override
    public String transform() {
        if(this.e == null && this.se == null) return "";
        else if(this.se == null && this.e != null) return e.transform() + "";
        else if(this.e == null && this.se != null) return "" + se.transform();
        else return this.e.transform() + this.se.transform();
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}


class SuiteItems extends Enumeration{
    Item se;
    SuiteItems si;

    public SuiteItems(){
    }

    public SuiteItems(Item se1, SuiteItems si1){
        this.se = se1;
        this.si = si1;
    }

    @Override
    public String transform() {
        if(this.si == null && this.se == null) return "";
        else if(this.se == null && this.si != null) return si.transform() + "";
        else if(this.si == null && this.se != null) return "" + se.transform();
        else return this.se.transform() + this.si.transform();
    }
}


class Item extends SuiteItems{
    private SuiteElements se;
    public Item(SuiteElements se){
        this.se = se;
    }
    public String transform(){
        if(this.se == null) return "";
        else return "\n <li>" + se.transform() + "</li>";
    }
}

