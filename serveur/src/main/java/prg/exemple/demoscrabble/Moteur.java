package prg.exemple.demoscrabble;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
public class Moteur {
    @Autowired
    MoteurWebControlleur ctrl;

    public void lancerPartie() {
        String motJoué = ctrl.demanderAuJoueurDeJoueur() ;
        System.out.println("Moteur > le joueur a joué : "+motJoué);
    }
}
