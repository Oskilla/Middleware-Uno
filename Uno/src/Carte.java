
public class Carte {
	private Figure figure;
	private int valeur;
	private String couleur;
	
	public Carte(String f, int val, String col) {
		this.figure = f;
		this.valeur = val;
		this.couleur = col;
	}
	
	public Figure getFigure() {
		return figure;
	}
	
	public void setFigure(Figure figure) {
		this.figure = figure;
	}
	public int getValeur() {
		return valeur;
	}
	public void setValeur(int valeur) {
		this.valeur = valeur;
	}
	public String getCouleur() {
		return couleur;
	}
	public void setCouleur(String couleur) {
		this.couleur = couleur;
	}
}
