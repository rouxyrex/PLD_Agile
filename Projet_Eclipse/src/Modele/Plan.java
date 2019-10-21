package Modele;
import java.util.List;
import java.util.Map;

public class Plan {
	Map<String, Intersection> intersections;
	List<Troncon> troncons;
	float lattitudeMin;
	float lattitudeMax;
	float longitudeMin;
	float longitudeMax;
	
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
	
	public void setLongitudeMin(float longitudeMin) {
		this.longitudeMin = longitudeMin;
	}

	public void setLattitudeMax(float lattitudeMax) {
		this.lattitudeMax = lattitudeMax;
	}

	public void setLattitudeMin(float lattitudeMin) {
		this.lattitudeMin = lattitudeMin;
	}

	public void setLongitudeMax(float longitudeMax) {
		this.longitudeMax = longitudeMax;
	}

	public float getLongitudeMin( ) {
		return longitudeMin;
	}

	public float getLattitudeMax( ) {
		return lattitudeMax;
	}

	public float getLattitudeMin( ) {
		return lattitudeMin;
	}

	public float getLongitudeMax( ) {
		return longitudeMax;
	}
}
