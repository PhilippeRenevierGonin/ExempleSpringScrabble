package prg.exemple.demoscrabble.webcontroller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import prg.exemple.demoscrabble.data.EtatDuJeu;
import prg.exemple.demoscrabble.data.Identification;
import prg.exemple.demoscrabble.data.MotPositionne;
import prg.exemple.demoscrabble.moteur.Moteur;


import java.io.IOException;
import java.nio.charset.Charset;


@SpringBootTest(args={"val1","val2","val3"})
public class TestsAvecDesAppelsExterieurTest {

    @MockBean
    Moteur moteur;

    @Autowired
    MoteurWebControlleur webControlleurTeste;

    MockWebServer mockWebServer; // pour simuler les appels rest vers l'extérieur

    @Autowired
    private ObjectMapper objectMapper;


    // data
    Identification id ;
    EtatDuJeu etat;

    // oracle
    MotPositionne [] reponse = new MotPositionne[2];
    String [] reponseRetournee = new String[reponse.length];

    @BeforeEach
    void setUp()throws Exception {


        mockWebServer = new MockWebServer();
        mockWebServer.start();

        id = new Identification("Utilisateur factice", "http://"+mockWebServer.getHostName()+":"+mockWebServer.getPort());

        etat = new EtatDuJeu();
        reponse[0] = new MotPositionne("toto", 7,7);
        reponseRetournee[0] = objectMapper.writeValueAsString(reponse[0]);

        reponse[1] = new MotPositionne("AutreMot", 7,7);
        reponseRetournee[1] = objectMapper.writeValueAsString(reponse[1]);
    }

    @AfterEach
    void tearDown() throws IOException {
        mockWebServer.close();
    }

    @Test
    void demanderAuJoueurDeJoueurTest() throws InterruptedException {
        // pour que le controlleur ait un joueur...
        webControlleurTeste.getValue(id);
        Mockito.verify(moteur, Mockito.times(1)).lancerPartie();


        mockWebServer.enqueue(new MockResponse().setResponseCode(200)
                .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .setBody(reponseRetournee[0]) );


        Assertions.assertEquals(reponse[0], webControlleurTeste.demanderAuJoueurDeJoueur(etat));

        RecordedRequest recordedRequest = mockWebServer.takeRequest();

        Assertions.assertEquals("POST", recordedRequest.getMethod());
        Assertions.assertEquals("/jouer", recordedRequest.getPath());
    }


    @Test
    void appelsALaSuite_demanderAuJoueurDeJoueurTest2() throws InterruptedException, JsonProcessingException {
        // pour que le controlleur ait un joueur...
        webControlleurTeste.getValue(id);
        Mockito.verify(moteur, Mockito.times(1)).lancerPartie();

        // 1er requete
        mockWebServer.enqueue(new MockResponse().setResponseCode(200)
                .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .setBody(reponseRetournee[0]) );

        // 2e requette
        mockWebServer.enqueue(new MockResponse().setResponseCode(200)
                .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .setBody(reponseRetournee[1]) );

        // 1er appel
        Assertions.assertEquals(reponse[0], webControlleurTeste.demanderAuJoueurDeJoueur(etat));
        String etat1 = objectMapper.writeValueAsString(etat);
        etat.addMotPlace(reponse[0]);
        // 2e appel
        Assertions.assertEquals(reponse[1], webControlleurTeste.demanderAuJoueurDeJoueur(etat));
        String etat2 = objectMapper.writeValueAsString(etat);


        //  check du 1er appel
        RecordedRequest recordedRequest = mockWebServer.takeRequest();
        Assertions.assertEquals("POST", recordedRequest.getMethod());
        Assertions.assertEquals("/jouer", recordedRequest.getPath());
        // on peut aussi vérifier le corps recordedRequest.getBody().readString(Charset.defaultCharset()) donne le contenu, ici du JSON
        Assertions.assertEquals(etat1, recordedRequest.getBody().readString(Charset.defaultCharset()));

        //  check du 2e appel
        recordedRequest = mockWebServer.takeRequest();
        Assertions.assertEquals("POST", recordedRequest.getMethod());
        Assertions.assertEquals("/jouer", recordedRequest.getPath());
        Assertions.assertEquals(etat2, recordedRequest.getBody().readString(Charset.defaultCharset()));

    }


}
