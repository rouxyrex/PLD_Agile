package controleur;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import modele.DemandeLivraison;
import modele.GraphePCC;
import modele.Livraison;
import modele.Plan;
import modele.Tournee;
import vue.Fenetre;
import xml.ExceptionXml;
import xml.LectureXml;

public class EtatDemandeLivraisonCharge implements Etat {
	// Etat initial


	@Override
	public void chargerPlan(Controleur controleur, Fenetre fenetre, ListeDeCdes listeDeCdes, Plan plan, DemandeLivraison demandeLivraison) {

		fenetre.afficheMessage("Chargement d'un plan : Veuillez saisir le fichier XML repr�sentant le plan.");

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


	@Override
	public void chargerDemandeLivraison(Controleur controleur, Fenetre fenetre, ListeDeCdes listeDeCdes, Plan plan, DemandeLivraison demandeLivraison) {

		fenetre.afficheMessage("Chargement d'une demande de livraison : Veuillez saisir le fichier XML repr�sentant la demande de livraison.");

		try {
			LectureXml.creerDemandeDeLivraison(plan, demandeLivraison);

			fenetre.effacerVueDemandeLivraison();

			listeDeCdes.reset();

			fenetre.initialiserVueDemandeLivraison();

		} catch (IOException | ParserConfigurationException | SAXException | NumberFormatException | ExceptionXml e) {
			fenetre.afficheAlerte(e.getMessage());
		}
	}


	@Override
	public void calculerTournee(Controleur controleur, Fenetre fenetre, Plan plan, DemandeLivraison demandeLivraison, GraphePCC graphePCC, Tournee tournee) {

		fenetre.afficheMessage("Calcul d'une tournee.");

		graphePCC.initialiserGraphePCC(plan, demandeLivraison);
		tournee.initialiserGraphePCC(graphePCC);
		tournee.CalculerTournee(10000, graphePCC, demandeLivraison);
		c.setEtatCourant(c.etatTourneeCalculee);
		fenetre.initialiserVueTournee();
	}


	@Override
	public void supprimerLivraison(Controleur controleur, Fenetre fenetre, ListeDeCdes listeDeCdes, Plan plan, DemandeLivraison demandeLivraison, Livraison livraison) {

		fenetre.afficheMessage("Suppression d'une livraison.");

		listeDeCdes.ajoute(new CdeInverse(new CdeAjoutLivraison(demandeLivraison, livraison)));

		fenetre.effacerVueDemandeLivraison();
		fenetre.initialiserVueDemandeLivraison();

	}


	@Override
	public void undo(Fenetre fenetre, ListeDeCdes listeDeCdes) {
		listeDeCdes.undo();

		fenetre.effacerVueDemandeLivraison();
		fenetre.initialiserVueDemandeLivraison();
	}


	@Override
	public void redo(Fenetre fenetre, ListeDeCdes listeDeCdes) {
		listeDeCdes.redo();

		fenetre.effacerVueDemandeLivraison();
		fenetre.initialiserVueDemandeLivraison();
	}


}
