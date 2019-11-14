package controleur;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import javafx.util.Pair;
import modele.DemandeLivraison;
import modele.GraphePCC;
import modele.Intersection;
import modele.Livraison;
import modele.Plan;
import modele.Tournee;
import vue.Fenetre;
import xml.ExceptionXml;
import xml.LectureXml;

/** Etat a la suite d'un calcul de tournee reussi
*/
public class EtatTourneeCalculee implements Etat {

	// Etat initial 
	
	/** Une fois la tournée calculée, il est possible de charger un nouveau plan 
	 *   @param controleur
	 *   @param fenetre 
	 *   @param listeDeCdes
	 *   @param plan Le plan courant chargé
	 *   @param demandeLivraison La demande de livraison courante chargée
	 *   @param tournee La tournée courante calculée
	*/
	@Override
	public void chargerPlan(Controleur controleur, Fenetre fenetre, ListeDeCdes listeDeCdes, Plan plan, DemandeLivraison demandeLivraison, Tournee tournee) {

		fenetre.afficheMessage("Chargement d'un plan : Veuillez saisir le fichier XML reprï¿½sentant le plan.");

		try {
			LectureXml.creerPlan(plan);

			demandeLivraison.reset();
			fenetre.effacerVuePlan();
			fenetre.effacerVueDemandeLivraison();

			tournee.reset();
			fenetre.effacerVueTournee();

			listeDeCdes.reset();

			fenetre.initialiserVuePlan();
			controleur.setEtatCourant(controleur.etatPlanCharge);

		} catch (IOException | ParserConfigurationException | SAXException | NumberFormatException | ExceptionXml e) {
			fenetre.afficheAlerte(e.getMessage());
		}
	}
	
	
	/** Une fois la tournée calculée, il est possible de charger une nouvelle demande de livraison
	 *   @param controleur
	 *   @param fenetre 
	 *   @param listeDeCdes
	 *   @param plan Le plan courant chargé
	 *   @param demandeLivraison La demande de livraison courante chargée
	 *   @param tournee La tournée courante calculée
	*/
	@Override
	public void chargerDemandeLivraison(Controleur controleur, Fenetre fenetre, ListeDeCdes listeDeCdes, Plan plan, DemandeLivraison demandeLivraison, Tournee tournee) {

		fenetre.afficheMessage("Chargement d'une demande de livraison : Veuillez saisir le fichier XML reprï¿½sentant la demande de livraison.");

		try {
			LectureXml.creerDemandeDeLivraison(plan, demandeLivraison);

			fenetre.effacerVueDemandeLivraison();

			tournee.reset();
			fenetre.effacerVueTournee();

			listeDeCdes.reset();

			fenetre.initialiserVueDemandeLivraison();
			controleur.setEtatCourant(controleur.etatDemandeLivraisonCharge);

		} catch (IOException | ParserConfigurationException | SAXException | NumberFormatException | ExceptionXml e) {
			fenetre.afficheAlerte(e.getMessage());
		}
	}

	
	/** Une fois la tournée calculée, il est possible de calculer une nouvelle fois la tournée
	 *   @param controleur
	 *   @param fenetre 
	 *   @param listeDeCdes
	 *   @param plan Le plan courant chargé
	 *   @param demandeLivraison La demande de livraison courante chargée
	 *   @param tournee La tournée courante calculée
	*/
	@Override
	public void calculerTournee(Controleur controleur, Fenetre fenetre, ListeDeCdes listeDeCdes, Plan plan, DemandeLivraison demandeLivraison, GraphePCC graphePCC, Tournee tournee) {
		fenetre.afficheMessage("Calcul d'une tournee.");

		tournee.reset();
		fenetre.effacerVueTournee();

		listeDeCdes.reset();

		graphePCC.initialiserGraphePCC();
		tournee.initialiserGraphePCC(graphePCC);
		tournee.calculerUneTournee(10000, demandeLivraison);
		
		if(tournee.getTempsLimiteAtteint() == true) {
			fenetre.afficheAlerte("Le calcul de la tournee a depasse le temps limite, la tournee n'est donc pas optimale.");
		}
		
		fenetre.initialiserVueTournee();

	}

	
	/** Une fois la tournée calculée, il est possible de supprimer des livraisons de la liste
	 *   @param controleur
	 *   @param fenetre 
	 *   @param listeDeCdes
	 *   @param plan Le plan courant chargé
	 *   @param demandeLivraison La demande de livraison courante chargée
	 *   @param livraison La livraison à supprimer
	 *   @param tournee La tournée courante calculée
	*/
	public void genererFeuilleRoute(Controleur c, Fenetre f, Tournee tournee) {
		tournee.genererFeuilleRoute();
	}


	@Override
	public void supprimerLivraison(Controleur controleur, Fenetre fenetre, ListeDeCdes listeDeCdes, Plan plan, DemandeLivraison demandeLivraison, Livraison livraison, Tournee tournee) {

		fenetre.afficheMessage("Suppression d'une livraison et calcul d'une nouvelle tournee.");

		listeDeCdes.ajoute(new CdeSuppressionLivraison(fenetre, demandeLivraison, livraison, tournee, null, null));

		fenetre.effacerVueTournee();
		fenetre.initialiserVueTournee();


	}
	
	@Override
	public void ajouterLivraison(Controleur controleur, Fenetre fenetre, ListeDeCdes listeDeCdes, Plan plan, DemandeLivraison demandeLivraison, Livraison livraison, Tournee tournee, Pair<Integer, Intersection> interAvantEnlevement, Pair<Integer, Intersection> interAvantDepot) {
		
		fenetre.afficheMessage("Ajout d'une livraison et calcul d'une nouvelle tournee.");
		
		listeDeCdes.ajoute(new CdeInverse(new CdeSuppressionLivraison(fenetre, demandeLivraison, livraison, tournee, interAvantEnlevement, interAvantDepot)));
		
		fenetre.effacerVueTournee();
		fenetre.initialiserVueTournee();
	}
	
	@Override
	public void undo(Fenetre fenetre, ListeDeCdes listeDeCdes) {
		listeDeCdes.undo();

		fenetre.effacerVueTournee();
		fenetre.initialiserVueTournee();
	}


	@Override
	public void redo(Fenetre fenetre, ListeDeCdes listeDeCdes) {
		listeDeCdes.redo();

		fenetre.effacerVueTournee();
		fenetre.initialiserVueTournee();


	}


}
