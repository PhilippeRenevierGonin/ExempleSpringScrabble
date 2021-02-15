package prg.exemple.demoscrabble;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import prg.exemple.demoscrabble.data.EtatDuJeu;
import prg.exemple.demoscrabble.data.Identification;
import prg.exemple.demoscrabble.data.MotPositionne;
import prg.exemple.demoscrabble.moteur.Moteur;
import prg.exemple.demoscrabble.webcontroller.MoteurWebControlleur;

import java.util.concurrent.TimeUnit;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class ConnexionTest {

    /*
    @SpyBean
    Moteur moteur;
    non utilisable ici car déjà créer dans DemoscrabbleApplication
     */
    @Autowired
    Moteur realMoteur;
    Moteur moteur; // le spy

    @Autowired
    MoteurWebControlleur webControlleur;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void initTest() {
        moteur = spy(realMoteur);
        // on réinjecte le spy
        ReflectionTestUtils.setField(webControlleur, "moteur", moteur);

    }

    @Test
    public void shouldReturnTrue() throws Exception {
        this.mockMvc.perform(post("/essai/")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("true")));
    }

    @Test
    public void shouldReturnTrue_theRealMethodWithParam() throws Exception {
        Identification id = new Identification();
        String paramConnexion = objectMapper.writeValueAsString(id);

        this.mockMvc.perform(post("/connexion/").contentType(MediaType.APPLICATION_JSON)
                .content(paramConnexion)).andExpect(status().isOk())
                .andExpect(content().string(containsString("true")));

        TimeUnit.MILLISECONDS.sleep(500); // le temps pour travis de lancer le thread

        verify(moteur, times(1)).lancerPartie();
        verify(moteur, times(1)).run();


        this.mockMvc.perform(post("/connexion/").contentType(MediaType.APPLICATION_JSON)
                .content(paramConnexion)).andExpect(status().isOk())
                .andExpect(content().string(containsString("false")));

        TimeUnit.MILLISECONDS.sleep(500);

        verify(moteur, times(2)).lancerPartie(); // compte cumulé
        verify(moteur, times(1)).run(); // la 2e fois, le thread n'est pas lancer
    }



}
