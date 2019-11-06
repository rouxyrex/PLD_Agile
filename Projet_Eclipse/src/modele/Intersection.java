package modele;

import java.util.ArrayList;
import java.util.List;

public class Intersection {
	String id;
	float latitude;
	float longitude;
	List<Troncon> tronconsVoisins;		
	
	public Intersection(String id, float latitude, float longitude) {
		this.id = id;
		this.latitude = latitude;
		this.longitude = longitude;
		tronconsVoisins = new ArrayList<Troncon>();
	}
	
	public void addTronconVoisin(Troncon troncon) {
		
		tronconsVoisins.add(troncon);
	}
	
	
	public float getLatitude() {
		return latitude;
	}
	
	public float getLongitude() {
		return longitude;
	}
	
	public String getId() {
		return id;
	}
	
	public List<Troncon> getTronconsVoisins() {
		return tronconsVoisins;
	}
}
