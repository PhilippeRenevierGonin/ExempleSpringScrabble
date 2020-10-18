package prg.exemple.demoscrabble.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JoueurWebControlleur {

    @Autowired
    Joueur joueur ;


    @GetMapping("/jouer")
    public String jouer() {
        System.out.println("Joueur > on me demande de jouer");
        return joueur.jouer();
    }
}
