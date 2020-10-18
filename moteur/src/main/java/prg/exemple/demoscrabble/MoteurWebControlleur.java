package prg.exemple.demoscrabble;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import prg.exemple.demoscrabble.data.Identification;
import prg.exemple.demoscrabble.data.MotPositionne;

@RestController
public class MoteurWebControlleur {
    int value = 0;

    private Identification joueurId;

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        // Do any additional configuration here
        return builder.build();
    }
    @Autowired
    private RestTemplate restTemplate;


    @Autowired
    Moteur moteur;

    @PostMapping("/connexion/")
    public boolean getValue(@RequestBody Identification joueurId) {
        System.out.println("Moteur > connexion acceptée de "+joueurId.getNom());
        this.joueurId = joueurId;
        moteur.lancerPartie();
        return true;
    }

    public MotPositionne demanderAuJoueurDeJoueur() {
        MotPositionne resultat = new MotPositionne();
        if (joueurId != null) {
            resultat = restTemplate.postForObject(joueurId.getUrl()+"/jouer", moteur.getPlateau(), MotPositionne.class);
        }
        return resultat ;
    }

    public String getNomJoueur() {
        String resultat = "[NULL]";
        if (joueurId != null) {
            resultat = joueurId.getNom();
        }
        return resultat ;
    }

    public void envoyerFin() {
        restTemplate.exchange(joueurId.getUrl()+"/finir", HttpMethod.POST, null, Void.class);
    }
}