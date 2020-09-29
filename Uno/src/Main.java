
import java.util.ArrayList;
import java.util.List;

public class Main {
	
	static final String[] Figures = {"Numéro","Interdit","Sens","+2","+4","Couleur"};
	
	static final String[] Couleurs = {"Rouge","Vert","Jaune","Bleu","Noir"};
  
	static List<Carte> paquet = new ArrayList<Carte>();


	public static void main(String[] args) {
		
		
	}
		
	private void CreationPaquet() {
		int cptCouleurs, cptFigure;
		//Creation du jeu de Uno pour les 4 Couleurss
		for(cptCouleurs= 0;cptCouleurs <4; cptCouleurs ++ ) {
			//creation des cartes Numéros
			cptFigure = 0;
			//la Carte 0 étatnt unique on la crée avant
			paquet.add(new Carte(Figures[cptFigure],0,Couleurs[cptCouleurs]));
			for(int cptNum =0; cptNum < 18; cptNum ++) {
				//Comme l'on doit crée 2 fois chacune des num. On les passe mod 9 et on ajoute 1
				paquet.add(new Carte(Figures[cptFigure],(cptNum % 9)+1,Couleurs[cptCouleurs]));	
			}
			//creation des cartes sens interdit
			cptFigure += 1;
			paquet.add(new Carte(Figures[cptFigure],20,Couleurs[cptCouleurs]));
			paquet.add(new Carte(Figures[cptFigure],20,Couleurs[cptCouleurs]));
			//creation des carrtes changement de sens
			cptFigure += 1;
			paquet.add(new Carte(Figures[cptFigure],20,Couleurs[cptCouleurs]));
			paquet.add(new Carte(Figures[cptFigure],20,Couleurs[cptCouleurs]));
			//Creation des cartes +2
			cptFigure += 1;
			paquet.add(new Carte(Figures[cptFigure],20,Couleurs[cptCouleurs]));
			paquet.add(new Carte(Figures[cptFigure],20,Couleurs[cptCouleurs]));
			//creation des cartes +4
			cptFigure+=1;
			paquet.add(new Carte(Figures[cptFigure],50,Couleurs[4]));
			//creation des cartes Joker (Changement de Couleurs)
			cptFigure+=1;
			paquet.add(new Carte(Figures[cptFigure],50,Couleurs[4]));	
		}

}
