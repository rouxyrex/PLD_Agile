package xml;

import modele.Plan;
import modele.DemandeLivraison;
import modele.Intersection;
import modele.Livraison;
import modele.Troncon;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


public class LectureXml {
	
	public LectureXml() {}
	
	public Plan creerPlan() throws IOException, ParserConfigurationException, SAXException, NumberFormatException, ExceptionXml{
		
		Plan plan;
		File xml = OuvreurDeFichierXml.getInstance().ouvre(true);
	    DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();	
	    Document document = docBuilder.parse(xml);
	    Element racine = document.getDocumentElement();
	    if (racine.getNodeName().equals("reseau")) {
	       plan = construirePlanAPartirDeDOMXML(racine);
	    }
	    else {
	        throw new ExceptionXml("Document de plan non conforme");
	    }
	    
	    return plan;
	}
	
public Plan creerPlan( File xml) throws IOException, ParserConfigurationException, SAXException, NumberFormatException, ExceptionXml{
		
		Plan plan;
	    DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();	
	    Document document = docBuilder.parse(xml);
	    Element racine = document.getDocumentElement();
	    if (racine.getNodeName().equals("reseau")) {
	       plan = construirePlanAPartirDeDOMXML(racine);
	    }
	    else {
	        throw new ExceptionXml("Document de plan non conforme");
	    }
	    
	    return plan;
	}
	
	
	private static Plan construirePlanAPartirDeDOMXML(Element noeudDOMRacine) throws ExceptionXml, NumberFormatException{
	    	
		Map<String, Intersection> intersections = new HashMap<String, Intersection>();
		
		float latitudeMax = 0;
		float latitudeMin = 0;
		float longitudeMax = 0;
		float longitudeMin = 0;
		
		float latitudeTemp;
		float longitudeTemp;
		
	    NodeList listeNoeuds = noeudDOMRacine.getElementsByTagName("noeud");
	    
	    if( listeNoeuds.getLength() < 2) {
	    	throw new ExceptionXml("Un plan doit contenir au moins 2 intersections.");
	    }
	    ArrayList<String> listenoeud = new ArrayList<String>();
		boolean verifierDoublonsIntersection = false;
		for(int i=0; i<listeNoeuds.getLength(); i++) {
			listenoeud.add(listeNoeuds.item(i).getAttributes().getNamedItem("id").getNodeValue());
		}
		for(int i=0; i<listeNoeuds.getLength(); i++) {
			if(listenoeud.indexOf(listenoeud.get(i)) != listenoeud.lastIndexOf(listenoeud.get(i))) {
				verifierDoublonsIntersection = true;
				break ; 
			}
			
		}
		if(verifierDoublonsIntersection==true)
		{
			throw new ExceptionXml("L'Id doit être unique.") ;
		}
	    
	    for (int i = 0; i < listeNoeuds.getLength(); i++) {
	       		
	        Intersection inter = creerIntersection((Element) listeNoeuds.item(i));
			intersections.put(inter.getId(), inter);
			
			if(i==0) {
				latitudeMax = inter.getLatitude();
				latitudeMin = inter.getLatitude();
				longitudeMax = inter.getLongitude();
				longitudeMin = inter.getLongitude();
			}
			else {
				latitudeTemp = inter.getLatitude();
				longitudeTemp = inter.getLongitude();
				
				if(latitudeTemp<latitudeMin) {
					latitudeMin = latitudeTemp;
				}
				else if(latitudeTemp>latitudeMax) {
					latitudeMax = latitudeTemp;
				}
				
				if(longitudeTemp<longitudeMin) {
					longitudeMin = longitudeTemp;
				}
				else if(longitudeTemp>longitudeMax) {
					longitudeMax = longitudeTemp;
				}
			}
				
	    }
	    
	    if( intersections.size() < 2) {
	    	throw new ExceptionXml("Un plan doit contenir au moins 2 intersections distinctes.");
	    }
	       	
	    List<Troncon> troncons = new ArrayList<Troncon>();
	       	
	    NodeList listeTroncons = noeudDOMRacine.getElementsByTagName("troncon");
	    
	    if( listeTroncons.getLength() < 1) {
	    	throw new ExceptionXml("Un plan doit contenir au moins 1 troncon.");
	    }
	    
	    for (int i = 0; i < listeTroncons.getLength(); i++) {
	       		
			Troncon t = creerTroncon((Element) listeTroncons.item(i), intersections);
			troncons.add(t);
	       		
	    }
	      
	boolean testOrigineEgaleDestination = true ; 
		for(int i=0; i<listeTroncons.getLength(); i++) {
			if(Long.parseLong(listeTroncons.item(i).getAttributes().getNamedItem("destination").getNodeValue())==Long.parseLong(listeTroncons.item(i).getAttributes().getNamedItem("origine").getNodeValue()))
			{
				testOrigineEgaleDestination = false ;
				break ;  
			}
		}
		if(testOrigineEgaleDestination==false)
		{
			throw new ExceptionXml("l'origine est égale à la destination");
		}
		
		
		
		ArrayList<String> listetroncon = new ArrayList<String>();
		boolean verifierDoublonsTroncon = false;
		for(int i=0; i<listeTroncons.getLength(); i++) {
			String nomTroncon = "";
			nomTroncon = listeTroncons.item(i).getAttributes().getNamedItem("nomRue").getNodeValue().concat(listeTroncons.item(i).getAttributes().getNamedItem("origine").getNodeValue()).concat(listeTroncons.item(i).getAttributes().getNamedItem("destination").getNodeValue().concat(listeTroncons.item(i).getAttributes().getNamedItem("longueur").getNodeValue()));
			listetroncon.add(nomTroncon);
		}
		for(int i=0; i<listeTroncons.getLength(); i++) {
			if(listetroncon.indexOf(listetroncon.get(i)) != listetroncon.lastIndexOf(listetroncon.get(i))) {
				verifierDoublonsTroncon = true;
				break;
			}
		}
		if(verifierDoublonsTroncon==true)
		{
			throw new ExceptionXml("il y a au moins un doublon dans le tronçon ");
		}
			
	    Plan plan = new Plan(intersections, troncons);
	    
	    plan.setLatitudeMax(latitudeMax);
	    plan.setLatitudeMin(latitudeMin);
	    plan.setLongitudeMax(longitudeMax);
	    plan.setLongitudeMin(longitudeMin);
	    
	    return plan;
	}
	
	
	private static Intersection creerIntersection(Element elt) throws ExceptionXml{
	    	
	    String id = elt.getAttribute("id");
	   	float latitude = Float.parseFloat(elt.getAttribute("latitude"));
	   	float longitude = Float.parseFloat(elt.getAttribute("longitude"));
	   	
	   	if( (latitude <= - 90) || (latitude >= 90) ) {
	   		throw new ExceptionXml("Une intersection a une latitude non comprise entre -90 et 90.");
	   	}
	   	
	   	if( (longitude <= - 180) || (longitude >= 180) ) {
	   		throw new ExceptionXml("Une intersection a une longitude non comprise entre -180 et 180.");
	   	}
	   	
	   	//System.out.println("Intersection: longitude= "+longitude+" latitude= "+latitude+" id= "+id);
	   	
	   	Intersection inter = new Intersection(id, latitude, longitude);
	   		
	   	return inter;
	}
	    
	    
	private static Troncon creerTroncon(Element elt, Map<String, Intersection> intersections) throws ExceptionXml{
	    
	   	String idOrigine = elt.getAttribute("origine");
	   	String idDestination = elt.getAttribute("destination");
	   	float longueur = Float.parseFloat(elt.getAttribute("longueur"));
	   	String nomRue = elt.getAttribute("nomRue");
	   	
	   	if( longueur <= 0 ) {
	   		throw new ExceptionXml("Un troncon a une longueur négative ou nulle.");
	   	}
	   	
	   	//System.out.println("Troncon: origine= "+idOrigine+" nomRue= "+nomRue+" longueur= "+longueur+" destination= "+idDestination);
	   	
	   	Intersection interOrigine = intersections.get(idOrigine);
	   	
	   	if(interOrigine == null) {
	   		throw new ExceptionXml("L'intersection origine du troncon \""+nomRue+"\" n'existe pas dans la liste des intersections.");
	   	}
	   	
	   	Intersection interDestination = intersections.get(idDestination);
	   	
	   	if(interDestination == null) {
	   		throw new ExceptionXml("L'intersection destination du troncon \""+nomRue+"\" n'existe pas dans la liste des intersections.");
	   	}
	   	
	   	Troncon t = new Troncon(interOrigine, interDestination, longueur, nomRue);
	   	
	   	interOrigine.addTronconVoisin(t);
	   	
	   	return t;
	}
	
	
	public DemandeLivraison creerDemandeDeLivraison(Plan plan) throws IOException, ParserConfigurationException, SAXException, NumberFormatException, ExceptionXml{
		
		DemandeLivraison demande;
		File xml = OuvreurDeFichierXml.getInstance().ouvre(true);
	    DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();	
	    Document document = docBuilder.parse(xml);
	    Element racine = document.getDocumentElement();
	    if (racine.getNodeName().equals("demandeDeLivraisons")) {
	    	demande = construireDemandeLivraisonAPartirDeDOMXML(racine, plan);
	    }
	    else {
	        throw new ExceptionXml("Document de demande de livraisons non conforme");
	    }
	    
	    return demande;
	}
	
public DemandeLivraison creerDemandeDeLivraison(Plan plan, File xml) throws IOException, ParserConfigurationException, SAXException, NumberFormatException, ExceptionXml{
		
		DemandeLivraison demande;
	    DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();	
	    Document document = docBuilder.parse(xml);
	    Element racine = document.getDocumentElement();
	    if (racine.getNodeName().equals("demandeDeLivraisons")) {
	    	demande = construireDemandeLivraisonAPartirDeDOMXML(racine, plan);
	    }
	    else {
	        throw new ExceptionXml("Document de demande de livraisons non conforme");
	    }
	    
	    return demande;
	}
	
	
	private static DemandeLivraison construireDemandeLivraisonAPartirDeDOMXML(Element noeudDOMRacine, Plan plan) throws ExceptionXml, NumberFormatException{
	    	
		Intersection entrepot;
		String heureDepart;
			
	    NodeList listeEntrepots = noeudDOMRacine.getElementsByTagName("entrepot");
	    
	    if (listeEntrepots.getLength() != 1) {
	    	throw new ExceptionXml("Le document ne comporte aucun entrepot ou plusieurs.");
	    }
	    
	    Element elt = (Element) listeEntrepots.item(0);
    	
    	String idAdresse = elt.getAttribute("adresse");
    	
	   	heureDepart = elt.getAttribute("heureDepart");
	   	
	   	//Bien faire une copie!
	   	Intersection entrepotACopier = plan.getIntersectionById(idAdresse);
    	
	   	if(entrepotACopier == null) {
	   		throw new ExceptionXml("L'entrepot ne correspond a aucune intersection du plan.");
	   	}
	   	
	   	entrepot = new Intersection(entrepotACopier.getId(), entrepotACopier.getLatitude(), entrepotACopier.getLongitude());
	   	
	   	//System.out.println("Entrepot: heureDepart= "+heureDepart+" adresse= "+idAdresse);
	   	
	    
	    List<Livraison> livraisons = new ArrayList<Livraison>();
	       	
	    NodeList listeLivraisons = noeudDOMRacine.getElementsByTagName("livraison");
	    ArrayList<String> listelivraison = new ArrayList<String>();
		boolean verifierDoublonsLivraison = false;
		for(int i=0; i<listeLivraisons.getLength(); i++) {
			String nomLivraison = "";
			nomLivraison = listeLivraisons.item(i).getAttributes().getNamedItem("adresseLivraison").getNodeValue().concat(listeLivraisons.item(i).getAttributes().getNamedItem("adresseEnlevement").getNodeValue()).concat(listeLivraisons.item(i).getAttributes().getNamedItem("dureeLivraison").getNodeValue().concat(listeLivraisons.item(i).getAttributes().getNamedItem("dureeEnlevement").getNodeValue()));
			listelivraison.add(nomLivraison);
		}
		for(int i=0; i<listeLivraisons.getLength(); i++) {
			if(listelivraison.indexOf(listelivraison.get(i)) != listelivraison.lastIndexOf(listelivraison.get(i))) {
				verifierDoublonsLivraison = true;
				break;
			}
		}
		if(verifierDoublonsLivraison==true)
		{
			throw new ExceptionXml("il y a au moins un doublon dans la livraison ");
		}
	    
	    for (int i = 0; i < listeLivraisons.getLength(); i++) {
	        
		    Livraison l = creerLivraison((Element) listeLivraisons.item(i), plan);
		    livraisons.add(l);
		    
		}
	    	
	    DemandeLivraison demande = new DemandeLivraison(livraisons, entrepot, heureDepart);
	       	
	    return demande;
	}
	
	    
	private static Livraison creerLivraison(Element elt, Plan plan) throws ExceptionXml{
	    
	   	String idEnlevement = elt.getAttribute("adresseEnlevement");
	   	String idDepot = elt.getAttribute("adresseLivraison");
	   	int dureeEnlevement = Integer.parseInt(elt.getAttribute("dureeEnlevement"));
	   	int dureeDepot = Integer.parseInt(elt.getAttribute("dureeLivraison"));
	   	
	   	if( dureeEnlevement < 0 ) {
	   		throw new ExceptionXml("Une duree d'enlevement est negative.");
	   	}
	   	
	   	if( dureeDepot < 0 ) {
	   		throw new ExceptionXml("Une duree de depot est negative.");
	   	}
	   	
	   	//System.out.println("Livraison: dureeLivraison= "+dureeDepot+" dureeEnlevement= "+dureeEnlevement+" adresseLivraison= "+idDepot+" adresseEnlevement= "+idEnlevement);
	   	
	   	Intersection adresseEnlevementACopier = plan.getIntersectionById(idEnlevement);// Tester si null + exception
	   	Intersection adresseDepotACopier = plan.getIntersectionById(idDepot);// Tester si null + exception
	   	
	   	if(adresseEnlevementACopier == null) {
	   		throw new ExceptionXml("Il y a au moins une adresse d'enlevement qui ne correspond a aucune intersection du plan.");
	   	}
	   	
	   	Intersection adresseEnlevement = new Intersection(adresseEnlevementACopier.getId(), adresseEnlevementACopier.getLatitude(), adresseEnlevementACopier.getLongitude());
	    
	   	if(adresseDepotACopier == null) {
	   		throw new ExceptionXml("Il y a au moins une adresse de depot qui ne correspond a aucune intersection du plan.");
	   	}
	   	
	   	Intersection adresseDepot = new Intersection(adresseDepotACopier.getId(), adresseDepotACopier.getLatitude(), adresseDepotACopier.getLongitude());
	   	
	   	Livraison l = new Livraison(adresseEnlevement, adresseDepot, dureeEnlevement, dureeDepot);
	   	
	   	return l;
	}
	
}
