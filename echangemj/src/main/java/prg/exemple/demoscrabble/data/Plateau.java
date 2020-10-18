package prg.exemple.demoscrabble.data;

import java.util.ArrayList;

public class Plateau {
    ArrayList<MotPositionne> listeDeMots ;

    public Plateau() {
        listeDeMots = new ArrayList<>();
    }

    public ArrayList<MotPositionne> getListeDeMots() {
        return listeDeMots;
    }

    public void setListeDeMots(ArrayList<MotPositionne> listeDeMots) {
        this.listeDeMots = listeDeMots;
    }


    public String toString() {
        return "[Plateau](contien "+listeDeMots.size()+" mot(s))";
    }
}
