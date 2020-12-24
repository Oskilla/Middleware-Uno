/**
 * Projet Middleware-Uno
 * Une implémentation du jeu de plateau Uno avec une architecture Client / Serveur à l'aide de RMI.
 * @authors Leveille Bastien, Lecomte Soline, Lode Gael & Perez Damien
 */

package com.rmi.entity;

import com.rmi.intf.CarteInterface;
import com.rmi.intf.ClientInterface;
import com.rmi.intf.JoueurInterface;
import com.rmi.intf.UnoInterface;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Collections;

import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;

/**
 * Classe representant un Uno, un Uno represente le jeu du meme nom
 * C est ici que les entitees Joueurs & Cartes vont interargir ensemble au fur et a mesure que le jeu se deroule
 */
public class Uno extends UnicastRemoteObject implements UnoInterface {
  // attribut representant la liste des joueurs composant cette partie de uno
  private List<JoueurInterface> joueurs = new ArrayList<JoueurInterface>();
  // attribut representant la liste des cartes composant le talon(les cartes jouees par les joueurs)
  private List<CarteInterface> talon = new ArrayList<CarteInterface>();
  // attribut representant la liste des cartes composant la pioche
  private List<CarteInterface> pioche = new ArrayList<CarteInterface>();
  // attribut representant toutes les couleurs presentes dans le jeu
  private final List<String> couleurs = new ArrayList<String>(Arrays.asList("Rouge","Bleu","Jaune","Vert","Noire"));
  // attribut representant tous les symboles presents dans le jeu
  private final List<String> symboles = new ArrayList<String>(Arrays.asList("+2","sens","interdit","+4","couleur"));
  // attribut permettant de recenser les joueurs qui souhaitent relancer une partie
  private List<String> idJoueursPret = new ArrayList<String>();
  // attribut representant la fin du jeu
  private Boolean GameOver = false;
  // attribut representant le joueur dont c est le tour de jouer
  private JoueurInterface courant;
  // attribue representant la couleur actuellement demandee
  private String couleurChoisie;
  // attribue representant le sens du jeu
  private boolean sens = false; // false pour horaire

  /**
  * Constructeur de la class Uno
  * cree un Uno avec le parametres ci-dessous
  * @param Listjoueurs, les joueurs qui vont participer au jeu
  */
  public Uno(List<JoueurInterface> Listjoueurs) throws RemoteException{
    // attribution des places dans le jeu
    for (int i=0;i<Listjoueurs.size();i++) {
      this.joueurs.add(Listjoueurs.get(i));
    }
    // creation des cartes composant le jeu
    for(int j=0;j<this.couleurs.size()-1;j++){
      // pour chaque couleur
      for(int z=0;z<10;z++){
        CarteNumero carte = new CarteNumero(this.couleurs.get(j),z);
        if(z != 0){
          // je creee deux cartes du meme numero sauf pour le numero 0
          CarteNumero carte2 = new CarteNumero(this.couleurs.get(j),z);
          this.pioche.add(carte2);
        }
        this.pioche.add(carte);
      }
      for (int m=0;m<3;m++){
        // je creee deux cartes actions de meme symbole par couleur
        CarteAction carteAction = new CarteAction(this.couleurs.get(j),this.symboles.get(m));
        CarteAction carteAction2 = new CarteAction(this.couleurs.get(j),this.symboles.get(m));
        this.pioche.add(carteAction);
        this.pioche.add(carteAction2);
      }
    }
    for(int h=0;h<4;h++){
      // je creee les 8 cartes joker
      CarteAction carteJoker = new CarteAction(this.couleurs.get(this.couleurs.size()-1),this.symboles.get(this.symboles.size()-2));
      CarteAction carteJoker2 = new CarteAction(this.couleurs.get(this.couleurs.size()-1),this.symboles.get(this.symboles.size()-1));
      this.pioche.add(carteJoker);
      this.pioche.add(carteJoker2);
    }
    // Thread qui vas attendre que tout les joueurs soient prets avant d initialiser la partie
    // cela est neccessaire car le uno est cree avant que le dernier client graphique ne recoive son joueur
    Thread t = new Thread(() -> {
        try {
          while(!this.tousPret()){Thread.sleep(10);}
          this.InitGame();
          Thread.currentThread().join();
        } catch (InterruptedException | IOException e) {
          e.printStackTrace();
        }
    });
    t.start();
  }

