package prg.exemple.demoscrabble;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;
import prg.exemple.demoscrabble.data.Identification;
import prg.exemple.demoscrabble.moteur.Moteur;

import java.io.IOException;
import java.util.Arrays;

@SpringBootApplication
public class DemoscrabbleApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoscrabbleApplication.class, args);
	}


	@Bean
	public CommandLineRunner aGame(@Autowired Moteur moteur) {
		return args -> {
			System.out.println("************************** aGame **************************************");
			System.out.println(Arrays.toString(args));
			System.out.println(args.length);

			// pour faire la différence entre un lancement via les tests et un lancement par mvn exec:java@id
			if (args.length >0) {
				moteur.setExitOnFinish(true);
			}
		};
	}

}
