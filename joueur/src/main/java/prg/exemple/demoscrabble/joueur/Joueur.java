package prg.exemple.demoscrabble.joueur;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import prg.exemple.demoscrabble.data.Identification;

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

    public String jouer() {
        return "mot";
    }
}
