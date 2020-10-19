package prg.exemple.demoscrabble.joueur;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class JoueurApplication {

    public static void main(String[] args) {
        SpringApplication.run(JoueurApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Bean
    public CommandLineRunner unClient(RestTemplate restTemplate, @Autowired Joueur joueur) {
        return args -> {
            /// retrieving the value
            Boolean val = restTemplate.postForObject("http://localhost:8080/connexion/", joueur.getId(), Boolean.class);
            System.out.println("client > la connexion est-elle acceptée ? "+val);
        };
    }
}