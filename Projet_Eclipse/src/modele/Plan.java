package modele;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class Plan {
	Map<String, Intersection> intersections;
	List<Troncon> troncons;
	float latitudeMin;
	float latitudeMax;
	float longitudeMin;
	float longitudeMax;
	
	public Plan(Map<String, Intersection> intersections, List<Troncon> troncons) {
		this.intersections = intersections;
		this.troncons = troncons;
	}
	
	
	
	public LinkedList<Trajet> Dijkstra(DemandeLivraison demandeLivraison, Intersection intersectionInitiale){
		
		LinkedList<Trajet> listeTrajets = new LinkedList<Trajet>();
		PriorityQueue<Intersection> interAVisiter = new PriorityQueue<>();
		Map<Intersection, Float> temps = new HashMap<>(); //initialiser tout ca
		Map<Intersection, Troncon> tronconOrigine = new HashMap<>();
		Map<Intersection, Boolean> visite = new HashMap<>();
		
		Intersection curr_i;
		Intersection interVoisine;
		
		interAVisiter.add(intersectionInitiale);
		while(! interAVisiter.isEmpty()) {
			curr_i = interAVisiter.poll(); //verifier la gestion de la pile
			if (! visite.get(curr_i)) {
				for(Troncon curr_tv : curr_i.getTronconsVoisins()) {
					interVoisine = curr_tv.getIntersectionDestination();
					interAVisiter.add(interVoisine);
					if (temps.get(curr_i) + curr_tv.getTempsParcoursMinute() < temps.get(interVoisine)) {
						temps.replace(interVoisine, temps.get(curr_i) + curr_tv.getTempsParcoursMinute());
						tronconOrigine.replace(interVoisine, curr_tv);
					}
				}
				visite.replace(curr_i, true);
			}
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
