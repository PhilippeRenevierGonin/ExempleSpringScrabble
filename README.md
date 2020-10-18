# ExempleSpringScrabble
exemple pour illustrer Spring et les Web Service dans Spring

## étape1 : juste un web service
Initialisation du projet avec un pom
Déclaration d'un Service Web


## étape2 : juste un web service
un client utilise le serveur
La structure du projet est modifiée (pom multimodule)
La partie client est créée
Il faut configurer le port de l'application Spring

## étape3 : les deux sont serveurs et clients
Client devient joueur, il s'identifie auprès de Moteur
Serveur devient Moteur, il accepte un joueur puis lance la partie
Moteur demande au joueur de jouer
(les adresses sont codées en dur)

## étape4 : échange d'objet (JSON est masquée)
Ajout de paramètre, échange d'objet 
Joueur -> Moteur
 * Le joueur s’identifie (nom, url)
 
Moteur -> Joueur
 * Plateau : état du jeu 
 * ~~Lettres : listes des lettres pour composer le mots~~
 
Joueur -> Moteur
 * Coup : le mot jouer
 
 
 ## étape5 : échange d'objet (JSON est masquée)
branchement sur travis-ci