package modele;

import java.util.ArrayList;

import java.util.List;

/** Represente une intersection
*/
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
	
	/** Ajoute un troncon a la liste des troncons lies a cette intersection
	 * @param troncon Le troncon a ajouter
	*/
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
