package controleur;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import modele.DemandeLivraison;
import modele.Livraison;
import modele.Plan;
import vue.Fenetre;
import xml.ExceptionXml;
import xml.LectureXml;

public class EtatTourneeCalculee implements Etat {
	// Etat initial 
	
	
	@Override
	public void chargerPlan(Controleur controleur, Fenetre fenetre, Plan plan, DemandeLivraison demandeLivraison) {
		
		fenetre.afficheMessage("Chargement d'un plan : Veuillez saisir le fichier XML représentant le plan.");
		
		try {
			LectureXml.creerPlan(plan);
			
			demandeLivraison.reset();
			fenetre.effacerVuePlan();
			fenetre.effacerVueDemandeLivraison();
			
			//Effacer la tournee et la vue tournee
			
			fenetre.initialiserVuePlan();
			controleur.setEtatCourant(controleur.etatPlanCharge);
		
		} catch (IOException | ParserConfigurationException | SAXException | NumberFormatException | ExceptionXml e) {
			fenetre.afficheMessage(e.getMessage());
		}
	}
	
	
	@Override
	public void chargerDemandeLivraison(Controleur controleur, Fenetre fenetre, Plan plan, DemandeLivraison demandeLivraison) {
		
		fenetre.afficheMessage("Chargement d'une demande de livraison : Veuillez saisir le fichier XML représentant la demande de livraison.");
		
		try {
			LectureXml.creerDemandeDeLivraison(plan, demandeLivraison);
			
			fenetre.effacerVueDemandeLivraison();
			
			//Effacer la tournee et la vue tournee
			
			fenetre.initialiserVueDemandeLivraison();
			controleur.setEtatCourant(controleur.etatDemandeLivraisonCharge);
		
		} catch (IOException | ParserConfigurationException | SAXException | NumberFormatException | ExceptionXml e) {
			fenetre.afficheMessage(e.getMessage());
		}
	}
	
	
	@Override
	public void calculerTournee(Controleur c, Fenetre f, Plan plan, DemandeLivraison demandeLivraison) {
		
		
	}
	
	
	@Override
	public void supprimerLivraison(Controleur controleur, Fenetre fenetre, Plan plan, DemandeLivraison demandeLivraison, Livraison livraison) {
		
		fenetre.afficheMessage("Suppression d'une livraison et calcul d'une nouvelle tournee.");
		
		demandeLivraison.supprimerLivraison(livraison);
		fenetre.effacerVueDemandeLivraison();
		fenetre.initialiserVueDemandeLivraison();
		
		//Recalculer tournee
	}
	
	
}