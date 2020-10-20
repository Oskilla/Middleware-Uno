import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Arrays;

public class Uno {

  private List<Joueur> joueurs = new ArrayList<Joueur>();
  private List<Carte> talon = new ArrayList<Carte>();
  private List<Carte> pioche = new ArrayList<Carte>();
  private final List<String> couleurs = new ArrayList<String>(Arrays.asList("Rouge","Bleu","Jaune","Vert","Noire"));
  private final List<String> symboles = new ArrayList<String>(Arrays.asList("+2","sens","interdit","+4","couleur"));
  private Boolean GameOver = false;
  private Joueur courant;
  private String couleurChoisie;
  private boolean sens = true; //true pour sens horraire, false pour anti-horraire.

  public Uno(List<Joueur> Listjoueurs){
    for (int i=0;i<Listjoueurs.size();i++) {
      switch (i) {
        case 0:
          Listjoueurs.get(i).setRight(Listjoueurs.get(i+1));
          break;
        default:
          if(i == Listjoueurs.size()-1){
            Listjoueurs.get(i).setLeft(Listjoueurs.get(i-1));
            Listjoueurs.get(i).setRight(Listjoueurs.get(0));
            Listjoueurs.get(0).setLeft(Listjoueurs.get(i));
          }else{
            Listjoueurs.get(i).setLeft(Listjoueurs.get(i-1));
            Listjoueurs.get(i).setRight(Listjoueurs.get(i+1));
          }
          break;
      }
      this.joueurs.add(Listjoueurs.get(i));
    }
    for(int j=0;j<couleurs.size()-1;j++){
      for(int z=0;z<10;z++){
        CarteNumero carte = new CarteNumero(couleurs.get(j),z);
        if(z != 0){
          CarteNumero carte2 = new CarteNumero(couleurs.get(j),z);
          this.pioche.add(carte2);
        }
        this.pioche.add(carte);
      }
      for (int m=0;m<3;m++){
        CarteAction carteAction = new CarteAction(couleurs.get(j),symboles.get(m));
        CarteAction carteAction2 = new CarteAction(couleurs.get(j),symboles.get(m));
        this.pioche.add(carteAction);
        this.pioche.add(carteAction2);
      }
    }
    for(int h=0;h<4;h++){
      CarteAction carteJoker = new CarteAction(couleurs.get(couleurs.size()-1),symboles.get(symboles.size()-2));
      CarteAction carteJoker2 = new CarteAction(couleurs.get(couleurs.size()-1),symboles.get(symboles.size()-1));
      this.pioche.add(carteJoker);
      this.pioche.add(carteJoker2);
    }
  }

  public void InitGame(){
	  this.melangerList(this.pioche);
    for(Joueur j : this.joueurs){
      for(int i=0;i<7;i++){
        j.piocher(this.pioche.remove(0));
      }
    }
    while(this.pioche.get(0).getClass() == CarteAction.class){
      this.melangerList(this.pioche);
    }
    Carte c = this.pioche.remove(0);
    this.couleurChoisie = c.getCouleur();
    this.talon.add(c);
    this.courant = this.joueurs.get(0);
  }

