package Modele;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import XML.ExceptionXml;
import XML.LectureXml;

public class Main {
	public static void main (String[] args) throws IOException, NumberFormatException, ParserConfigurationException, SAXException, ExceptionXml{
		
		LectureXml l = new LectureXml();
		
		
		Plan p = l.creerPlan();
		
		
		DemandeLivraison d = l.creerDemandeDeLivraison(p);
		
	}
}