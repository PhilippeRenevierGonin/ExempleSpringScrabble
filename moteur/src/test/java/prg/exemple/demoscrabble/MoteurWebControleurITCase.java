package prg.exemple.demoscrabble;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;
import prg.exemple.demoscrabble.data.EtatDuJeu;
import prg.exemple.demoscrabble.data.Identification;
import prg.exemple.demoscrabble.moteur.Moteur;
import prg.exemple.demoscrabble.webcontroller.MoteurWebControlleur;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@SpringBootTest
@ExtendWith(SpringExtension.class)
public class MoteurWebControleurITCase {
    // data
    Identification id ;
    EtatDuJeu etat;

    @SpyBean
    MoteurWebControlleur webControlleur;

    @Autowired
    Moteur moteur;

    Moteur mSpy;

    Object synchro = new Object();

    @BeforeEach
    void setUp()throws Exception {
        id = new Identification("Joueur pour intégration", "http://127.0.0.1:8081/");
        etat = new EtatDuJeu();

        mSpy = Mockito.spy(moteur);
        ReflectionTestUtils.setField(webControlleur, "moteur", mSpy);
    }

    @Test
    void demanderAuJoueurDeJoueurTest() {

        doAnswer((invocation) -> {
            invocation.callRealMethod();
            synchronized (synchro) { synchro.notifyAll(); }
            return null;}).when(webControlleur).envoyerFin();

        // pour que le controlleur ait un joueur...
        // l'appel extérieur qui lance tout
        webControlleur.getValue(id);

        /*
        // some code are in a thread... solution ici de facilité...
        try {
            TimeUnit.MILLISECONDS.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        */

        synchronized (synchro) {
            try {
                System.err.println("on a attend la synchro, ce message ne devrait pas exister");
                synchro.wait();
                System.err.println("on a fini la synchro, ce message ne devrait pas exister");
            } catch (InterruptedException e) {
                fail("pas de synchro entre le code et le test");
            }

        }

        verify(mSpy, times(1)).lancerPartie();
        verify(webControlleur, times(2)).demanderAuJoueurDeJoueur(any());
        verify(webControlleur, times(1)).envoyerFin();


        EtatDuJeu plateau = mSpy.getPlateau();
        assertEquals(2, plateau.getListeDeMots().size());

        verify(mSpy, times(3)).getPlateau();
        // error to verify it is a good spy // verify(mSpy, times(16)).aMethod();

        // normalement, à la fin le client est éteint
        // changement restTemplate -> WebClient // assertThrows(org.springframework.web.client.ResourceAccessException.class, () -> mSpy.run());
        assertThrows(org.springframework.web.reactive.function.client.WebClientRequestException.class, () -> mSpy.run());

        // etc.

    }



    // un test est possible avec le lancement d'une  docker.
    @Test
    void demanderAuJoueurDeJoueurTest2foisDeSuite() {

        doAnswer((invocation) -> {
            invocation.callRealMethod(); // on appelle la vraie méthode
            synchronized (synchro) { synchro.notifyAll(); } // on notife le test
            return null;}).when(webControlleur).envoyerFin();

        // pour que le controlleur ait un joueur...

        webControlleur.getValue(id);
        webControlleur.getValue(id);

        /*
        // some code are in a thread... solution de facilité
        try {
            TimeUnit.MILLISECONDS.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        */
        synchronized (synchro) {
            try {
                System.err.println("on a attend la synchro, ce message ne devrait pas exister");
                synchro.wait();
                System.err.println("on a fini la synchro, ce message ne devrait pas exister");
            } catch (InterruptedException e) {
                fail("pas de synchro entre le code et le test");
            }

        }

        // 2 fois lancerPartie, mais une seule avec effet... comme c'est un sout, on ne peut pas vérfier la 2e fois si ce n'est que le reste ne change pas
        verify(mSpy, times(2)).lancerPartie();
        verify(webControlleur, times(2)).demanderAuJoueurDeJoueur(any());
        verify(webControlleur, times(1)).envoyerFin();


        EtatDuJeu plateau = mSpy.getPlateau();
        assertEquals(2, plateau.getListeDeMots().size());

        verify(mSpy, times(3)).getPlateau();
        // error to verify it is a good spy // verify(mSpy, times(16)).aMethod();

        // normalement, à la fin le client est éteint
        assertThrows(org.springframework.web.reactive.function.client.WebClientRequestException.class, () -> mSpy.run());

        // etc.

    }

/*
mvn clean install -DskipTests
mvn test
docker build joueur -t scrabble:joueur
cd moteur
docker run -d --name joueur_test  -e LANCEMENT="POUR_LES_TEST" -e PORT="8081" -p 8081:8081 scrabble:joueur
mvn failsafe:integration-test -Dit.test=prg.exemple.demoscrabble.MoteurWebControleurITCase#demanderAuJoueurDeJoueurTest
docker start joueur_test
mvn failsafe:integration-test -Dit.test=prg.exemple.demoscrabble.MoteurWebControleurITCase#demanderAuJoueurDeJoueurTest2foisDeSuite
*/

}
