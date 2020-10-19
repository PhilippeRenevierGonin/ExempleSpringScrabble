package prg.exemple.demoscrabble.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import prg.exemple.demoscrabble.data.MotPositionne;
import prg.exemple.demoscrabble.data.EtatDuJeu;

import java.util.concurrent.TimeUnit;

@RestController
public class JoueurWebControlleur {

    @Autowired
    Joueur joueur ;


    @PostMapping("/jouer")
    public MotPositionne jouer(@RequestBody EtatDuJeu etatDuJeu) {
        System.out.println("Joueur > on me demande de jouer sur "+ etatDuJeu);
        return joueur.jouer(etatDuJeu);
    }

    @PostMapping("/finir")
    public void finir() {
        // fin brutale (pour abréger sur travis), mais il faut répondre un peu après
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Joueur > fin du programme");
                try {
                    TimeUnit.MILLISECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    System.exit(0);
                }

            }
        });
        t.start();
    }
}
