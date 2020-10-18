package prg.exemple.demoscrabble.data;

public class MotPositionne {

    private String mot;
    private int abscisse;
    private int ordonnée;

    public MotPositionne() {
        this("mot", 0, 0);
    }

    public MotPositionne(String mot, int x, int y) {
        setMot(mot);
        setAbscisse(x);
        setOrdonnée(y);
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

    public void setOrdonnée(int ordonnée) {
        this.ordonnée = ordonnée;
    }

    public int getOrdonnée() {
        return ordonnée;
    }
}
