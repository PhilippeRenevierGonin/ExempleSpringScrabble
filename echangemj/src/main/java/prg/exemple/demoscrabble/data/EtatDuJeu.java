package prg.exemple.demoscrabble.data;

import java.util.ArrayList;

public class EtatDuJeu {
    ArrayList<MotPositionne> listeDeMots ;
    ArrayList<Character> chariot;

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


    public void ajouterMot(MotPositionne mot) {
        listeDeMots.add(mot);
    }

    public void ajouterLettres(Character... lettres) {
        for(Character c : lettres) chariot.add(c);
    }

    public String toString() {
        return "[Plateau](contien "+listeDeMots.size()+" mot(s), et les lettres sont "+chariot+")";
    }
}
