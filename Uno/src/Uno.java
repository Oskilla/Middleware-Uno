public class Uno {

  private List<Joueur> joueurs = new ArrayList<Joueur>();
  private List<Carte> talon = new ArrayList<Carte>();
  private List<Carte> pioche = new ArrayList<Carte>();
  private Boolean GameOver = false;
  private Joueur courant;
  private int sens = 1;

  public Uno(List<Carte> jeu, List<Joueur> j){
    for (int i=0; i<jeu.size();i++) {
      this.pioche.add(jeu.get(i));
    }
    for (int j=0;j<j.size();j++) {
      this.joueurs.add(j.get(i));
    }
  }

  public void InitGame(){
    for (int i=0; ; ) {

    }
  }
}
