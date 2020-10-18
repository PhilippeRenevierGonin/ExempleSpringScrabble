package prg.exemple.demoscrabble.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import prg.exemple.demoscrabble.data.MotPositionne;
import prg.exemple.demoscrabble.data.Plateau;

@RestController
public class JoueurWebControlleur {

    @Autowired
    Joueur joueur ;


    @PostMapping("/jouer")
    public MotPositionne jouer(@RequestBody Plateau plateau) {
        System.out.println("Joueur > on me demande de jouer sur "+plateau);
        return joueur.jouer();
    }
}
