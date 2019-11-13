package controleur;

import vue.Fenetre;
import modele.Plan;
import modele.Tournee;
import modele.DemandeLivraison;
import modele.GraphePCC;
import modele.Livraison;

public interface Etat {

	/**
	 * Methode appelee par le controleur apres un clic sur le bouton "Charger un Plan"
	 * @param c le controleur
	 * @param f la fenetre
	 */
	public default void chargerPlan(Controleur c, Fenetre f, ListeDeCdes listeDeCdes, Plan plan, DemandeLivraison demandeLivraison){};

	/**
	 * Methode appelee par controleur apres un clic sur le bouton "Charger une demande de livraison"
	 * @param c le controleur
	 * @param f la fenetre
	 */
	public default void chargerDemandeLivraison(Controleur c, Fenetre f, ListeDeCdes listeDeCdes, Plan plan, DemandeLivraison demandeLivraison){};

	/**
	 * Methode appelee par fenetre apres un clic sur le bouton "Calculer une tournee"
	 * @param c le controleur
	 * @param f la fenetre
	 */
//	public default void calculerTournee(Controleur c, Fenetre f, Plan plan, DemandeLivraison demandeLivraison) {};

	/**
	 * Methode appelee par fenetre apres un clic sur le bouton "Supprimer une livraison" puis le chois d'une livraison par l'utilisateur
	 * @param c le controleur
	 * @param f la fenetre
	 */
	public default void supprimerLivraison(Controleur c, Fenetre f, ListeDeCdes listeDeCdes, Plan plan, DemandeLivraison demandeLivraison, Livraison livraison) {};

	/**
	 * Methode appelee par le controleur apres un clic sur le bouton "Undo"
	 * @param l la liste des commandes en cours
	 */
	public default void undo(Fenetre f, ListeDeCdes l){};

	/**
	 * Methode appelee par le controleur apres un clic sur le bouton "Redo"
	 * @param l la liste des commandes en cours
	 */
	public default void redo(Fenetre f, ListeDeCdes l){};

	/**
	 * Methode appelee par le controleur apres un clic sur le bouton "Redo"
	 *
	 */
	public default void genererFeuilleRoute(Controleur controleur, Fenetre fenetre, Tournee tournee) {};

	public default void calculerTournee(Controleur controleur, Fenetre fenetre, Plan plan, DemandeLivraison demandeLivraison,
			GraphePCC graphePCC, Tournee tournee) {};
}
