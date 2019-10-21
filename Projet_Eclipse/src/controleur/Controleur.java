package controleur;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import Modele.Plan;
import XML.ExceptionXml;
import XML.LectureXml;
import vue.Fenetre;

public class Controleur {
	
	
	
	private Plan plan;
	private Fenetre fenetre;
	private LectureXml l;
	
	public Controleur( int echelle)
	{
		l = new LectureXml();
		//etatCourant = etatInit;
		try {
			this.plan = l.creerPlan("grandPlan.xml");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fenetre = new Fenetre(plan, echelle, this);
	}


	public void chargerPlan() throws IOException, NumberFormatException, ParserConfigurationException, SAXException, ExceptionXml {


		String cheminPlan = "\\\\servif-home\\homes\\alafaille\\TÚlÚchargements\\fichiersXML2019\\fichiersXML2019\\petitPlan.xml";
		
		Plan p = l.creerPlan("petitPlan.xml");
		//fenetre = new Fenetre(p, 1, this);
		//fenetre.passerPlan(p);
		
		
	}
}