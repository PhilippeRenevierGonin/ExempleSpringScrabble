package prg.exemple.demoscrabble.client;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import prg.exemple.demoscrabble.data.Identification;
import reactor.core.publisher.Mono;

import java.net.InetAddress;
import java.util.Arrays;

@SpringBootApplication
public class JoueurApplication {

    public static void main(String[] args) {
        // les traces sont là juste pour montrer le déroulement et le lancement
        System.out.println("args = "+args.length+" "+Arrays.toString(args));
        SpringApplication.run(JoueurApplication.class, args);
    }

       /* @Bean public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    } */


    @Bean
    public CommandLineRunner unClient(/* RestTemplate restTemplate*/) {
        // les traces sont là juste pour montrer le déroulement et le lancement
        System.out.println("----------------- CommandLineRunner -----------------");
        return args -> {
            // les traces sont là juste pour montrer le déroulement et le lancement
            System.out.println("----------------- args = "+args.length+" "+Arrays.toString(args)+" -----------------");
            if ((args.length > 0) && (args[0].equals("autoconnect"))) {
                // les traces sont là juste pour montrer le déroulement et le lancement
                System.out.println("----------------- début de joueur -----------------");
                /// connexion
                String adresse =  "http://"+InetAddress.getLocalHost().getHostAddress();
                System.out.println("mon adresse = "+adresse);

                Identification monId = new Identification("Michel", "http://localhost:8081/");  //@TODO url en dur

                WebClient webClient = WebClient.create("http://localhost:8080/");  //@TODO url en dur

                Boolean val = webClient.post().uri("/connexion/").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).body(Mono.just(monId),Identification.class).retrieve().bodyToMono(Boolean.class).block();

                        // .postForObject("http://localhost:8080/connexion/", monId, Boolean.class);
                // les traces sont là juste pour montrer le déroulement et le lancement
                System.out.println("Joueur > état de la connexion : "+val);
            }
        };
    }
}