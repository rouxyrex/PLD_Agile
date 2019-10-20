package Modele;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import XML.ExceptionXml;
import XML.LectureXml;

public class Main {
	public static void main (String[] args) throws IOException, NumberFormatException, ParserConfigurationException, SAXException, ExceptionXml{
		
		LectureXml l = new LectureXml();
		
		String cheminPlan = "C:\\\\Users\\\\So&So\\\\Desktop\\\\Solal\\\\INSA 4IF\\\\AGILE\\\\Fichiers XML\\\\fichiersXML2019\\\\petitPlan.xml";
		
		Plan p = l.creerPlan(cheminPlan);
		
		String cheminDemandeLivraison = "C:\\\\Users\\\\So&So\\\\Desktop\\\\Solal\\\\INSA 4IF\\\\AGILE\\\\Fichiers XML\\\\fichiersXML2019\\\\demandePetit2.xml";
		
		DemandeLivraison d = l.creerDemandeDeLivraison(cheminDemandeLivraison, p);
		
	}
}