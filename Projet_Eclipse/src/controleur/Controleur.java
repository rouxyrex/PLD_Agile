package controleur;

import java.io.IOException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import modele.DemandeLivraison;
import modele.Plan;
import modele.Tournee;
import modele.Trajet;
import xml.ExceptionXml;
import xml.LectureXml;
import vue.Fenetre;

public class Controleur {
	
	private Plan plan;
	private Fenetre fenetre;
	private LectureXml l;
	private Tournee tournee;
	
	public Controleur( int echelle)
	{
		l = new LectureXml();
		//etatCourant = etatInit;
		fenetre = new Fenetre(plan, echelle, this);
	}


	public void chargerPlan() throws Exception{ 
		plan = l.creerPlan(); 
		fenetre.passerPlan(plan); 
	}
	
	
	public void chargerDemandeLivraison() throws Exception{
		
		DemandeLivraison dl = l.creerDemandeDeLivraison(plan); 
		fenetre.afficherDemandeLivraison(dl);
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
}