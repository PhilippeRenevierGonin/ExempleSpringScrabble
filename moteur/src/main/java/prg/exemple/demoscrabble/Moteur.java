package prg.exemple.demoscrabble;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
public class Moteur implements Runnable {
    @Autowired
    MoteurWebControlleur ctrl;

    Thread partie ;

    public void lancerPartie() {
        if (partie == null) {
            System.out.println("Moteur > la partie est démarrée");
            partie = new Thread(this);
            partie.start();
        } else {
            System.out.println("Moteur > la partie est déjà démarrée");
        }
    }

    @Override
    public void run() {
        String motJoué = ctrl.demanderAuJoueurDeJoueur() ;
        System.out.println("Moteur > le "+ctrl.getNomJoueur()+" a joué : "+motJoué);
        System.out.println("Moteur > la partie est finie");
        partie = null;
    }
}
