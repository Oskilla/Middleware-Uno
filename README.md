pour compiler le serveurou le client placez-vous dans le chemin suivant src/main/java/com/rmi et effectuez la commande suivante :
<code>javac -cp com.rmi.* \*\/*.java</code>
pour éxécuter le serveur ou le client retournez dans le dossier src/main/java et faites:
<code>java com.rmi.app.App</code>
pour trouver le processus utilsant le port 1099
<code>lsof -i:1099</code>
pour le tuer
<code>kill -9 NUMPROCESS</code>

# Middleware-Uno

Projet de **Middleware** 2020.
Le but de ce projet est de créer une application synchronisée avec client/serveur **RMI**.
Nous avons choisit de faire un jeu de UNO. Nous allons donc vous détailler les règles que nous utiliserons pour notre UNO.

Le jeu se joue de 2 à 10 joueurs.

## Constitution du jeu

108 cartes  réparties en :

19 cartes numérotés de 0 à 9 par couleur (bleues, vertes, rouges et jaunes)
2 cartes +2 de chaque couleur
2 cartes Sens par couleur
2 cartes Interdit par couleur
1 carte Couleur par couleur (on représentera la couleur de cette carte par noir)
1 carte +4 par couleur (on représentera la couleur de cette carte par noir)

### Conditions de fin du jeu

Un joueur a terminé de jouer dans la manche courante si il a épuisé toute les cartes qui composent sa main.

Le jeu se termine lorsqu'il ne reste plus qu'un seul joueur en lice.

### Mise en place du jeu

Le jeu est mélangé.
Tout les joueurs reçoivent 7 cartes.
Les autres cartes forment la pioche.
La carte du dessus est retournée.
*Si cette carte est un symbole alors le joueur qui commencera la partie définira la couleur en jouant une carte.*

Un joueur est choisis aléatoirement pour commencer.

### Déroulement d'un tour

Si c'est le premier tour, le joueur choisit aléatoirement commence en posant une carte arborant une couleur (autre que noire), cette couleur définira la couleur de jeu pour le prochain joueur.

Si il ne reste plus aucune carte dans la pioche, le talon est alors mélangé, vidé et placé dans la pioche.

**Certains effets de carte s'applique au début du tour d'un nouveau joueur, comme un +4, par exemple, dans ce cas là le joueur reçoit les cartes du paquet, elle sont donc retirées de ce dernier, et son tour est automatiquement passé.
Il en vas de même pour les effets des cartes Sens, ou bien Interdit.**

Le joueur dont c'est le tour de jouer, joue une carte de même couleur, de même numéro ou de même symbole que le talon, il peut également jouer une carte Couleur ou bien +4. Une fois que la carte est jouée, le joueur passe automatiquement son tour.
*Si le joueur ne peut pas jouer de cartes alors il doit piocher dans la pioche, la carte est donc retirée du paquet.*

**Si le joueur pioche une carte, il peut, si les règles ci-dessus sont respectées, jouer la carte et aucune autre, sinon le joueur passe son tour**
**Le joueur ne peut jouer qu'une carte à la fois**

#### Explication des cartes Actions

- Carte +2, couleurs (Rouge,Vert,Jaune,Bleu): lorsqu'un joueur joue cette carte, le joueur suivant pioche deux cartes et doit passer son tour. Cette carte ne peut être jouée que sur une carte de même couleur, ou sur une autre carte de symbole +2.

- Carte Sens, couleurs (Rouge,Vert,Jaune,Bleu): lorsqu'un joueur joue cette carte, le sens du jeu change. Cette carte ne peut être jouée que sur une carte de même couleur, ou sur une autre carte de symbole Sens.

- Carte Interdit, couleurs (Rouge,Vert,Jaune,Bleu): lorsqu'un joueur joue cette carte, le joueur suivant passe automatiquement son tour, il ne peut donc jouer aucune carte. Cette carte ne peut être jouée que sur une carte de même couleur, ou sur une autre carte de symbole Sens.

- Carte +4, couleur (Noir): lorsqu'un joueur joue cette carte, le joueur suivant pioche quatre cartes et doit passer son tour. Cette carte peut être jouée n'importe quand.

- Carte Couleur, couleur (Noir): lorsqu'un joueur joue cette carte, il choisit automatiquement la couleur actuelle du jeu, et passe son tour. Cette carte peut être jouée n'importe quand.
