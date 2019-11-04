package controleur;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import modele.DemandeLivraison;
import modele.Plan;
import xml.ExceptionXml;
import xml.LectureXml;
import vue.Fenetre;

public class Controleur {
	
	private Plan plan;
	private Fenetre fenetre;
	private LectureXml lecture;
	private DemandeLivraison dl;
	
	public Controleur( int echelle)
	{
		lecture = new LectureXml();
		//etatCourant = etatInit;
		fenetre = new Fenetre(plan, echelle, this);
	}


	public void chargerPlan() throws IOException, NumberFormatException, ParserConfigurationException, SAXException, ExceptionXml {


		String cheminPlan = "\\\\servif-home\\homes\\alafaille\\Téléchargements\\fichiersXML2019\\fichiersXML2019\\petitPlan.xml";
		
		plan = lecture.creerPlan();
		
		fenetre.passerPlan(plan);
		
		
	}
	
	
	public void chargerDemandeLivraison() throws Exception{
		
		dl = lecture.creerDemandeDeLivraison(plan);
		
		fenetre.afficherDemandeLivraison(dl, this);
	}
	
	public int getNbLivraisons() {
		
		return dl.getNbLivraisons();
	}
}