  public boolean JouerCarte(Joueur j,Carte carte,String col){
    if(!this.GameOver){
      if(j == courant){
        Carte last = this.talon.get(this.talon.size()-1);
        if(this.pioche.size()<=4){
          this.talonIntoPioche();
        }
        if(carte == null){
          if(this.peutJouer(j) == null){
            Carte cartePiocher = this.pioche.remove(0);
            j.piocher(cartePiocher);
            if(this.peutJouer(cartePiocher,j)){
              this.CarteJouer(j,cartePiocher);
            }else{
              if(this.sens){
                this.courant = this.courant.getRight();
              }else{
                this.courant = this.courant.getLeft();
              }
            }
            return true;
          }
        }else{
          if(carte.getCouleur() == "Noire"){
            if(carte.getSymbole() == "couleur"){
              if(col == null){
                return false;
              }else{
                this.couleurChoisie = col;
              }
            }
            if(carte.getSymbole() == "+4"){
              if(this.sens){
                j.getRight().piocher(this.pioche.remove(0));
                j.getRight().piocher(this.pioche.remove(0));
                j.getRight().piocher(this.pioche.remove(0));
                j.getRight().piocher(this.pioche.remove(0));
                this.courant = j.getRight();
              }else{
                j.getLeft().piocher(this.pioche.remove(0));
                j.getLeft().piocher(this.pioche.remove(0));
                j.getLeft().piocher(this.pioche.remove(0));
                j.getLeft().piocher(this.pioche.remove(0));
                this.courant = j.getLeft();
              }
            }
            this.CarteJouer(j,carte);
            return true;
          }else{
            if(carte.getCouleur() == couleurChoisie){
              if(carte.getClass() == CarteAction.class){
                switch (carte.getSymbole()){
                  case "sens":
                    this.sens = !this.sens;
                    break;
                  case "+2":
                    if(this.sens){
                      j.getRight().piocher(this.pioche.remove(0));
                      j.getRight().piocher(this.pioche.remove(0));
                      this.courant = j.getRight();
                    }else{
                      j.getLeft().piocher(this.pioche.remove(0));
                      j.getLeft().piocher(this.pioche.remove(0));
                      this.courant = j.getLeft();
                    }
                    break;
                  case "interdit":
                    if(this.sens){
                      this.courant = j.getRight();
                    }else{
                      this.courant = j.getLeft();
                    }
                    break;
                }
              }
              this.CarteJouer(j,carte);
              return true;
            }else{
              if(carte.getClass() == last.getClass()){
                switch (carte.getClass().toString()){
                  case "CarteAction":
                    if(carte.getSymbole() == last.getSymbole()){
                      if(carte.getSymbole() == "sens"){
                        this.sens = !this.sens;
                      }
                      if(carte.getSymbole() == "+2"){
                        if(this.sens){
                          j.getRight().piocher(this.pioche.remove(0));
                          j.getRight().piocher(this.pioche.remove(0));
                          this.courant = j.getRight();
                        }else{
                          j.getLeft().piocher(this.pioche.remove(0));
                          j.getLeft().piocher(this.pioche.remove(0));
                          this.courant = j.getLeft();
                        }
                      }
                      if(carte.getSymbole() == "interdit"){
                        if(this.sens){
                          this.courant = j.getRight();
                        }else{
                          this.courant = j.getLeft();
                        }
                      }
                      this.CarteJouer(j,carte);
                      return true;
                    }
                    break;
                  case "CarteNumero":
                    if(carte.getNumero() == last.getNumero()){
                      this.CarteJouer(j,carte);
                      return true;
                    }
                    break;
                }
              }
            }
          }
        }
      }
    }
    return false;
  }

  public void CarteJouer(Joueur j,Carte c){
    this.talon.add(c);
    j.jouer(c);
    if(j.getMain().size() == 0){
      if(j.getLeft() == j.getRight()){
        this.GameOver = true;
      }else{
        j.getLeft().setRight(j.getRight());
        j.getRight().setLeft(j.getLeft());
      }
    }
    if(this.sens){
      this.courant = this.courant.getRight();
    }else{
      this.courant = this.courant.getLeft();
    }
  }

  public Carte peutJouer(Joueur j){
    Carte last = this.talon.get(this.talon.size()-1);
    for(Carte c : j.getMain()){
      if(c.getCouleur() == "Noire"){
        return c;
      }
      if(c.getCouleur() == this.couleurChoisie){
        return c;
      }else{
        if(c.getClass() == last.getClass()){
          switch (c.getClass().toString()){
            case "CarteAction":
              if(c.getSymbole() == last.getSymbole()){
                return c;
              }
              break;
            case "CarteNumero":
              if(c.getNumero() == last.getNumero()){
                return c;
              }
              break;
          }
        }
      }
    }
    return null;
  }

  public boolean peutJouer(Carte c, Joueur j){
    Carte last = this.talon.get(this.talon.size()-1);
    if(j.getMain().contains(c)){
      if(c.getCouleur() == "Noire"){
        return true;
      }
      if(c.getCouleur() == this.couleurChoisie){
        return true;
      }else{
        if(c.getClass() == last.getClass()){
          switch (c.getClass().toString()){
            case "CarteAction":
              if(c.getSymbole() == last.getSymbole()){
                return true;
              }
              break;
            case "CarteNumero":
              if(c.getNumero() == last.getNumero()){
                return true;
              }
              break;
          }
        }
      }
    }
    return false;
  }

  public void talonIntoPioche(){
    this.melangerList(this.talon);
    for(Carte carte : this.talon){
      this.pioche.add(carte);
    }
    this.talon.clear();
  }

  public List<Joueur> getJoueurs(){
	  return joueurs;
  }

  public Joueur getJoueur(int i){
	  return joueurs.get(i);
  }

  public List<Carte> getTalon(){
	  return talon;
  }

  public List<Carte> getPioche(){
	  return pioche;
  }

  public boolean isGameOver(){
	  return GameOver;
  }

  public void setGameOver(){
	  this.GameOver = true;
  }

  public Joueur getCourant(){
	  return courant;
  }

  public String getCouleurChoisie(){
    return this.couleurChoisie;
  }

  public void setCourant(Joueur newCourant){
	  courant = newCourant;
  }

  public boolean getSens(){
	  return sens;
  }

  public void setSens(){
	  this.sens = !sens;
  }

  public void melangerList(List<Carte> aMelanger){
	  Collections.shuffle(aMelanger);
  }

}
