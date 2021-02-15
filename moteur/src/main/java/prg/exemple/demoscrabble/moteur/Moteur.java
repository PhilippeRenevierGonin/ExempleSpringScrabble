package prg.exemple.demoscrabble.moteur;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import prg.exemple.demoscrabble.webcontroller.MoteurWebControlleur;
import prg.exemple.demoscrabble.data.MotPositionne;
import prg.exemple.demoscrabble.data.EtatDuJeu;

@Component
@Scope("singleton")
public class Moteur implements Runnable {

    @Autowired
    MoteurWebControlleur ctrl;

    Thread partie ;


    EtatDuJeu etatDuJeu;
    private boolean exitOnFinish = false;

    public boolean lancerPartie() {
        boolean result = (partie == null);
        if (result) {
            System.out.println("Moteur > la partie est démarrée");
            etatDuJeu = new EtatDuJeu();
            partie = new Thread(this);
            partie.start();
        } else {
            System.out.println("Moteur > la partie est déjà démarrée");
        }
        return result;
    }

    @Override
    public void run() {
        for(int nbTour = 0; nbTour < 2; nbTour++) {
            etatDuJeu.ajouterLettres('a','b','c','d','m','o','t');
            MotPositionne motJoué = ctrl.demanderAuJoueurDeJoueur(getPlateau()) ;
            System.out.println("Moteur > "+ctrl.getNomJoueur()+" a joué : "+motJoué+ " (il n'y a pas de vérification)");
            etatDuJeu.addMotPlacé(motJoué);
        }
        System.out.println("Moteur > la partie est finie "+ etatDuJeu);
        partie = null;

        ctrl.envoyerFin();
        // fin brutale (pour abréger sur travis).
        if (getExitOnFinish()) System.exit(0);
    }


    public EtatDuJeu getPlateau() {
        return etatDuJeu;
    }


    public void aMethod() {}

    public void setExitOnFinish(boolean exitOnFinish) {
        this.exitOnFinish = exitOnFinish;
    }

    public boolean getExitOnFinish() {
        return exitOnFinish;
    }
}
