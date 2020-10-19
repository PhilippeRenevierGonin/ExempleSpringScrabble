package prg.exemple.demoscrabble.joueur;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import prg.exemple.demoscrabble.data.EtatDuJeu;
import prg.exemple.demoscrabble.data.MotPositionne;

@RestController
public class JoueurWebController {

    @Autowired
    Joueur joueur;

    @PostMapping("/jouer")
    public MotPositionne jouer(@RequestBody EtatDuJeu plateau) {
        System.out.println("Joueur > on me demande de jouer");
        return joueur.jouer(plateau);
    }
}
