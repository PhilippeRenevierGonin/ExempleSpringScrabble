package prg.exemple.demoscrabble.joueur;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JoueurWebController {

    @Autowired
    Joueur joueur;

    @GetMapping("/jouer")
    public String jouer() {
        System.out.println("Joueur > on me demande de jouer");
        return joueur.jouer();
    }
}
