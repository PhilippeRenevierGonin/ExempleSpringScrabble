package prg.exemple.demoscrabble.joueur;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
public class Joueur {

    public String jouer() {
        return "mot";
    }
}
