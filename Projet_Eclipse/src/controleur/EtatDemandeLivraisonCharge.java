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

/** Etat a la suite d'un chargement de demande de livraison reussi
*/
public class EtatDemandeLivraisonCharge implements Etat {
	// Etat initial 
	
	/** Une fois la demande de livraison charg�e, il est possible de charger un plan diff�rent
	 *   @param controleur
	 *   @param fenetre 
	 *   @param listeDeCdes
	 *   @param plan Le plan courant charg�
	 *   @param demandeLivraison La demande de livraison courante charg�e
	 *   @param tournee La tourn�e courante, peut �tre vide
	*/
	@Override
	public void chargerPlan(Controleur controleur, Fenetre fenetre, ListeDeCdes listeDeCdes, Plan plan, DemandeLivraison demandeLivraison, Tournee tournee) {
		
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
			fenetre.afficheMessage(e.getMessage());
		}
	}
	
	/** Une fois la demande de livraison charg�e, il est possible de charger une demande diff�rente
	 *   @param controleur
	 *   @param fenetre 
	 *   @param listeDeCdes
	 *   @param plan Le plan courant charg�
	 *   @param demandeLivraison La demande de livraison courante charg�e
	 *   @param tournee La tourn�e courante, peut �tre vide
	*/
	@Override
	public void chargerDemandeLivraison(Controleur controleur, Fenetre fenetre, ListeDeCdes listeDeCdes, Plan plan, DemandeLivraison demandeLivraison, Tournee tournee) {
		
		fenetre.afficheMessage("Chargement d'une demande de livraison : Veuillez saisir le fichier XML repr�sentant la demande de livraison.");
		
		try {
			LectureXml.creerDemandeDeLivraison(plan, demandeLivraison);
			
			fenetre.effacerVueDemandeLivraison();
			
			listeDeCdes.reset();
			
			fenetre.initialiserVueDemandeLivraison();
			
		} catch (IOException | ParserConfigurationException | SAXException | NumberFormatException | ExceptionXml e) {
			fenetre.afficheMessage(e.getMessage());
		}
	}
	
	/** Une fois la demande de livraison charg�e, il est possible de calculer la tourn�e 
	 *   @param controleur
	 *   @param fenetre 
	 *   @param listeDeCdes
	 *   @param plan Le plan courant charg�
	 *   @param demandeLivraison La demande de livraison courante charg�e
	 *   @param tournee La tourn�e courante, peut �tre vide
	*/
	@Override
	public void calculerTournee(Controleur controleur, Fenetre fenetre, ListeDeCdes listeDeCdes, Plan plan, DemandeLivraison demandeLivraison, GraphePCC graphePCC, Tournee tournee) {
		
		fenetre.afficheMessage("Calcul d'une tournee.");
		
		graphePCC.initialiserGraphePCC();
		tournee.initialiserGraphePCC(graphePCC);
		tournee.calculerUneTournee(1000000, demandeLivraison);
		
		listeDeCdes.reset();
		
		fenetre.initialiserVueTournee();
		controleur.setEtatCourant(controleur.etatTourneeCalculee);
	}
	
	/** Une fois la demande de livraison charg�e, il est possible de supprimer des livraisons de la liste
	 *   @param controleur
	 *   @param fenetre 
	 *   @param listeDeCdes
	 *   @param plan Le plan courant charg�
	 *   @param demandeLivraison La demande de livraison courante charg�e
	 *   @param livraison La livraison � supprimer
	 *   @param tournee La tourn�e courante, peut �tre vide
	*/
	@Override
	public void supprimerLivraison(Controleur controleur, Fenetre fenetre, ListeDeCdes listeDeCdes, Plan plan, DemandeLivraison demandeLivraison, Livraison livraison, Tournee tournee) {
		
		fenetre.afficheMessage("Suppression d'une livraison.");
		
		listeDeCdes.ajoute(new CdeSuppressionLivraison(demandeLivraison, livraison, tournee));
		
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