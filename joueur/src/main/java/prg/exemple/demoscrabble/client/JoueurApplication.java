package prg.exemple.demoscrabble.client;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import prg.exemple.demoscrabble.data.Identification;

import java.net.InetAddress;
import java.util.Arrays;

@SpringBootApplication
public class JoueurApplication {

    public static void main(String[] args) {
        // les traces sont là juste pour montrer le déroulement et le lancement
        System.out.println("args = "+args.length+" "+Arrays.toString(args));
        SpringApplication.run(JoueurApplication.class, args);
    }

        @Bean public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }


    @Bean
    public CommandLineRunner unClient(RestTemplate restTemplate) {
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
                Identification monId = new Identification("Michel", "http://localhost:8081/");
                Boolean val = restTemplate.postForObject("http://localhost:8080/connexion/", monId, Boolean.class);
                // les traces sont là juste pour montrer le déroulement et le lancement
                System.out.println("Joueur > état de la connexion : "+val);
            }
        };
    }
}