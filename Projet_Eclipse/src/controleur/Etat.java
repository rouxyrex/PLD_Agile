package controleur;

import vue.Fenetre;
import modele.Plan;
import modele.DemandeLivraison;

public interface Etat {
	
	/**
	 * Methode appelee par le controleur apres un clic sur le bouton "Ajouter un cercle"
	 * @param c le controleur
	 * @param f la fenetre
	 */
	public default void chargerPlan(Controleur c, Fenetre f, Plan plan, DemandeLivraison demandeLivraison){};
	
	/**
	 * Methode appelee par controleur apres un clic sur le bouton "Ajouter un rectangle"
	 * @param c le controleur
	 * @param f la fenetre
	 */
	public default void chargerDemandeLivraison(Controleur c, Fenetre f, Plan plan, DemandeLivraison demandeLivraison){};
	
	
	
}