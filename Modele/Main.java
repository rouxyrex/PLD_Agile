package Modele;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import XML.ExceptionXml;
import XML.LectureXml;
import controleur.Controleur;

public class Main {
	public static void main (String[] args) throws IOException, NumberFormatException, ParserConfigurationException, SAXException, ExceptionXml{
		
		//LectureXml l = new LectureXml();
		
		//String cheminPlan = "\\\\servif-home\\homes\\alafaille\\Téléchargements\\fichiersXML2019\\fichiersXML2019\\petitPlan.xml";
		
		//Plan p = l.creerPlan("petitPlan.xml");
		
		//String cheminDemandeLivraison = "\\\\servif-home\\homes\\sthoniel\\Mes documents\\4IF\\PLD_Agile\\XML_Files\\demandePetit2.xml";
		
		//DemandeLivraison d = l.creerDemandeDeLivraison(cheminDemandeLivraison, p);
		
		Controleur c = new Controleur(1);
	}
}