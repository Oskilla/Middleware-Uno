"# Middleware-Uno" 
Projet de middleware 2020.
Le but de ce projet est de créer une application rmi. 
Nous avons choisit de faire un jeu de Uno. 

Le client est le Joueur;
Le serveur est le UNO;
le client appelera le serveur a chaque fois qu'il voudra prendre une carte ou en jouer une.
Le serveur lui retourne une carte aleatoire et l'enleve de son paquet.
Pour le premier tour de jeu, il en tire une au hasard et le met sur la table. Le serveur impose la couleur de la carte au joueur et si'l tombe sur une carte noir, il choisit aleatoirement la couleur.
On ne peut pas mettre plus d'une carte à la fois et le jour qui doit prendre un +4 ou +2 pioche ses cartes et passe son tour.
Si le joueur ne peut pas jouer car il n'a pas la bonne couleur. Il pioche une carte et s'il peut joué la carte pioche, il l'a joue.
  