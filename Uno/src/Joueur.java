import java.util.ArrayList;

public class Joueur {
	private String identifiant;
	private List<Carte> main;
	private int nbPoints;
	
	public Joueur(String identifiant){
		this.identifiant = identifiant;
	}
	/*
	 * proc�dure mainInit appel� 1 fois en d�but de jeu pour distribuer les 7 cartes de la main initial du joueur.
	 */
	public void mainInit(JeuDeCarte jeu) {
		main = new ArrayList<Carte>();
		for(int i = 1; i<= 7; i++) {
			Carte tmp;
			tmp = jeu.tireCarte(); // return une Carte au hasard pr�sent dans le paquet, la carte tir� doit �tre retir� du paquet.
			main.add(tmp);
		}
	}
	/*
	 * proc�dure appel� quand le joueur joue une carte. Le joueur doit jouer une carte de sa main
	 */
	public void joueCarte(Carte carte) {
		//peut etre faire un try catch ici
		if(main.contains(carte)) {
			main.remove(carte);
			//joue la carte lol
		}
		else {
			
		}
	}
	/*
	 * proc�dure quand le joueur ne peut pas jouer de carte et doit en pioch� une.
	 */
	public void tireCarte(JeuDeCarte jeu) {
		main.add(jeu.TireCarte());
	}
}
