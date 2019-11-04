package modele;

import java.util.List;

public class Trajet {
	
	List <Troncon> trajet;
	Intersection intersectionOrigine;
	Intersection intersectionDestination;
	float tempsParcours;
	
	public Trajet(List <Troncon> trajet, Intersection intersectionOrigine, Intersection intersectionDestination) {
		this.trajet = trajet;
		
		this.intersectionOrigine = intersectionOrigine;
		this.intersectionDestination = intersectionDestination;
		
		float duree = 0;
		
		for(Troncon t : trajet) {
			duree += t.getTempsParcoursMinute();
		}
		
		this.tempsParcours = duree;
	}
	
	
	public List<Troncon> getTrajet() {
		return this.trajet;
	}
	
	public Intersection getIntersectionOrigine() {
		return this.intersectionOrigine;
	}
	
	public Intersection getIntersectionDestination() {
		return this.intersectionDestination;
	}
	
	public float getTempsParcours() {
		return this.tempsParcours;
	}
	
	
}