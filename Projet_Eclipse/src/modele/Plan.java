package modele;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import java.util.Observable;

public class Plan extends Observable {
	Map<String, Intersection> intersections;
	List<Troncon> troncons;
	
	float latitudeMin;
	float latitudeMax;
	float longitudeMin;
	float longitudeMax;
	
	public Plan() {
		
	}
	
	public void initialiser(Map<String, Intersection> intersections, List<Troncon> troncons) {
		this.intersections = intersections;
		this.troncons = troncons;
		
		setChanged();
		notifyObservers();
	}
	
	
	public LinkedList<Trajet> Dijkstra(DemandeLivraison demandeLivraison, Intersection intersectionInitiale){
		
		//declaration
				LinkedList<Trajet> listeTrajets = new LinkedList<Trajet>();
				LinkedList<Intersection> interAVisiter = new LinkedList<>(); //queue
				Map<Intersection, Float> temps = new HashMap<>();
				Map<Intersection, Troncon> tronconOrigine = new HashMap<>();
				Map<Intersection, Boolean> visite = new HashMap<>();
				
				List<Intersection> ptsInteret = demandeLivraison.getPtsPassage();
				ptsInteret.add(demandeLivraison.getEntrepot());
				
				Intersection curr_i;
				Intersection interVoisine;
				float zero = 0;
				
				String idInterInit = intersectionInitiale.getId();
				
				//initialisation
				intersections.forEach((key,value)->temps.put(value, Float.POSITIVE_INFINITY));
				temps.replace(intersections.get(idInterInit), zero);
				
				intersections.forEach((key,value)->visite.put(value, false));
				
				interAVisiter.add(intersections.get(idInterInit));
				
				//algo
				while(! interAVisiter.isEmpty()) {
					curr_i = interAVisiter.poll();
					if (! visite.get(curr_i)) {
						for(Troncon curr_tv : curr_i.getTronconsVoisins()) {
							interVoisine = curr_tv.getIntersectionDestination();
							interAVisiter.add(intersections.get(interVoisine.getId()));
							
							if (temps.get(curr_i) + curr_tv.getTempsParcoursMinute() < temps.get(interVoisine)) {
								temps.replace(interVoisine, temps.get(curr_i) + curr_tv.getTempsParcoursMinute());
								tronconOrigine.put(interVoisine, curr_tv);
							}
						}
						visite.replace(curr_i, true);
					}
				}
				
				for (Intersection i : ptsInteret) {
					LinkedList<Troncon> tronconsParcourus = new LinkedList<Troncon>();
					curr_i = intersections.get(i.getId());
					Troncon curr_tv;
					
					while (curr_i.getId() != idInterInit) {
						curr_tv = tronconOrigine.get(curr_i);
						tronconsParcourus.addFirst(curr_tv);
						curr_i = curr_tv.getIntersectionOrigine();
					}
					Trajet curr_trajet = new Trajet(tronconsParcourus, intersectionInitiale, i);
					listeTrajets.add(curr_trajet);
				}
				
				return listeTrajets;

	}
	
	
	public Map<String, Intersection> getIntersections() {
		return intersections;
	}
	
	public Intersection getIntersectionById(String id) {
		
		Intersection inter = intersections.get(id);
		
		if(inter != null) {
			return inter;
		}
		else {
			return null;
		}
		
	}
	
	public List<Troncon> getTroncons() {
		return troncons;
	}
	
	public void setLongitudeMin(float longitudeMin) {
		this.longitudeMin = longitudeMin;
	}

	public void setLatitudeMax(float latitudeMax) {
		this.latitudeMax = latitudeMax;
	}

	public void setLatitudeMin(float latitudeMin) {
		this.latitudeMin = latitudeMin;
	}

	public void setLongitudeMax(float longitudeMax) {
		this.longitudeMax = longitudeMax;
	}

	public float getLongitudeMin( ) {
		return longitudeMin;
	}

	public float getLatitudeMax( ) {
		return latitudeMax;
	}

	public float getLatitudeMin( ) {
		return latitudeMin;
	}

	public float getLongitudeMax( ) {
		return longitudeMax;
	}
}
