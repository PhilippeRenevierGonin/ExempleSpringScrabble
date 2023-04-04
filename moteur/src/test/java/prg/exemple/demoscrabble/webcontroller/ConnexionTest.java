package prg.exemple.demoscrabble.webcontroller;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import prg.exemple.demoscrabble.data.Identification;
import prg.exemple.demoscrabble.moteur.Moteur;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class ConnexionTest {


    @MockBean
    Moteur moteur; // le spy

    @SpyBean
    MoteurWebControlleur webControlleur;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void initTest() {
    }

    @Test
    public void shouldReturnTrue() throws Exception {
        this.mockMvc.perform(post("/essai/")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("true")));
    }

    @Test
    public void cheminConnexion() throws Exception {
        Identification id = new Identification();
        String paramConnexion = objectMapper.writeValueAsString(id);

        Object synchro = new Object();

        // pour synchro test et code
        when(moteur.lancerPartie()).thenReturn(true);



        this.mockMvc.perform(post("/connexion/").contentType(MediaType.APPLICATION_JSON)
                .content(paramConnexion)).andExpect(status().isOk())
                .andExpect(content().string(containsString("true")));



        verify(webControlleur, times(1)).getValue(id);
        verify(moteur, times(1)).lancerPartie();


    }



}
