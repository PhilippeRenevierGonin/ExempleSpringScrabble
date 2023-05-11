package prg.exemple.demoscrabble;


import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import prg.exemple.demoscrabble.data.EtatDuJeu;
import prg.exemple.demoscrabble.data.Identification;
import prg.exemple.demoscrabble.data.MotPositionne;
import prg.exemple.demoscrabble.moteur.Moteur;
import prg.exemple.demoscrabble.webcontroller.MoteurWebControlleur;


import java.io.IOException;
import java.nio.charset.Charset;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class SenarioExterieurFacticeTest {


    @Autowired
    Moteur moteurReal; // le spy
    Moteur moteur; // le spy

    @SpyBean
    MoteurWebControlleur webControlleur;

    @Autowired
    private MockMvc mockMvc;

    MockWebServer mockWebServer; // pour simuler les appels rest vers l'extérieur

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void initTest() throws IOException {
        moteur = spy(moteurReal);
        ReflectionTestUtils.setField(webControlleur, "moteur", moteur);
        moteur.setExitOnFinish(false);


        mockWebServer = new MockWebServer();
        mockWebServer.start();
    }

    @AfterEach
    void finTest() throws IOException {
        mockWebServer.close();
    }

    @Test
    public void unePartie() throws Exception {
        Identification id = new Identification("Utilisateur factice", "http://"+mockWebServer.getHostName()+":"+mockWebServer.getPort());
        String paramConnexion = objectMapper.writeValueAsString(id);


        MotPositionne [] reponse = new MotPositionne[2];
        String [] reponseRetournee = new String[reponse.length];
        reponse[0] = new MotPositionne("toto", 7,7);
        reponseRetournee[0] = objectMapper.writeValueAsString(reponse[0]);
        mockWebServer.enqueue(new MockResponse().setResponseCode(200)
                .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .setBody(reponseRetournee[0]) );

        reponse[1] = new MotPositionne("AutreMot", 7,7);
        reponseRetournee[1] = objectMapper.writeValueAsString(reponse[1]);
        mockWebServer.enqueue(new MockResponse().setResponseCode(200)
                .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .setBody(reponseRetournee[1]) );

        // fini
        mockWebServer.enqueue(new MockResponse().setResponseCode(200));

        EtatDuJeu etat = new EtatDuJeu();
        etat.ajouterLettres('a','b','c','d','m','o','t');
        String etat1 = objectMapper.writeValueAsString(etat);
        etat.addMotPlace(reponse[0]);
        etat.ajouterLettres('a','b','c','d','m','o','t');
        String etat2 = objectMapper.writeValueAsString(etat);

        Object synchro = new Object();

        // pour synchro test et code
        doAnswer(invocationOnMock -> {
            invocationOnMock.callRealMethod();
            synchronized (synchro) {
                synchro.notify();
            }
            return null;
        }).when(webControlleur).envoyerFin();

        // pour ne pas faire d'appel exterieur, sinon, voir


        InOrder ordreAppel = inOrder(webControlleur, moteur);

        this.mockMvc.perform(post("/connexion/").contentType(MediaType.APPLICATION_JSON)
                .content(paramConnexion)).andExpect(status().isOk())
                .andExpect(content().string(containsString("true")));

        // TimeUnit.MILLISECONDS.sleep(500); // le temps pour travis de lancer le thread
        synchronized (synchro) {
            synchro.wait();
        }



        ordreAppel.verify(webControlleur, times(1)).nouveauJoueur(id);
        ordreAppel.verify(moteur, times(1)).lancerPartie();
        ordreAppel.verify(moteur, times(1)).run();
        ordreAppel.verify(webControlleur, times(1)).demanderAuJoueurDeJoueur(any());
        ordreAppel.verify(webControlleur, times(1)).getNomJoueur();
        ordreAppel.verify(webControlleur, times(1)).demanderAuJoueurDeJoueur(any());
        ordreAppel.verify(webControlleur, times(1)).getNomJoueur();
        ordreAppel.verify(webControlleur, times(1)).envoyerFin();
        ordreAppel.verify(moteur, times(1)).getExitOnFinish();


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

        //  check du 3e appel
        recordedRequest = mockWebServer.takeRequest();
        Assertions.assertEquals("POST", recordedRequest.getMethod());
        Assertions.assertEquals("/finir", recordedRequest.getPath());
        Assertions.assertEquals("true", recordedRequest.getBody().readString(Charset.defaultCharset()));

    }



}
