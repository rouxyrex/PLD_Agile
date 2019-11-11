package modele;
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
		
		LinkedList<Trajet> graphouille= new LinkedList<Trajet>();
		return graphouille;
		
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
