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

/** Etat initial
*/
public class EtatInit implements Etat {

	/** A l'etat initial, il est uniquement possible de charger un plan
	 *   @param controleur
	 *   @param fenetre 
	 *   @param listeDeCdes
	 *   @param plan Le plan courant, vide
	 *   @param demandeLivraison La demande de livraison courante, vide
	 *   @param tournee La tournée courante, vide
	*/
	@Override
	public void chargerPlan(Controleur controleur, Fenetre fenetre, ListeDeCdes listeDeCdes, Plan plan, DemandeLivraison demandeLivraison, Tournee tournee) {
		
		fenetre.afficheMessage("Chargement d'un plan : Veuillez saisir le fichier XML representant le plan.");
		
		try {
			LectureXml.creerPlan(plan);
		
			fenetre.initialiserVuePlan();
		
			controleur.setEtatCourant(controleur.etatPlanCharge);
		
		} catch (IOException | ParserConfigurationException | SAXException | NumberFormatException | ExceptionXml e) {
			fenetre.afficheAlerte(e.getMessage());
		}
	}

	
}