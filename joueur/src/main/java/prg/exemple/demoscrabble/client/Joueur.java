package prg.exemple.demoscrabble.client;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
public class Joueur {

    public String jouer() {
        System.out.println("Joueur > je joue mot");
        return "mot";
    }
}
