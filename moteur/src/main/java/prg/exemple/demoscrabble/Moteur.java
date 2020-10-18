package prg.exemple.demoscrabble;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import prg.exemple.demoscrabble.data.MotPositionne;
import prg.exemple.demoscrabble.data.Plateau;

@Component
@Scope("singleton")
public class Moteur implements Runnable {
    @Autowired
    MoteurWebControlleur ctrl;

    Thread partie ;

    Plateau plateau;

    public void lancerPartie() {
        if (partie == null) {
            System.out.println("Moteur > la partie est démarrée");
            plateau = new Plateau();
            partie = new Thread(this);
            partie.start();
        } else {
            System.out.println("Moteur > la partie est déjà démarrée");
        }
    }

    @Override
    public void run() {
        MotPositionne motJoué = ctrl.demanderAuJoueurDeJoueur() ;
        System.out.println("Moteur > "+ctrl.getNomJoueur()+" a joué : "+motJoué+ " (il n'y a pas de vérificatoin)");
        plateau.addMotPlacé(motJoué);
        System.out.println("Moteur > la partie est finie "+plateau);
        partie = null;
    }


    public Plateau getPlateau() {
        return plateau;
    }

}
