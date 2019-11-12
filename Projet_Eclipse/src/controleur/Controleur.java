package controleur;

import java.util.LinkedList;

import modele.DemandeLivraison;
import modele.GraphePCC;
import modele.Intersection;
import modele.Livraison;
import modele.Plan;
import modele.Tournee;
import modele.Trajet;
import vue.Fenetre;

public class Controleur {
	
	private Fenetre fenetre;
	private Plan plan;
	private DemandeLivraison demandeLivraison;
	private GraphePCC graphePCC;
	private Tournee tournee;
	private ListeDeCdes listeDeCdes;
	
	private Etat etatCourant;
	// Instances associees a chaque etat possible du controleur
	protected final EtatInit etatInit = new EtatInit();
	protected final EtatPlanCharge etatPlanCharge = new EtatPlanCharge();
	protected final EtatDemandeLivraisonCharge etatDemandeLivraisonCharge = new EtatDemandeLivraisonCharge();
	protected final EtatTourneeCalculee etatTourneeCalculee = new EtatTourneeCalculee();
		
	
	public Controleur(int echelle)
	{
		etatCourant = etatInit;
		plan = new Plan();
		demandeLivraison = new DemandeLivraison();
		graphePCC = new GraphePCC();
		tournee = new Tournee();
		listeDeCdes = new ListeDeCdes();
		
		fenetre = new Fenetre(plan, demandeLivraison, tournee, echelle, this);
	}
	
	/**
	 * Change l'etat courant du controleur
	 * @param etat le nouvel etat courant
	 */
	protected void setEtatCourant(Etat etat){
		etatCourant = etat;
	}
	
	
	// Methodes correspondant aux evenements utilisateur
	/**
	 * Methode appelee par fenetre apres un clic sur le bouton "Charger un plan"
	 */
	public void chargerPlan() {
		etatCourant.chargerPlan(this, fenetre, listeDeCdes, plan, demandeLivraison, tournee);
	}
	
	
	/**
	 * Methode appelee par fenetre apres un clic sur le bouton "Charger une demande de livraison"
	 */
	public void chargerDemandeLivraison() {
		etatCourant.chargerDemandeLivraison(this, fenetre, listeDeCdes, plan, demandeLivraison, tournee);
	}
	
	/**
	 * Methode appelee par fenetre apres un clic sur le bouton "Calculer une tournee"
	 */
	public void calculerTournee() {
		etatCourant.calculerTournee(this, fenetre, listeDeCdes, plan, demandeLivraison, graphePCC, tournee);
	}
	
	/**
	 * Methode appelee par fenetre apres un clic sur le bouton "Generer feuille de route"
	 */
	public void genererFeuilleRoute() {
		//Creer un methode generer feuille de route dans etat
	}
	
	/**
	 * Methode appelee par fenetre apres un clic sur le bouton "Supprimer une livraison" puis le choix d'une livraison par l'utilisateur
	 */
	public void supprimerLivraison(Livraison livraison) {
		etatCourant.supprimerLivraison(this, fenetre, listeDeCdes, plan, demandeLivraison, livraison, tournee);
	}
	
	/**
	 * Methode appelee par la fenetre quand l'utilisateur clique sur le bouton "Undo"
	 */
	public void undo(){
		etatCourant.undo(fenetre, listeDeCdes);
	}

	/**
	 * Methode appelee par fenetre apres un clic sur le bouton "Redo"
	 */
	public void redo(){
		etatCourant.redo(fenetre, listeDeCdes);
	}
	
}