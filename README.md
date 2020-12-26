# Middleware-Uno

Projet de **Middleware** 2020.
Le but de ce projet est de créer une application synchronisée avec client/serveur **RMI**.
Nous avons choisit de faire un jeu de UNO. Nous allons donc vous détailler les règles que nous utiliserons pour notre UNO.

Le jeu se joue à 4 joueurs.

## Pré-requis / erreurs

**Pour exécuter le projet vous devez vous assurer d'utiliser java 11.  
Pour le compiler et créer les jars éxécutables vous devez également vous assurer que maven utilise java 11.**

--------------------------------------------------------------------------------

Pour connaître la version de java utilisée par maven :

<code>mvn -version</code>

--------------------------------------------------------------------------------

Par défaut le serveur utilise le port 1099, si ce port est déjà occupé, vous pouvez le modifier dans le code de la classe Serveur/src/main/java/com/rmi/app/App.java, vous pouvez également kill le processus utilisant le port mentionné. Pour cela, sous linux :

<code>lsof -i:1099</code>

## Compilation / exécution

Pour compiler le projet et créer un jar exécutable avec maven :

<code>mvn compile</code>

<code>mvn clean package</code>

Le jar exécutable sera dans le dossier **target**.

--------------------------------------------------------------------------------

Pour lancer le jar exécutable :

<code>java -jar JarName.jar</code>

Si vous avez besoin de specifier la version de java, exemple sous windows avec le jar du client:

<code>java -jar -Djava.library.path="C:\Program Files\Java\jdk-11.0.9\bin" Client-1.0-SNAPSHOT-jar-with-dependencies.jar</code>


## Constitution du jeu

108 cartes réparties en :  

- 19 cartes numérotés de 0 à 9 par couleur (bleues, vertes, rouges et jaunes). Deux exemplaires par couleur pour les cartes de 1 à 9, un seul exemplaire du 0.
- 2 cartes +2 par couleur
- 2 cartes Sens par couleur
- 2 cartes Interdit par couleur
- 4 cartes Couleur (changement de couleur) (on représentera la couleur de ces carte par noir)
- 4 cartes +4 (on représentera la couleur de ces carte par noir)

### Conditions de fin du jeu

Le jeu se termine lorsqu'un joueur n'a plus de cartes dans sa main.

## Mise en place du jeu

Le jeu est mélangé.
Tout les joueurs reçoivent 7 cartes.
Les autres cartes forment la pioche.
La carte du dessus est retournée.
Si cette carte est un symbole alors le jeu retire une carte jusqu'à tomber sur un chiffre.

Le premier joueur à être entré dans le jeu démarre la partie.

### Déroulement d'un tour

Si il reste moins de 10 cartes dans la pioche, le talon est alors mélangé, vidé et placé dans la pioche.

**Certains effets de carte s'appliquent au début du tour d'un nouveau joueur, comme un +4, par exemple. Dans ce cas là, le joueur reçoit les cartes du paquet, elle sont donc retirées de ce dernier, et son tour est automatiquement passé.
Il en va de même pour les effets des cartes Sens, ou bien Interdit.**

Le joueur dont c'est le tour de jouer joue une carte de même couleur, de même numéro ou de même symbole que le talon. Il peut également jouer une carte Couleur ou bien +4. Une fois que la carte est jouée, le joueur passe automatiquement son tour.  
Si le joueur ne peut pas jouer de cartes alors il doit piocher dans la pioche, la carte est donc retirée du paquet.  

Si le joueur pioche une carte, il peut, si les règles ci-dessus sont respectées, jouer la carte et aucune autre, sinon le joueur passe son tour.  
Le joueur ne peut jouer qu'une carte à la fois.

### Explication des cartes Actions

- Carte +2 (Rouge, Vert, Jaune, Bleu): lorsqu'un joueur joue cette carte, le joueur suivant pioche deux cartes et doit passer son tour. Cette carte ne peut être jouée que sur une carte de même couleur, ou sur une autre carte de symbole +2.

