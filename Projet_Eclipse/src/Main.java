
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import controleur.Controleur;
import xml.ExceptionXml;


public class Main {
	public static void main (String[] args) throws IOException, NumberFormatException, ParserConfigurationException, SAXException, ExceptionXml{
		
		Controleur c = new Controleur(1);
	}
}