package prg.exemple.demoscrabble.joueur;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import prg.exemple.demoscrabble.data.EtatDuJeu;
import prg.exemple.demoscrabble.data.Identification;
import prg.exemple.demoscrabble.data.MotPositionne;

@Component
@Scope("singleton")
public class Joueur {

    Identification id = new Identification("Michel", "http://localhost:8081/");

    public Identification getId() {
        return id;
    }

    public void setId(Identification id) {
        this.id = id;
    }

    public MotPositionne jouer(EtatDuJeu plateau) {
        MotPositionne resultat = new MotPositionne("mot", 7, 7);
        System.out.println("Joueur > je joue "+resultat+" sur le plateau "+plateau);
        return resultat;
    }
}
