```
mvn spring-boot:run -Dspring-boot.run.jvmArguments='-Dserver.port=8081' -Dspring-boot.run.arguments='POUR_LES_TEST' -pl joueur
```

Les tests (les tests ne sont pas complets) : 
 - MoteurTest : test dans spring d'un composant (les autres composants sont des MockBean)
 - ConnexionTest : test de l'API de ce service
 - MoteurWebControlleurTest : test avec "mock" d'un service extérieur
 - TestsAvecDesAppelsExterieurTest : test avec "mock" d'un service extérieur et un MockBean
 - SenarioExterieurFacticeTest : même test que TestsAvecDesAppelsExterieurTest mais sans MockBean
 - MoteurWebControleurITCase : Test d'intégration, même test que SenarioExterieurFacticeTest mais qu'avec des objets et services réels