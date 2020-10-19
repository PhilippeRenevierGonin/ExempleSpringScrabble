package prg.exemple.demoscrabble.data;

public class Identification {

    private String nom;
    private String url;

    public Identification() {
        this("nom par défaut", "http://localhost:0081/");
    }

    public Identification(String nom, String url) {
        setNom(nom);
        setUrl(url);
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public String toString() {
        return getNom()+"["+getUrl()+"]";
    }
}