package prg.exemple.demoscrabble;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import prg.exemple.demoscrabble.data.EtatDuJeu;
import prg.exemple.demoscrabble.data.Identification;
import prg.exemple.demoscrabble.data.MotPositionne;
import prg.exemple.demoscrabble.moteur.Moteur;
import prg.exemple.demoscrabble.webcontroller.MoteurWebControlleur;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

// @RestClientTest(MoteurWebControlleur.class)
@SpringBootTest
public class MoteurWebControleurTest {

    @MockBean
    Moteur moteur;

    @Autowired
    MoteurWebControlleur webControlleurTesté;

    // @Autowired
    // private MockRestServiceServer server;   // uniquement avec restTemplate, pas avec web client...
    // piste = MockWebServer https://www.baeldung.com/spring-mocking-webclient c'est un truc extérieur...
    // idem https://blog.mimacom.com/spring-webclient-testing/
        /* java.lang.IllegalStateException: Unable to use auto-configured MockRestServiceServer since MockServerRestTemplateCustomizer has not been bound to a RestTemplate
        at prg.exemple.demoscrabble.MoteurWebControleurTest.demanderAuJoueurDeJoueurTest(MoteurWebControleurTest.java:67)*/


    MockWebServer mockWebServer; // pour simuler les appels rest vers l'extérieur

    @Autowired
    private ObjectMapper objectMapper;


    // data
    Identification id ;
    EtatDuJeu etat;

    // oracle
    MotPositionne réponse;
    String réponseRetournée;

    @BeforeEach
    void setUp()throws Exception {


        mockWebServer = new MockWebServer();
        mockWebServer.start();

        id = new Identification();
        id.setUrl(String.format("http://localhost:%s", mockWebServer.getPort()));

        etat = new EtatDuJeu();
        réponse = new MotPositionne("toto", 7,7);
        réponseRetournée = objectMapper.writeValueAsString(réponse);
    }

    @AfterEach
    void tearDown() throws IOException {
        mockWebServer.close();
    }

    @Disabled("test en simulant des services distants à mettre à jour")
    @Test
    void demanderAuJoueurDeJoueurTest() throws InterruptedException {
        // pour que le controlleur ait un joueur...
        webControlleurTesté.getValue(id);
        verify(moteur, times(1)).lancerPartie();


        mockWebServer.enqueue(new MockResponse().setResponseCode(200)
                .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .setBody(réponseRetournée) );

        // mock du template
        // server.expect(requestTo(id.getUrl()+"jouer")).andExpect(method(HttpMethod.POST)).andRespond(withSuccess(réponseRetournée, MediaType.APPLICATION_JSON));


        assertEquals(réponse, webControlleurTesté.demanderAuJoueurDeJoueur(etat));

        RecordedRequest recordedRequest = mockWebServer.takeRequest();

        assertEquals("POST", recordedRequest.getMethod());
        assertEquals("/jouer", recordedRequest.getPath());
    }


}