- Carte Sens (Rouge, Vert, Jaune, Bleu): lorsqu'un joueur joue cette carte, le sens du jeu change. Cette carte ne peut être jouée que sur une carte de même couleur, ou sur une autre carte de symbole Sens.

- Carte Interdit (Rouge, Vert, Jaune, Bleu): lorsqu'un joueur joue cette carte, le joueur suivant passe automatiquement son tour, il ne peut donc jouer aucune carte. Cette carte ne peut être jouée que sur une carte de même couleur, ou sur une autre carte de symbole Sens.

- Carte +4 (Noir): lorsqu'un joueur joue cette carte, le joueur suivant pioche quatre cartes et doit passer son tour. Cette carte peut être jouée n'importe quand.

- Carte Couleur (Noir): lorsqu'un joueur joue cette carte, il choisit la nouvelle couleur actuelle du jeu, et passe son tour. Cette carte peut être jouée n'importe quand.

## Partie Synchronisation

1- Lorsque qu'un joueur se connecte au serveur il décide de créer un lobby ou d'en rejoindre un déjà existant. Cela se passe dans la classe Serveur/src/main/java/com/rmi/server/RMIServer. Un tableau temporaire est créé pour chaque lobby et la méthode pour rejoindre ou créer un lobby est synchronized afin qu'un seul client à la fois puisse interargir avec le dit tableau. Lorsque qu'un lobby atteint 4 joueurs ce dernier est retiré de la liste des lobbys en attente. Cette méthode assure que la gestion du tableau est Thread Safe grace aux verrous imposés par le mot clé synchronized.

2- Le uno associé à un lobby étant créé avant que le dernier des quatre client reçoive le joueur qui lui est associé, un thread est créé côté client dans la méthode init() de la classe Client/src/main/java/com/rmi/controller/JeuController. Ce dernier va attendre que le client reçoive son joueur avant d'envoyer au uno commun un signal lui indiquant qu'il est prêt (méthode joueurPret de la classe uno). Cette méthode est synchronized pour s'assurer que l'ajout dans le tableau des joueurs prêt est thread safe. Côté serveur un thread vas attendre que tous les joueurs soient prêt (méthode tousPret, de la classe uno) avant d'initialiser une partie, c'est à dire attribuer les cartes à chaque joueurs etc. Cela se passe dans le constructeur de la classe uno.

3- Une fois que le jeu est intialisé, le Uno va créer côté serveur un thread par client, ces threads vont se synchroniser sur l'objet uno et tant que la partie n'est pas terminée, appeler la méthode getCourant de la classe uno avec le joueur associé au thread. Cette méthode est synchronisée sur l'objet uno également. Dans cette méthode, si le joueur est le joueur courant, alors il va indiquer au client que c'est son tour de jouer, à l'aide de la méthode joueurCourant de la classe Joueur, et attendre que ce dernier aie joué avant d'envoyer les informations à tous les clients puis de réveiller un des threads en attente sur l'objet. Si ce n'est pas le tour du joueur, le thread va réveiller un autre thread puis attendre qu'on le réveille.

4- Lorsqu'une partie est terminée, alors le uno vas créer un thread dans la méthode CarteJouer() de la classe uno qui va indiquer à tous les clients que la partie est terminée. Ce thread vas attendre pendant 3 minutes que les joueurs indiquent au serveur qu'ils sont prêt à redémarrer une partie. Si au bout de trois minutes les joueurs ne sont pas prêt, la partie est terminée. Le système pour indiquer qu'un joueur est prêt est le même que celui du point 1.

## Présentation visuelle

![Alt text](<visuel/connect.png>)
![Alt text](<visuel/chooseLobby.png>)
![Alt text](<visuel/selectLobby.png>)
![Alt text](<visuel/jeu.png>)

