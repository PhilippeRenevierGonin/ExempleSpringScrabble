package prg.exemple.demoscrabble;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class MoteurWebControlleur {
    int value = 0;

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        // Do any additional configuration here
        return builder.build();
    }
    @Autowired
    private RestTemplate restTemplate;


    @Autowired
    Moteur moteur;

    @GetMapping("/connexion/")
    public boolean getValue() {
        System.out.println("Moteur > connexion acceptée");
        moteur.lancerPartie();
        return true;
    }

    public String demanderAuJoueurDeJoueur() {
        return restTemplate.getForObject("http://localhost:8081/jouer", String.class);
    }
}