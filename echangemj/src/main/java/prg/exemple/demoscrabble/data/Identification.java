package prg.exemple.demoscrabble.data;

public class Identification {

    private String nom;
    private String url;

    public Identification() {
        this("nom par défaut", "http://localhost:8080/");
    }

    public Identification(String nom_par_défaut, String url_par_defaut) {
        setNom(nom_par_défaut);
        setUrl(url_par_defaut);
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
}