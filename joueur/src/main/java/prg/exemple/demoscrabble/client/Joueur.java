package prg.exemple.demoscrabble.client;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import prg.exemple.demoscrabble.data.MotPositionne;

@Component
@Scope("singleton")
public class Joueur {

    public MotPositionne jouer() {
        System.out.println("Joueur > je joue mot au milieu");
        return new MotPositionne("mot", 7, 7) ;
    }
}
