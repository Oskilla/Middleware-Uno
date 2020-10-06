public abstract class Carte {

  private String couleur;
  private String description;

  public Carte(String  col, String des) {
    this.couleur = col;
    this.description = des;
  }

  public String getCouleur(){
    return this.couleur;
  }

  public String getDescription(){
    return this.description;
  }
}
