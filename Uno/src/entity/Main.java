import java.util.*;

public class Main {

	public static void main(String[] args) {
    Joueur j = new Joueur("Damien",null,null);
    Joueur j2 = new Joueur("Damien2",null,null);
    Joueur j3 = new Joueur("Damien3",null,null);
    Joueur j4 = new Joueur("Damien4",null,null);

    List<Joueur> joueurs = new ArrayList<Joueur>();
		Scanner sc=new Scanner(System.in);
    joueurs.add(j);
    joueurs.add(j2);
    joueurs.add(j3);
    joueurs.add(j4);
    Uno uno = new Uno(joueurs);
    uno.InitGame();
		System.out.println("Talon: " + uno.getTalon().get(uno.getTalon().size()-1).affiche());
		j.montreMain();
		int c = sc.nextInt();
		System.out.println(uno.JouerCarte(j,null,null));
		System.out.println("Talon: " + uno.getTalon().get(uno.getTalon().size()-1).affiche());
		System.out.println();
		j.montreMain();
		System.out.println();
		j2.montreMain();
		c = sc.nextInt();
		System.out.println(uno.JouerCarte(j2,j2.getMain().get(c),null));
		System.out.println("Talon: " + uno.getTalon().get(uno.getTalon().size()-1).affiche());
		System.out.println();
		j.montreMain();
		System.out.println();
		j2.montreMain();
		System.out.println();
		j3.montreMain();
		c = sc.nextInt();
		System.out.println(uno.JouerCarte(j3,j3.getMain().get(c),null));
		System.out.println("Talon: " + uno.getTalon().get(uno.getTalon().size()-1).affiche());
		System.out.println();
		j3.montreMain();
  }
}
