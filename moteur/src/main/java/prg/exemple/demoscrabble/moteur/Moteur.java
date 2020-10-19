package prg.exemple.demoscrabble.moteur;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import prg.exemple.demoscrabble.data.EtatDuJeu;
import prg.exemple.demoscrabble.data.MotPositionne;

@Component
@Scope("singleton")
public class Moteur implements Runnable{

    @Autowired
    MoteurWebController ctrl;

    Thread partie;
    EtatDuJeu plateau;

    public void lancerPartie() {
        if (partie == null) {
            partie = new Thread(this);
            partie.start();
        }
        else {
            System.out.println("Moteur > Une partie est déjà commencé.");
        }
    }


    @Override
    public void run() {
        plateau = new EtatDuJeu();
        String nom = ctrl.getNomJoueur();

        for(int nbTour = 0; nbTour < 2; nbTour++) {
            System.out.println("Moteur > la partie commence avec "+nom);
            plateau.ajouterLettres('a','b','c','d','m','o','t');
            MotPositionne motJoué = ctrl.demanderAuJoueurDeJoueur(plateau) ;
            System.out.println("Moteur > "+nom+" a joué : "+motJoué);
            plateau.ajouterMot(motJoué);
        }


        partie = null;
    }


}
