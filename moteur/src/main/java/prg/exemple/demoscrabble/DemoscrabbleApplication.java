package prg.exemple.demoscrabble;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
/* import org.springframework.core.env.Environment; */
import prg.exemple.demoscrabble.moteur.Moteur;

@SpringBootApplication
public class DemoscrabbleApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoscrabbleApplication.class, args);
	}

	/*
	// exemple pour récupérer des variables d'environnement
	@Autowired
	Environment environment;
	*/


	@Bean
	public CommandLineRunner aGame(@Autowired Moteur moteur) {
		return args -> {
			System.out.println("************************** aGame **************************************");
			/*
				// exemple pour récupérer des variables d'environnement
			String port = environment.getProperty("local.server.port");
			System.out.println("port = "+port);
			System.out.println(environment.getProperty("JAVA_HOME"));
			 */

			// pour faire la différence entre un lancement via les tests et un lancement par mvn exec:java@id
			if (args.length >0) {
				moteur.setExitOnFinish(true);

			}
		};
	}

}
