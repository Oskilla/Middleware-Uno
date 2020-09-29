
public class Joueur {
	private String identifiant;
	private List<Carte> main;
	private int nbPoints;
	
	public Joueur(String identifiant){
		this.identifiant = identifiant;
	}
	public void mainInit(JeuDeCarte jeu) {
		for(int i = 1; i<= 7; i++) {
			Carte tmp;
			tmp = jeu.tireCarte(); // return une Carte au hasard pr�sent dans le paquet, la carte tir� doit �tre retir� du paquet.
			main.add(tmp);
		}
	}
	public void joueCarte() {
		
	}
	public void tireCarte(JeuDeCarte jeu) {
		main.add(jeu.TireCarte());
	}
}
