package prg.exemple.demoscrabble.data;

public class MotPositionne {

    private String mot;
    private int abscisse;
    private int ordonnée;
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
        setOrdonnée(y);
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

    public void setOrdonnée(int ordonnée) {
        this.ordonnée = ordonnée;
    }

    public int getOrdonnée() {
        return ordonnée;
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
        return "("+getMot()+","+getAbscisse()+","+getOrdonnée()+","+dir+")";
    }

    public boolean equals(Object o) {
        if ((o != null) && (o instanceof MotPositionne)) {
            MotPositionne p = (MotPositionne) o;
            return (p.getHonrizontal() == getHonrizontal()) && (p.getOrdonnée() == getOrdonnée()) && (p.getAbscisse() == getAbscisse()) && (p.getMot().equals(getMot()));
         }
        else
            return false;
    }
}
