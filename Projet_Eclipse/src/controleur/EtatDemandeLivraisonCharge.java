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

/** Etat a la suite d'un chargement de demande de livraison reussi
*/
public class EtatDemandeLivraisonCharge implements Etat {
	// Etat initial 
	
	/** Une fois la demande de livraison chargée, il est possible de charger un plan différent
	 *   @param controleur
	 *   @param fenetre 
	 *   @param listeDeCdes
	 *   @param plan Le plan courant chargé
	 *   @param demandeLivraison La demande de livraison courante chargée
	 *   @param tournee La tournée courante, peut être vide
	*/
	@Override
	public void chargerPlan(Controleur controleur, Fenetre fenetre, ListeDeCdes listeDeCdes, Plan plan, DemandeLivraison demandeLivraison, Tournee tournee) {

		fenetre.afficheMessage("Chargement d'un plan : Veuillez saisir le fichier XML reprï¿½sentant le plan.");

		try {

			LectureXml.creerPlan(plan);

			demandeLivraison.reset();
			fenetre.effacerVuePlan();
			fenetre.effacerVueDemandeLivraison();

			listeDeCdes.reset();

			fenetre.initialiserVuePlan();
			controleur.setEtatCourant(controleur.etatPlanCharge);

		} catch (IOException | ParserConfigurationException | SAXException | NumberFormatException | ExceptionXml e) {
			fenetre.afficheAlerte(e.getMessage());
		}
	}
	
	
	/** Une fois la demande de livraison chargée, il est possible de charger une demande différente
	 *   @param controleur
	 *   @param fenetre 
	 *   @param listeDeCdes
	 *   @param plan Le plan courant chargé
	 *   @param demandeLivraison La demande de livraison courante chargée
	 *   @param tournee La tournée courante, peut être vide
	*/
	@Override
	public void chargerDemandeLivraison(Controleur controleur, Fenetre fenetre, ListeDeCdes listeDeCdes, Plan plan, DemandeLivraison demandeLivraison, Tournee tournee) {

		fenetre.afficheMessage("Chargement d'une demande de livraison : Veuillez saisir le fichier XML reprï¿½sentant la demande de livraison.");

		try {
			LectureXml.creerDemandeDeLivraison(plan, demandeLivraison);

			fenetre.effacerVueDemandeLivraison();

			listeDeCdes.reset();

			fenetre.initialiserVueDemandeLivraison();

		} catch (IOException | ParserConfigurationException | SAXException | NumberFormatException | ExceptionXml e) {
			fenetre.afficheAlerte(e.getMessage());
		}
	}

	
	/** Une fois la demande de livraison chargée, il est possible de calculer la tournée 
	 *   @param controleur
	 *   @param fenetre 
	 *   @param listeDeCdes
	 *   @param plan Le plan courant chargé
	 *   @param demandeLivraison La demande de livraison courante chargée
	 *   @param tournee La tournée courante, peut être vide
	*/
	@Override
	public void calculerTournee(Controleur controleur, Fenetre fenetre, ListeDeCdes listeDeCdes, Plan plan, DemandeLivraison demandeLivraison, GraphePCC graphePCC, Tournee tournee) {

		fenetre.afficheMessage("Calcul d'une tournee.");

		graphePCC.initialiserGraphePCC();
		tournee.initialiserGraphePCC(graphePCC);
		tournee.calculerUneTournee(10000, demandeLivraison);
		
		if(tournee.getTempsLimiteAtteint() == true) {
			fenetre.afficheAlerte("Le calcul de la tournee a depasse le temps limite, la tournee n'est donc pas optimale.");
		}
		
		listeDeCdes.reset();

		fenetre.initialiserVueTournee();
		controleur.setEtatCourant(controleur.etatTourneeCalculee);
	}

	
	/** Une fois la demande de livraison chargée, il est possible de supprimer des livraisons de la liste
	 *   @param controleur
	 *   @param fenetre 
	 *   @param listeDeCdes
	 *   @param plan Le plan courant chargé
	 *   @param demandeLivraison La demande de livraison courante chargée
	 *   @param livraison La livraison à supprimer
	 *   @param tournee La tournée courante, peut être vide
	*/
	@Override
	public void supprimerLivraison(Controleur controleur, Fenetre fenetre, ListeDeCdes listeDeCdes, Plan plan, DemandeLivraison demandeLivraison, Livraison livraison, Tournee tournee) {

		fenetre.afficheMessage("Suppression d'une livraison.");

		listeDeCdes.ajoute(new CdeSuppressionLivraison(fenetre, demandeLivraison, livraison, tournee, null, null));

	}

	@Override
	public void ajouterLivraison(Controleur controleur, Fenetre fenetre, ListeDeCdes listeDeCdes, Plan plan, DemandeLivraison demandeLivraison, Livraison livraison, Tournee tournee, Pair<Integer, Intersection> interAvantEnlevement, Pair<Integer, Intersection> interAvantDepot) {
		
		fenetre.afficheMessage("Ajout d'une livraison.");
		
		listeDeCdes.ajoute(new CdeInverse(new CdeSuppressionLivraison(fenetre, demandeLivraison, livraison, tournee, interAvantEnlevement, interAvantDepot)));
	}
	
	@Override
	public void undo(Fenetre fenetre, ListeDeCdes listeDeCdes) {
		listeDeCdes.undo();

	}


	@Override
	public void redo(Fenetre fenetre, ListeDeCdes listeDeCdes) {
		listeDeCdes.redo();

	}


}
