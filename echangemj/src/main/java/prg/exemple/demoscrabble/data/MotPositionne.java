package prg.exemple.demoscrabble.data;

import java.util.Objects;

public class MotPositionne {

    private String mot;
    private int abscisse;
    private int ordonnee;
    private boolean honrizontal;

    public MotPositionne() {
        this("motParDéfaut", 0, 0, true);
    }


    public MotPositionne(String mot, int x, int y) {
        this(mot, x, y, true);
    }

    public MotPositionne(String mot, int x, int y, boolean honrizontal) {
        setMot(mot);
        setAbscisse(x);
        setOrdonnee(y);
        setHonrizontal(honrizontal);
    }

    public void setMot(String mot) {
        this.mot = mot;
    }

    public String getMot() {
        return mot;
    }

    public void setAbscisse(int abscisse) {
        this.abscisse = abscisse;
    }

    public int getAbscisse() {
        return abscisse;
    }

    public void setOrdonnee(int ordonnee) {
        this.ordonnee = ordonnee;
    }

    public int getOrdonnee() {
        return ordonnee;
    }

    public void setHonrizontal(boolean honrizontal) {
        this.honrizontal = honrizontal;
    }

    public boolean getHonrizontal() {
        return honrizontal;
    }

    public String toString() {
        String dir = "horizontal";
        if (! getHonrizontal()) dir ="vertical";
        return "("+getMot()+","+getAbscisse()+","+ getOrdonnee()+","+dir+")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MotPositionne that = (MotPositionne) o;
        return abscisse == that.abscisse && ordonnee == that.ordonnee && honrizontal == that.honrizontal && Objects.equals(mot, that.mot);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mot, abscisse, ordonnee, honrizontal);
    }
}
