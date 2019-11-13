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
		
		fenetre.afficheMessage("Chargement d'un plan : Veuillez saisir le fichier XML représentant le plan.");
		
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
			fenetre.afficheMessage(e.getMessage());
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
		
		fenetre.afficheMessage("Chargement d'une demande de livraison : Veuillez saisir le fichier XML représentant la demande de livraison.");
		
		try {
			LectureXml.creerDemandeDeLivraison(plan, demandeLivraison);
			
			fenetre.effacerVueDemandeLivraison();
			
			tournee.reset();
			fenetre.effacerVueTournee();
			
			listeDeCdes.reset();
			
			fenetre.initialiserVueDemandeLivraison();
			controleur.setEtatCourant(controleur.etatDemandeLivraisonCharge);
		
		} catch (IOException | ParserConfigurationException | SAXException | NumberFormatException | ExceptionXml e) {
			fenetre.afficheMessage(e.getMessage());
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
		tournee.calculerUneTournee(1000000, demandeLivraison);
		
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
	@Override
	public void supprimerLivraison(Controleur controleur, Fenetre fenetre, ListeDeCdes listeDeCdes, Plan plan, DemandeLivraison demandeLivraison, Livraison livraison, Tournee tournee) {
		
		fenetre.afficheMessage("Suppression d'une livraison et calcul d'une nouvelle tournee.");
		
		listeDeCdes.ajoute(new CdeSuppressionLivraison(demandeLivraison, livraison, tournee));
		
		fenetre.effacerVueDemandeLivraison();
		fenetre.initialiserVueDemandeLivraison();
		fenetre.effacerVueTournee();
		fenetre.initialiserVueTournee();
		
		
	}
	
	
	@Override
	public void undo(Fenetre fenetre, ListeDeCdes listeDeCdes) {
		listeDeCdes.undo();
		
		fenetre.effacerVueDemandeLivraison();
		fenetre.initialiserVueDemandeLivraison();
		
		fenetre.effacerVueTournee();
		fenetre.initialiserVueTournee();
	}
	
	
	@Override
	public void redo(Fenetre fenetre, ListeDeCdes listeDeCdes) {
		listeDeCdes.redo();
		
		fenetre.effacerVueDemandeLivraison();
		fenetre.initialiserVueDemandeLivraison();
		
		fenetre.effacerVueTournee();
		fenetre.initialiserVueTournee();
		
		
	}
	
	
}