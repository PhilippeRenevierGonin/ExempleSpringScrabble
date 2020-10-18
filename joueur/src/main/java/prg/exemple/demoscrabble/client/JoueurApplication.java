package prg.exemple.demoscrabble.client;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import prg.exemple.demoscrabble.data.Identification;

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
            Identification monId = new Identification("Michel", "http://localhost:8081/");
            Boolean val = restTemplate.postForObject("http://localhost:8080/connexion/", monId, Boolean.class);
            System.out.println("Joueur > état de la connexion : "+val);
        };
    }
}