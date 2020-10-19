package prg.exemple.demoscrabble.moteur;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import prg.exemple.demoscrabble.data.Identification;

@RestController
public class MoteurWebController {
    @Autowired
    Moteur moteur;

    Identification joueur;

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        // Do any additional configuration here
        return builder.build();
    }
    @Autowired
    private RestTemplate restTemplate;

    @PostMapping("/connexion/")
    public boolean connecter(@RequestBody Identification j) {
        joueur = j;
        System.out.println("Moteur > connexion acceptée pour "+joueur);
        moteur.lancerPartie();
        return true;
    }

    public String demanderAuJoueurDeJoueur() {
        return restTemplate.getForObject("http://localhost:8081/jouer", String.class);
    }

    public String getNomJoueur() {
        String resultat = "#pas de joueur#";
        if (joueur != null) resultat = joueur.getNom();
        return resultat;
    }
}