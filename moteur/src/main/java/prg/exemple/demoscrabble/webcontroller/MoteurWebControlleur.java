package prg.exemple.demoscrabble.webcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import prg.exemple.demoscrabble.data.Identification;
import prg.exemple.demoscrabble.data.MotPositionne;
import prg.exemple.demoscrabble.data.EtatDuJeu;
import prg.exemple.demoscrabble.moteur.Moteur;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.BodyInserters.fromMultipartData;

@RestController
public class MoteurWebControlleur {
    Identification joueurId;


    @Autowired
    Moteur moteur;


    /*
    @Bean
    public WebClient restTemplate(WebClient.Builder builder) {
        // Do any additional configuration here
        return builder.baseUrl("http://localhost:8081").build();   //@TODO URL EN DUR
    }
    @Autowired

     */
    private WebClient webClient;


    // juste pour le MockMvcTest, sans param
    @PostMapping("/essai/")
    public boolean getValue() {
        return true;
    }


    @PostMapping("/connexion/")
    public boolean getValue(@RequestBody Identification joueurId) {
        System.out.println("Moteur > connexion acceptée de "+joueurId.getNom());
        this.joueurId = joueurId;
        webClient = WebClient.create(joueurId.getUrl());
        return moteur.lancerPartie();
    }

    public MotPositionne demanderAuJoueurDeJoueur(EtatDuJeu p) {
        MotPositionne resultat = new MotPositionne();
        if (joueurId != null) {
            // resultat = webClient.postForObject(joueurId.getUrl()+"/jouer", p, MotPositionne.class); // le "/" de /jouer par sécurité
            resultat = webClient.post().uri("/jouer").contentType(MediaType.APPLICATION_JSON).body(Mono.just(p),EtatDuJeu.class).accept(MediaType.APPLICATION_JSON).retrieve().bodyToMono(MotPositionne.class).block(); // block pour être synchrone
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
        // webClient.exchange(joueurId.getUrl()+"/finir", HttpMethod.POST, null, Void.class);
        System.out.println(">> on envoie finir");

        /*
        String perceval = webClient.post().uri("/plein").retrieve().bodyToMono(String.class).block();
        System.out.println(">> recu : "+perceval);
        */

        webClient.post().uri("/finir").contentType(MediaType.APPLICATION_JSON).body(Mono.just(true),Boolean.class).retrieve().bodyToMono(Void.class).block();


        System.out.println(">> finir a été envoyé");

    }
}