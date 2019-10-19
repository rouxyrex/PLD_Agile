package xml;


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import plan.Intersection;
import plan.Plan;
import plan.Troncon;

public class LectureXml {
	
	public LectureXml() {}
	
	public Plan creerPlan(String path) throws IOException{
		
		InputStream flux;
		
		Plan plan = null;
		
		try {
			flux = new FileInputStream(path);
			InputStreamReader lecture=new InputStreamReader(flux);
			BufferedReader buff=new BufferedReader(lecture);
			String ligne;
			float longitudeMax = -181;
			float longitudeMin = 181;
			float lattitudeMax = -91;
			float lattitudeMin = 91;
			
			Map<String, Intersection> intersections = new HashMap<String, Intersection>();
			List<Troncon> troncons = new ArrayList<Troncon>();
			
			while ((ligne=buff.readLine())!=null){ 
				if(ligne.indexOf("noeud") != -1) {
					int recherche = ligne.indexOf('"')+1;
					String id = (String) ligne.subSequence(recherche, ligne.indexOf('"', recherche));
					recherche = ligne.indexOf('"', recherche) +1;
					recherche = ligne.indexOf('"', recherche) +1;
					float lattitude = Float.parseFloat((String) ligne.subSequence(recherche, ligne.indexOf('"', recherche)));
					recherche = ligne.indexOf('"', recherche) +1;
					recherche = ligne.indexOf('"', recherche) +1;
					float longitude = Float.parseFloat((String) ligne.subSequence(recherche, ligne.indexOf('"', recherche)));
					
			/*		System.out.println(id);
					System.out.println(lattitude);
					System.out.println(longitude);*/
					if(lattitude < lattitudeMin) lattitudeMin = lattitude;
					if(lattitude > lattitudeMax) lattitudeMax = lattitude;
					if(longitude < longitudeMin) longitudeMin = longitude;
					if(longitude > longitudeMax) longitudeMax = longitude;
					
					Intersection inter = new Intersection(id, lattitude, longitude);
					intersections.put(id, inter);
					
				}else if(ligne.indexOf("troncon") != -1) {
			//		System.out.println(ligne);
					int recherche = ligne.indexOf('"')+1;
					String idDest = (String) ligne.subSequence(recherche, ligne.indexOf('"', recherche));
					recherche = ligne.indexOf('"', recherche) +1;
					recherche = ligne.indexOf('"', recherche) +1;
					float longueur = Float.parseFloat((String) ligne.subSequence(recherche, ligne.indexOf('"', recherche)));
					recherche = ligne.indexOf('"', recherche) +1;
					recherche = ligne.indexOf('"', recherche) +1;
					String nomRue = (String) ligne.subSequence(recherche, ligne.indexOf('"', recherche));
					recherche = ligne.indexOf('"', recherche) +1;
					recherche = ligne.indexOf('"', recherche) +1;
					String idOrginie = (String) ligne.subSequence(recherche, ligne.indexOf('"', recherche));
					
					System.out.println(idDest);
					System.out.println(longueur);
					System.out.println(nomRue);
					System.out.println(idOrginie);
					
					Intersection intersectionOrigine = intersections.get(idOrginie);
					Intersection intersectionDestination = intersections.get(idDest);
					
					
					Troncon t = new Troncon(intersectionOrigine, intersectionDestination, longueur, nomRue);
					troncons.add(t);
					
				}
			}
			
			plan = new Plan(intersections, troncons);
			plan.setLattitudeMax(lattitudeMax);
			plan.setLattitudeMin(lattitudeMin);
			plan.setLongitudeMax(longitudeMax);
			plan.setLongitudeMin(longitudeMin);
			
			buff.close(); 
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		return plan;
		
	}
}
