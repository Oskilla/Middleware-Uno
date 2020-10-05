public class CarteNumero extends Carte {

  private int numero;

  public CarteNumero(String col, String des, int num) {
    super(col,des);
    this.numero = num;
  }

  public int getNum(){
    return this.numero;
  }
}
