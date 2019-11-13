package controleur;

import javafx.util.Pair;
import modele.DemandeLivraison;
import modele.Intersection;
import modele.Livraison;
import modele.Tournee;

/** Commande de suppression de la livraison
*/
public class CdeSuppressionLivraison implements Commande {
	
	private DemandeLivraison demandeLivraison;
	private Livraison livraison;
	private Tournee tournee;
	
	/**
	    * Intersection visitee avant l'enlevement de la livraison consideree
	    */
	Pair<Integer, Intersection> interAvantEnlevement;
	/**
	    * Intersection visitee apres le depot de la livraison consideree
	    */
	Pair<Integer, Intersection> interAvantDepot;
	
	/**
	 * Cree la commande qui supprime la livraison de la demande de livraison d
	 * @param d la demande de livraison de laquelle supprimer l
	 * @param l la retirer a de d
	 */
	public CdeSuppressionLivraison(DemandeLivraison d, Livraison l, Tournee t, Pair<Integer, Intersection> interAvantEnlevement2, Pair<Integer, Intersection> interAvantDepot2){
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
		
		if(tournee.estInitialise()) {
			Pair<Pair<Integer, Intersection>, Pair<Integer, Intersection>> paire = tournee.supprimerLivraison(livraison);
			this.interAvantEnlevement = paire.getKey();
			this.interAvantDepot = paire.getValue();
		}
	}

	@Override
	public void undoCde() {
		demandeLivraison.ajouterLivraison(livraison);
		
		if(tournee.estInitialise()) {
			tournee.ajouterLivraison(livraison, interAvantEnlevement, interAvantDepot);
		}
		
	}

}
