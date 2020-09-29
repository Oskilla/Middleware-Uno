import java.util.List;
import java.util.ArrayList;

public class JeuDeCartes {
	private List<Carte> paquet;
	private List<String> couleur;
	
	public JeuDeCartes() {
		this.couleur = new ArrayList<String>();
		this.couleur.add("Rouge");
		this.couleur.add("Jaune");
		this.couleur.add("Bleu");
		this.couleur.add("Vert");
		this.couleur.add("Neutre");
		CreerJeu();
	}
	
	private void CreerJeu() {
		this.paquet = new ArrayList<Carte>();
		
		int cptCouleur, cptFigure;
		//Creation du jeu de Uno pour les 4 couleurs
		for(cptCouleur= 0;cptCouleur <4; cptCouleur ++ ) {
			//creation des cartes Numéros
			cptFigure = 0;
			paquet.add(new Carte(Figures.get(cptFigure),0,couleur.get(cptCouleur)));
			paquet.add(new Carte(Figures.get(cptFigure),1,couleur.get(cptCouleur)));
			paquet.add(new Carte(Figures.get(cptFigure),1,couleur.get(cptCouleur)));
			paquet.add(new Carte(Figures.get(cptFigure),2,couleur.get(cptCouleur)));
			paquet.add(new Carte(Figures.get(cptFigure),2,couleur.get(cptCouleur)));
			paquet.add(new Carte(Figures.get(cptFigure),3,couleur.get(cptCouleur)));
			paquet.add(new Carte(Figures.get(cptFigure),3,couleur.get(cptCouleur)));
			paquet.add(new Carte(Figures.get(cptFigure),4,couleur.get(cptCouleur)));
			paquet.add(new Carte(Figures.get(cptFigure),4,couleur.get(cptCouleur)));
			paquet.add(new Carte(Figures.get(cptFigure),5,couleur.get(cptCouleur)));
			paquet.add(new Carte(Figures.get(cptFigure),5,couleur.get(cptCouleur)));
			paquet.add(new Carte(Figures.get(cptFigure),6,couleur.get(cptCouleur)));
			paquet.add(new Carte(Figures.get(cptFigure),6,couleur.get(cptCouleur)));
			paquet.add(new Carte(Figures.get(cptFigure),7,couleur.get(cptCouleur)));
			paquet.add(new Carte(Figures.get(cptFigure),7,couleur.get(cptCouleur)));
			paquet.add(new Carte(Figures.get(cptFigure),8,couleur.get(cptCouleur)));
			paquet.add(new Carte(Figures.get(cptFigure),8,couleur.get(cptCouleur)));
			paquet.add(new Carte(Figures.get(cptFigure),9,couleur.get(cptCouleur)));
			paquet.add(new Carte(Figures.get(cptFigure),9,couleur.get(cptCouleur)));
			//creation des cartes sens interdit
			cptFigure += 1;
			paquet.add(new Carte(Figures.get(cptFigure),20,couleur.get(cptCouleur)));
			paquet.add(new Carte(Figures.get(cptFigure),20,couleur.get(cptCouleur)));
			//creation des carrtes changement de sens
			cptFigure += 1;
			paquet.add(new Carte(Figures.get(cptFigure),20,couleur.get(cptCouleur)));
			paquet.add(new Carte(Figures.get(cptFigure),20,couleur.get(cptCouleur)));
			//Creation des cartes +2
			cptFigure += 1;
			paquet.add(new Carte(Figures.get(cptFigure),20,couleur.get(cptCouleur)));
			paquet.add(new Carte(Figures.get(cptFigure),20,couleur.get(cptCouleur)));
			//creation des cartes Joker (Changement de couleur)
			cptFigure+=1;
			paquet.add(new Carte(Figures.get(cptFigure),50,couleur.get(4)));
			//creation des cartes +4
			cptFigure+=1;
			paquet.add(new Carte(Figures.get(cptFigure),50,couleur.get(4)));
			
		}
	}
	
}
