public class CarteAction extends Carte {

  private String symbole;

  public CarteAction(String col, String des, String sym) {
    super(col,des);
    this.symbole = sym;
  }

  public String getSymbole(){
    return this.symbole;
  }

  public String jouerEffet(){
    return "non définie";
  }
}
