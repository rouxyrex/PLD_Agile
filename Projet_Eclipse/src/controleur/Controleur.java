package controleur;

import java.util.List;
import java.util.LinkedList;

import modele.DemandeLivraison;
import modele.GraphePCC;
import modele.Intersection;
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

	private Etat etatCourant;
	// Instances associees a chaque etat possible du controleur
	protected final EtatInit etatInit = new EtatInit();
	protected final EtatPlanCharge etatPlanCharge = new EtatPlanCharge();
	protected final EtatDemandeLivraisonCharge etatDemandeLivraisonCharge = new EtatDemandeLivraisonCharge();


	public Controleur(int echelle)
	{
		etatCourant = etatInit;
		plan = new Plan();
		demandeLivraison = new DemandeLivraison();

		fenetre = new Fenetre(plan, demandeLivraison, echelle, this);
	} 
	public void zoom() {
		fenetre.zoom();
	}
	public void dezoom() {
		fenetre.dezoom();
	}
	public void haut() {
		fenetre.haut();
	}
	public void bas() {
		fenetre.bas();
	}
	public void droite() {
		fenetre.droite();
	}
	public void gauche() {
		fenetre.gauche();
	}

	public void calculerTournee() {
		tournee = new Tournee();
		List <Trajet> trajets = tournee.getParcours();
		fenetre.afficherTournee(trajets);
	}


	public void genererFeuilleRoute() {
		// TODO Auto-generated method stub
		fenetre.genererFeuilleRoute();
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
		etatCourant.chargerPlan(this, fenetre, plan, demandeLivraison);
	}


	/**
	 * Methode appelee par fenetre apres un clic sur le bouton "Charger une demande de livraison"
	 */
	public void chargerDemandeLivraison() {
		etatCourant.chargerDemandeLivraison(this, fenetre, plan, demandeLivraison);
	}




	public void creerGraphePCC() {

		int nbSommets = 1 + demandeLivraison.getPtsPassage().size();
		graphePCC = new GraphePCC(nbSommets);

		Intersection entrepot = demandeLivraison.getEntrepot();

		LinkedList<Trajet> graphouille;

		graphouille = plan.Dijkstra(demandeLivraison, entrepot);
		graphePCC.ajouterGraphouille(graphouille, 0);

		for(int i = 1; i < nbSommets; i++) {

			Intersection intersectionInitiale = demandeLivraison.getPtsPassage().get(i);
			graphouille = plan.Dijkstra(demandeLivraison, intersectionInitiale);
			graphePCC.ajouterGraphouille(graphouille, i);

		}

	}
	public DemandeLivraison getDemandeLivraison() {
		// TODO Auto-generated method stub
		return demandeLivraison;
	}
}
