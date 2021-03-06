package prg.exemple.demoscrabble.webcontroller;

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
import prg.exemple.demoscrabble.data.EtatDuJeu;
import prg.exemple.demoscrabble.moteur.Moteur;

@RestController
public class MoteurWebControlleur {
    Identification joueurId;

    @Autowired
    Moteur moteur;


    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        // Do any additional configuration here
        return builder.build();
    }
    @Autowired
    private RestTemplate restTemplate;


    // juste pour le MockMvcTest, sans param
    @PostMapping("/essai/")
    public boolean getValue() {
        return true;
    }


    @PostMapping("/connexion/")
    public boolean getValue(@RequestBody Identification joueurId) {
        System.out.println("Moteur > connexion acceptée de "+joueurId.getNom());
        this.joueurId = joueurId;
        return moteur.lancerPartie();
    }

    public MotPositionne demanderAuJoueurDeJoueur(EtatDuJeu p) {
        MotPositionne resultat = new MotPositionne();
        if (joueurId != null) {
            resultat = restTemplate.postForObject(joueurId.getUrl()+"/jouer", p, MotPositionne.class); // le "/" de /jouer par sécurité
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