package Modele;
import java.util.List;
import java.util.Map;

public class Plan {
	Map<String, Intersection> intersections;
	List<Troncon> troncons; 
	
	public Plan(Map<String, Intersection> intersections, List<Troncon> troncons) {
		this.intersections = intersections;
		this.troncons = troncons;
	}
	
	public Map<String, Intersection> getIntersections() {
		return intersections;
	}
	
	public Intersection getIntersectionById(String id) { //Test si rien trouve, renvoyer null
		
		Intersection inter = intersections.get(id);
		
		return inter;
	}
	
	public List<Troncon> getTroncons() {
		return troncons;
	}
}
