package controleur;

import modele.DemandeLivraison;
import modele.Livraison;

public class CdeAjoutLivraison implements Commande {
	
	private DemandeLivraison demandeLivraison;
	private Livraison livraison;
	
	/**
	 * Cree la commande qui ajoute la livraison l a la demande de livraison d
	 * @param d la demande de livraison dans laquelle ajouter l
	 * @param l la livraison a ajouter dans d
	 */
	public CdeAjoutLivraison(DemandeLivraison d, Livraison l){
		this.demandeLivraison = d;
		this.livraison = l;
	}

	@Override
	public void doCde() {
		demandeLivraison.ajouterLivraison(livraison);
	}

	@Override
	public void undoCde() {
		demandeLivraison.supprimerLivraison(livraison);
	}

}
