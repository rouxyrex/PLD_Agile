package controleur;

import javafx.util.Pair;
import modele.DemandeLivraison;
import modele.Intersection;
import modele.Livraison;
import modele.Tournee;
import vue.Fenetre;

public class CdeSuppressionLivraison implements Commande {
	
	private Fenetre fenetre;
	private DemandeLivraison demandeLivraison;
	private Livraison livraison;
	private Tournee tournee;
	Pair<Integer, Intersection> interAvantEnlevement;
	Pair<Integer, Intersection> interAvantDepot;
	
	/**
	 * Cree la commande qui ajoute la livraison l a la demande de livraison d
	 * @param d la demande de livraison dans laquelle ajouter l
	 * @param l la livraison a ajouter dans d
	 */
	public CdeSuppressionLivraison(Fenetre f, DemandeLivraison d, Livraison l, Tournee t, Pair<Integer, Intersection> interAvantEnlevement2, Pair<Integer, Intersection> interAvantDepot2){
		this.fenetre = f;
		this.demandeLivraison = d;
		this.livraison = l;
		this.tournee = t;
		
		if(interAvantEnlevement2 != null) {
			this.interAvantEnlevement = interAvantEnlevement2;
		}
		
		if(interAvantDepot2 != null) {
			this.interAvantDepot = interAvantDepot2;
		}
	}

	@Override
	public void doCde() {
		demandeLivraison.supprimerLivraison(livraison);
		
		fenetre.supprimerVueDemandeLivraison(livraison);
		
		if(tournee.estInitialise()) {
			Pair<Pair<Integer, Intersection>, Pair<Integer, Intersection>> paire = tournee.supprimerLivraison(livraison);
			this.interAvantEnlevement = paire.getKey();
			this.interAvantDepot = paire.getValue();
		}
	}

	@Override
	public void undoCde() {
		demandeLivraison.ajouterLivraison(livraison);
		
		fenetre.ajouterVueDemandeLivraison(livraison);
		
		if(tournee.estInitialise()) {
			tournee.ajouterLivraison(livraison, interAvantEnlevement, interAvantDepot);
		}
		
	}

}
