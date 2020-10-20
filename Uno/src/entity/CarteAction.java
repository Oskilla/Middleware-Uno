public class CarteAction extends Carte {

  private String symbole;

  public CarteAction(String col, String sym) {
    super(col);
    this.symbole = sym;
  }

  public String getSymbole(){
    return this.symbole;
  }

  public int getNumero(){
    return -1;
  }

  public String affiche(){
    return "Carte: " + this + " couleur: " + this.getCouleur() + " symbole: " + this.symbole;
  }

}