  /**
  * Methode initialisant le jeu, c est ici que les joueurs vont recevoir leurs cartes et que le talon va etre selectionne
  */
  private void InitGame() throws IOException {
    // on melange la pioche
    this.melangerList(this.pioche);
    for(JoueurInterface j : this.joueurs){
      for(int i=0;i<7;i++){
        // on attribue 7 cartes par joueurs en les retirant de la pioche
        j.piocher(this.pioche.remove(0));
      }
    }
    // on selectionne une carte numero comme premiere carte du talon
    while(this.pioche.get(0).getClassName().equals("CarteAction")){
      this.melangerList(this.pioche);
    }
    CarteInterface c = this.pioche.remove(0);
    this.couleurChoisie = c.getCouleur();
    this.talon.add(c);
    this.courant = this.joueurs.get(0);
    // ces Threads seront en concurrence sur l objet uno (this), cela permettra d indiquer au joueur dont c est le tour de jouer
    for(JoueurInterface joue : this.joueurs){
      Thread t = new Thread(() -> {
        try {
          // tant que la partie n est pas finie
          while (!this.GameOver){
            synchronized (this) {
              // si ce n est pas son tour le thread se met en wait
              this.getCourant(joue);
            }
          }
          Thread.currentThread().join();
        } catch (IOException | InterruptedException e) {
          e.printStackTrace();
        }
      });
      t.start();
    }
    // a la fin de l initialisation on envoie les donnees a tout les clients graphiques afin que le visuel soit a jour
    this.sendAll();
  }

  /**
  * Methode appliquant les effets des CarteAction de couleur autre que noire
  * @param j, le joueur possedant la carte
  * @param carte, la carte a jouer
  * @return true si le Joueur d apres doit sauter son tour ou pas (voir methode JouerCarte), false sinon
  */
  private boolean effetCarte(JoueurInterface j,CarteInterface carte) throws RemoteException{
    if(carte.getSymbole().equals("sens")){
      changeSens();
    }
    if(carte.getSymbole().equals("+2")){
      // le joueur suivant (selon le sens) pioche deux cartes
      if(this.sens){
        j.getRight().piocher(this.pioche.remove(0));
        j.getRight().piocher(this.pioche.remove(0));
      }else{
        j.getLeft().piocher(this.pioche.remove(0));
        j.getLeft().piocher(this.pioche.remove(0));
      }
      return true;
    }
    if(carte.getSymbole().equals("interdit")){
      return true;
    }
    return false;
  }

  /**
  * Methode appliquant les effets des CarteAction de couleur noire uniquement
  * @param j, le joueur possedant la carte
  * @param carte, la carte a jouer
  * @param col, la couleur a changer si la carte possede le symbole couleur
  */
  private void effetCarteNoire(JoueurInterface j,CarteInterface carte,String col) throws RemoteException{
    this.couleurChoisie = col;
    if(carte.getSymbole().equals("+4")){
      // le joueur suivant pioche 4 cartes
      if(this.sens){
        j.getRight().piocher(this.pioche.remove(0));
        j.getRight().piocher(this.pioche.remove(0));
        j.getRight().piocher(this.pioche.remove(0));
        j.getRight().piocher(this.pioche.remove(0));
      }else{
        j.getLeft().piocher(this.pioche.remove(0));
        j.getLeft().piocher(this.pioche.remove(0));
        j.getLeft().piocher(this.pioche.remove(0));
        j.getLeft().piocher(this.pioche.remove(0));
      }
    }
  }

