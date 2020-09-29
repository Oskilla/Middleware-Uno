import java.util.ArrayList;
import java.util.List;

public class Joueur {
	private String identifiant;
	private List<Carte> main;
	private int nbPoints;
	
	public Joueur(String identifiant){
		this.identifiant = identifiant;
	}
	/*
	 * procédure mainInit appelé 1 fois en début de jeu pour distribuer les 7 cartes de la main initial du joueur.
	 */
	public void mainInit(JeuDeCartes jeu) {
		main = new ArrayList<Carte>();
		for(int i = 1; i<= 7; i++) {
			Carte tmp;
			tmp = jeu.tireCarte(); // return une Carte au hasard présent dans le paquet, la carte tiré doit être retiré du paquet.
			main.add(tmp);
		}
	}
	/*
	 * procédure appelé quand le joueur joue une carte. Le joueur doit jouer une carte de sa main
	 */
	public void joueCarte(Carte carte) {
		
	}
	/*
	 * procédure quand le joueur ne peut pas jouer de carte et doit en pioché une.
	 */
	public void tireCarte(JeuDeCartes jeu) {
		main.add(jeu.TireCarte());
	}
}
