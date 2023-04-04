package prg.exemple.demoscrabble.data;

import java.util.ArrayList;
import java.util.Objects;

public class EtatDuJeu {
    ArrayList<MotPositionne> listeDeMots ;
    ArrayList<Character> chariot;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EtatDuJeu etatDuJeu = (EtatDuJeu) o;
        return Objects.equals(listeDeMots, etatDuJeu.listeDeMots) && Objects.equals(chariot, etatDuJeu.chariot);
    }

    @Override
    public int hashCode() {
        return Objects.hash(listeDeMots, chariot);
    }

    public EtatDuJeu() {
        listeDeMots = new ArrayList<>();
        chariot = new ArrayList<>();
    }

    public ArrayList<MotPositionne> getListeDeMots() {
        return listeDeMots;
    }

    public void setListeDeMots(ArrayList<MotPositionne> listeDeMots) {
        this.listeDeMots = listeDeMots;
    }

    public ArrayList<Character> getChariot() {
        return chariot;
    }

    public void setChariot(ArrayList<Character> chariot) {
        this.chariot = chariot;
    }


    public void ajouterLettres(Character... lettres) {
        for(Character c : lettres) chariot.add(c);
    }

    public String toString() {
        return "[Plateau](contient "+listeDeMots.size()+" mot(s), et les lettres sont "+chariot+")";
    }

    public void addMotPlace(MotPositionne motJoue) {
        this.listeDeMots.add(motJoue);
    }
}
