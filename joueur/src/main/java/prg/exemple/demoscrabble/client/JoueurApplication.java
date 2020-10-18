package prg.exemple.demoscrabble.client;

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

        @Bean public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }


    @Bean
    public CommandLineRunner unClient(RestTemplate restTemplate) {
        return args -> {
            /// connexion
            Boolean val = restTemplate.getForObject("http://localhost:8080/connexion/",Boolean.class);
            System.out.println("Joueur > état de la connexion : "+val);
        };
    }
}