  /**
  * Methode permettant au joueur courant de jouer une carte et d appliquer son effet, si la partie n est pas terminee
  * @param id, l identifiant du joueur
  * @param carte, la carte a jouer
  * @param col, la couleur a changer si l effet de la carte l impose
  * @param aPioche, booleen permettant de specifier si le joueur vient de piocher une carte et est en train de jouer la carte piochee
  * @return true si le Joueur a pu jouer sa carte, false sinon
  */
  public boolean JouerCarte(String id,CarteInterface carte,String col, boolean aPioche) throws IOException {
    // on recupere le joueur
    JoueurInterface j = this.getJoueurByID(id);
    boolean carteValide = false;
    if(!this.GameOver){
      if(j == this.courant){
        // si je suis le joueur courant, je recupere la derniere carte du talon
        CarteInterface last = this.talon.get(this.talon.size()-1);
        // si la pioche ne contient plus que 10 cartes je remplis la pioche avec le talon
        if(this.pioche.size()<=10){
          this.talonIntoPioche();
        }
        // si le joueur ne peut pas jouer
        if(carte == null){
          if(!aPioche){
            // il pioche s il ne vient pas de piocher
            CarteInterface cartePiocher = this.pioche.remove(0);
            j.piocher(cartePiocher);
            // on envoie l information a tous les joueurs
            this.sendAll();
          }
        }else{
          // si le joueur possede bien la carte qu il veut jouer
          if(j.contient(carte)){
            // si la carte est de couleur noire
            if(carte.getCouleur().equals("Noire")){
              this.effetCarteNoire(j,carte,col);
              if(carte.getSymbole().equals("+4")){
                this.CarteJouer(j,carte,true);
                return true;
              }
              carteValide = true;
            }else{
              // si la carte n est pas noire, mais elle est de la meme couleur que la couleur demandee
              if(carte.getCouleur().equals(this.couleurChoisie)){
                // si c est une carte action
                if(carte.getClassName().equals("CarteAction")){
                  // je verifie selon l effet si le joueur d apres saute son tour ou pas
                  if(this.effetCarte(j,carte)){
                    this.CarteJouer(j,carte,true);
                    return true;
                  }
                }
                // la carte est soit une carteAction et son effet vient d etre applique, soit une carte numero, la carte est validee le joueur peut la jouer
                carteValide = true;
              }else{
                // la carte n est pas noire et elle n est pas de la meme couleur que la couleur demandee
                if(carte.getClassName().equals(last.getClassName())){
                  // si c est une carte action
                  if(carte.getClassName().equals("CarteAction")){
                    // la carte a t elle le meme symbole que la derniere carte du talon
                    if(carte.getSymbole().equals(last.getSymbole())){
                      // la carte etant de meme symbole mais de couleur diffente je change la couleur demandee
                      this.couleurChoisie = carte.getCouleur();
                      // je verifie selon l effet si le joueur d apres saute son tour ou pas
                      if(this.effetCarte(j,carte)){
                        this.CarteJouer(j,carte,true);
                        return true;
                      }
                      carteValide = true;
                    }
                  }
                  // si la carte est une carte numero
                  if(carte.getClassName().equals("CarteNumero")){
                    // si le numero de la carte est le meme que celui de la derniere carte du talon
                    if(carte.getNumero() == last.getNumero()){
                      // la carte etant de meme numero mais de couleur diffente je change la couleur demandee
                      this.couleurChoisie = carte.getCouleur();
                      carteValide = true;
                    }
                  }
                }
              }
            }
          }
        }
        // si la carte est valide alors le joueur la joue et le joueur d apres ne saute pas son tour
        if(carteValide){
          this.CarteJouer(j,carte,false);
          return true;
        }
        // si le joueur vient de piocher et que sa carte ne peut pas etre jouee
        if(aPioche){
          // on passe au joueur suivant
          this.changeJoueur();
          j.setAJoue();
          return true;
        }
      }
    }
    return false;
  }

  /**
  * Methode effectuant les changements dus a la carte qu un joueur vient de jouer
  * @param j, le joueur qui vient de jouer une carte
  * @param c, la carte qui vient d etre jouee
  * @param pass, booleen permettant de specifier si la carte jouee implique que le joueur suivant doit passer son tour ou non
  */
  private void CarteJouer(JoueurInterface j,CarteInterface c,boolean pass) throws RemoteException{
    this.talon.add(c);
    j.jouer(c);
    // si le joueur n a plus de cartes en main
    if(j.getMain().size() == 0){
      // il a gagne
      j.getClient().incrementPoint();
      // la partie s arrete
      this.GameOver = true;
      // Creation d un thread attendant la reponse de tous les clients pour relancer une partie
      Thread t = new Thread(() -> {
        try {
          // 3 minutes d attente
          long time= System.currentTimeMillis();
          long end = time+180000;
          while(System.currentTimeMillis() < end){
            if(this.tousPret()){
              this.resetPartie();
              break;
            }
            Thread.sleep(100);
          }
          Thread.currentThread().join();
        } catch (Exception e) {
          e.printStackTrace();
        }
      });
      t.start();
      j.setAJoue();
      return;
    }
    // si le joueur passe son tour
    if(pass){
      if(this.sens){
        this.courant = this.courant.getRight().getRight();
      }else{
        this.courant = this.courant.getLeft().getLeft();
      }
    }else{
      this.changeJoueur();
    }
    j.setAJoue();
  }

  /**
   * Methode permettant d envoyer toute les informations necessaires a la maj des clients graphiques
   */
  private void sendAll() throws IOException {
    for(JoueurInterface joue : this.joueurs) {
      ClientInterface client = joue.getClient();
      // permet de demander aux clients si il veulent relancer une partie
      if(this.GameOver){
        client.setFinPartie(this.courant.getId());
      }else{
        client.setMyHand(joue.getMain());
        client.setCouleurChoisie(this.couleurChoisie);
        client.setJoueurHand(joue.getLeft().getId(), joue.getLeft().getMain().size());
        client.setJoueurHand(joue.getRight().getId(), joue.getRight().getMain().size());
        client.setJoueurHand(joue.getLeft().getLeft().getId(), joue.getLeft().getLeft().getMain().size());
        client.setTalon(this.talon.get(this.talon.size() - 1));
        client.setPlayerTurn(this.courant.getId());
      }
    }
  }

