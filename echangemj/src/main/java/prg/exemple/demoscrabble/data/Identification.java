package prg.exemple.demoscrabble.data;

import java.util.Objects;

public class Identification {

    private String nom;
    private String url;

    public Identification() {
        this("nom par défaut", "http://localhost:8080/");
    }

    public Identification(String nom_par_defaut, String url_par_defaut) {
        setNom(nom_par_defaut);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Identification that = (Identification) o;
        return Objects.equals(nom, that.nom) && Objects.equals(url, that.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nom, url);
    }
}