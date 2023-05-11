package prg.exemple.demoscrabble.webcontroller;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import prg.exemple.demoscrabble.data.Identification;
import prg.exemple.demoscrabble.moteur.Moteur;

import java.io.IOException;
import java.nio.charset.Charset;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MoteurWebControlleurTest {

    @MockBean
    Moteur moteur;

    @Autowired
    MoteurWebControlleur webControlleurTeste;

    @Autowired
    private ObjectMapper objectMapper;

    Identification id ;

    MockWebServer mockWebServer; // pour simuler les appels rest vers l'extérieur



    @BeforeEach
    void setUp() {
        mockWebServer = new MockWebServer();
        try {
            mockWebServer.start();
        } catch (IOException e) {
            fail();
        }
        id = new Identification("Utilisateur factice", "http://"+mockWebServer.getHostName()+":"+mockWebServer.getPort());

        webControlleurTeste.nouveauJoueur(id);
    }

    @AfterEach
    void tearDown() {
        try {
            mockWebServer.close();
        } catch (IOException e) {
           fail();
        }
    }

    @Test
    void envoyerFin() {
        mockWebServer.enqueue(new MockResponse().setResponseCode(200));
        webControlleurTeste.envoyerFin();
        try {
            RecordedRequest requeteFaite = mockWebServer.takeRequest();
            assertEquals("POST", requeteFaite.getMethod());
            assertEquals("/finir", requeteFaite.getPath());
            assertEquals("true", requeteFaite.getBody().readString(Charset.defaultCharset()));
        } catch (InterruptedException e) {
            fail();
        }


    }
}