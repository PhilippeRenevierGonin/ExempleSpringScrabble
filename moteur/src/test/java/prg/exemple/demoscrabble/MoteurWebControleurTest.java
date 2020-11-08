package prg.exemple.demoscrabble;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import prg.exemple.demoscrabble.data.EtatDuJeu;
import prg.exemple.demoscrabble.data.Identification;
import prg.exemple.demoscrabble.data.MotPositionne;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RestClientTest(MoteurWebControlleur.class)
public class MoteurWebControleurTest {

    @MockBean
    Moteur moteur;

    @Autowired
    MoteurWebControlleur webControlleurTesté;

    @Autowired
    private MockRestServiceServer server;

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
        id = new Identification();
        etat = new EtatDuJeu();
        réponse = new MotPositionne("toto", 7,7);
        réponseRetournée = objectMapper.writeValueAsString(réponse);


    }

    @Test
    void demanderAuJoueurDeJoueurTest() {
        // pour que le controlleur ait un joueur...
        webControlleurTesté.getValue(id);
        verify(moteur, times(1)).lancerPartie();

        // mock du template
        server.expect(requestTo(id.getUrl()+"jouer")).andExpect(method(HttpMethod.POST)).andRespond(withSuccess(réponseRetournée, MediaType.APPLICATION_JSON));

        assertEquals(réponse, webControlleurTesté.demanderAuJoueurDeJoueur(etat));
    }
}