  /**
  * Methode permettant de verifier si un joueur peut jouer ou non
  * @param j, le joueur
  * @return la carte qui peut etre jouee, null sinon
  */
  public CarteInterface peutJouer(JoueurInterface j) throws RemoteException{
    // on recupere la derniere carte du talon
    CarteInterface last = this.talon.get(this.talon.size()-1);
    for(CarteInterface c : j.getMain()){
      if(c.getCouleur().equals("Noire")){
        return c;
      }
      if(c.getCouleur().equals(this.couleurChoisie)){
        return c;
      }else{
        if(c.getClassName().equals(last.getClassName())){
          if(c.getClassName().equals("CarteAction")){
            if(c.getSymbole().equals(last.getSymbole())){
              return c;
            }
          }
          if(c.getClassName().equals("CarteNumero")){
            if(c.getNumero() == last.getNumero()){
              return c;
            }
          }
        }
      }
    }
    return null;
  }

  /**
  * Methode permettant de passer au joueur d apres
  */
  private void changeJoueur() throws RemoteException{
    if(this.sens){
      this.courant = this.courant.getRight();
    }else{
      this.courant = this.courant.getLeft();
    }
  }

  /**
  * Methode permettant de mettre les cartes du talon dans la pioche, sans les melanger
  */
  private void talonIntoPioche(){
    for(CarteInterface carte : this.talon){
      this.pioche.add(carte);
    }
    this.talon.clear();
  }

  /**
  * Methode permettant de recuperer un joueur en fonction de son identifiant dans le tableau des joueurs
  * @param id, l identifiant
  * @return le joueur si il est present dans le tableau des joueurs, null sinon
  */
  private JoueurInterface getJoueurByID(String id) throws RemoteException{
    for(JoueurInterface j : this.joueurs){
      if(j.getId().equals(id)){
        return j;
      }
    }
    return null;
  }

  /**
  * Methode permettant de changer le sens du jeu
  */
  private void changeSens(){
    if(this.sens){
      this.sens = false;
    }else{
      this.sens = true;
    }
  }

  /**
   * Methode effectuant les changements dus a la carte qu un joueur vient de jouer
   * @param j, le joueur qui vient de jouer une carte
   */
  private synchronized void getCourant(JoueurInterface j) throws IOException, InterruptedException {
    // si j n est pas le joueur courant
    if(j != this.courant) {
      try {
        // je notifie un autre Thread qui est en train d attendre
        notify();
        // je me met en attente
        wait();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }else{
      // j attends que le joueur joue
      j.joueurCourant();
      // jenvoie l information a tous les joueurs
      this.sendAll();
      // j appelle un autre Thread
      notify();
    }
  }

  /**
  * Methode permettant de melanger une liste donnee en parametre
  * @param aMelanger, la liste a melanger
  */
  private void melangerList(List<CarteInterface> aMelanger){
	  Collections.shuffle(aMelanger);
  }

  /**
  * Methode permettant de specifier qu un des joueurs est pret
  * Cette methode doit etre en synchronized car le tableau n etant pas Thread safe, il ne faut pas que deux joueurs appelent cette methode en conccurence
  * @param id, l identifiant d un des joueur
  */
  public synchronized void joueurPret(String id){
    if(!this.idJoueursPret.contains(id)){
      this.idJoueursPret.add(id);
    }
  }

  /**
  * Methode permettant de savoir si tout les joueurs sont pret
  * @return true si le tableau des id des joueurs pret contient tout les joueurs, false sinon
  */
  private boolean tousPret() throws RemoteException{
    if(this.idJoueursPret.size() == 4){
      this.idJoueursPret.clear();
      return true;
    }
    return false;
  }

  /**
   * Methode permettant de relancer une partie si tout les joueurs sont pret
   */
  private void resetPartie() throws IOException {
    this.GameOver = false;
    this.sens = false;
    // on remet les cartes restantes dans les mains des joueurs dans la pioche
    for(JoueurInterface j : this.joueurs){
      for(CarteInterface c : j.getMain()){
        this.pioche.add(c);
      }
      j.getMain().clear();
    }
    // pareil pour les cartes du talon
    this.talonIntoPioche();
    // on relance avec les meme joueurs
    this.InitGame();
  }

}
