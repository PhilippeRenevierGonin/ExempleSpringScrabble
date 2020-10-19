package prg.exemple.demoscrabble.moteur;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
public class Moteur implements Runnable{

    @Autowired
    MoteurWebController ctrl;

    Thread partie;

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
        System.out.println("Moteur > la partie commence.");
        String motJoué = ctrl.demanderAuJoueurDeJoueur() ;
        System.out.println("Moteur > le joueur a joué : "+motJoué);
        partie = null;
    }


}
