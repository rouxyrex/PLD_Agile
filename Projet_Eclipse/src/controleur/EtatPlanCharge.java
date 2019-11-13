package controleur;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import modele.DemandeLivraison;
import modele.Plan;
import modele.Tournee;
import vue.Fenetre;
import xml.ExceptionXml;
import xml.LectureXml;

/** Etat a la suite d'un chargement de plan reussi
*/
public class EtatPlanCharge implements Etat {
	// Etat initial 
	
	/** Une fois le plan chargé, il est possible de charger un plan différent
	 *   @param controleur
	 *   @param fenetre 
	 *   @param listeDeCdes
	 *   @param plan Le plan courant chargé
	 *   @param demandeLivraison La demande de livraison, vide
	 *   @param tournee La tournée courante, vide
	*/
	@Override
	public void chargerPlan(Controleur controleur, Fenetre fenetre, ListeDeCdes listeDeCdes, Plan plan, DemandeLivraison demandeLivraison, Tournee tournee) {
		
		fenetre.afficheMessage("Chargement d'un plan : Veuillez saisir le fichier XML représentant le plan.");
		
		try {
			LectureXml.creerPlan(plan);
			
			fenetre.effacerVuePlan();
			fenetre.initialiserVuePlan();
		
		} catch (IOException | ParserConfigurationException | SAXException | NumberFormatException | ExceptionXml e) {
			fenetre.afficheMessage(e.getMessage());
		}
	}
	
	/** Une fois le plan chargé, il est possible de charger une demande de livraison
	 *   @param controleur
	 *   @param fenetre 
	 *   @param listeDeCdes
	 *   @param plan Le plan courant chargé
	 *   @param demandeLivraison La demande de livraison, vide
	 *   @param tournee La tournée courante, vide
	*/
	@Override
	public void chargerDemandeLivraison(Controleur controleur, Fenetre fenetre, ListeDeCdes listeDeCdes, Plan plan, DemandeLivraison demandeLivraison, Tournee tournee) {
		
		fenetre.afficheMessage("Chargement d'une demande de livraison : Veuillez saisir le fichier XML représentant la demande de livraison.");
		
		try {
			LectureXml.creerDemandeDeLivraison(plan, demandeLivraison);
			
			fenetre.initialiserVueDemandeLivraison();
			
			controleur.setEtatCourant(controleur.etatDemandeLivraisonCharge);
		
		} catch (IOException | ParserConfigurationException | SAXException | NumberFormatException | ExceptionXml e) {
			fenetre.afficheMessage(e.getMessage());
		}
	}
	
}