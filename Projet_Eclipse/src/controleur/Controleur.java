package controleur;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import modele.DemandeLivraison;
import modele.GraphePCC;
import modele.Intersection;
import modele.Plan;
import modele.Trajet;
import xml.ExceptionXml;
import xml.LectureXml;
import vue.Fenetre;

public class Controleur {
	
	private Fenetre fenetre;
	private LectureXml l;
	private Plan plan;
	private DemandeLivraison demandeLivraison;
	private GraphePCC graphePCC;
	
	public Controleur(int echelle)
	{
		l = new LectureXml();
		//etatCourant = etatInit;
		plan = new Plan();
		demandeLivraison = new DemandeLivraison();
		
		fenetre = new Fenetre(plan, demandeLivraison, echelle, this);
	}


	public void chargerPlan() throws IOException, NumberFormatException, ParserConfigurationException, SAXException, ExceptionXml {
		demandeLivraison.reset();
		l.creerPlan(plan);
		
		fenetre.initialiserVuePlan();
	}
	
	
	public void chargerDemandeLivraison() throws Exception{
		
		demandeLivraison.reset();
		l.creerDemandeDeLivraison(plan, demandeLivraison);
		
		fenetre.initialiserVueDemandeLivraison();
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
}