package modele;
import java.util.List;
import java.util.Map;

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
