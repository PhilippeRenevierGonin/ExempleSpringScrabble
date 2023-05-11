package prg.exemple.demoscrabble.moteur;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import prg.exemple.demoscrabble.data.EtatDuJeu;
import prg.exemple.demoscrabble.data.MotPositionne;
import prg.exemple.demoscrabble.webcontroller.MoteurWebControlleur;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
class MoteurTest {

    @MockBean
    MoteurWebControlleur ctrl;

    @SpyBean
    Moteur moteur;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void runTest() {
        MotPositionne mot = new MotPositionne();
        EtatDuJeu etat = new EtatDuJeu();
        EtatDuJeu etat1 = new EtatDuJeu();
        etat1.addMotPlace(mot);
        Mockito.when(ctrl.demanderAuJoueurDeJoueur(any())).thenReturn(mot);

        moteur.etatDuJeu = new EtatDuJeu();
        moteur.run();

        verify(ctrl, times(2)).demanderAuJoueurDeJoueur(any());
        verify(ctrl, times(1)).envoyerFin();

    }

}