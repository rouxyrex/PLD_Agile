package plan;


public class Troncon {
	Intersection intersectionOrigine;
	Intersection intersectionDestination;
	float longueur;
	String nomRue;
	float tempsParcoursMinute;
	float vitesseMetresMinute = 250;
	
	public Troncon(Intersection intersectionOrigine, Intersection intersectionDestination, float longueur, String nomRue) {
		this.intersectionOrigine = intersectionOrigine;
		this.intersectionDestination = intersectionDestination;
		this.longueur = longueur;
		this.nomRue = nomRue;
		tempsParcoursMinute = (float) (longueur/vitesseMetresMinute);
	}
	
	public Intersection getIntersectionOrigine() {
		return intersectionOrigine;
	}
	
	public Intersection getIntersectionDestination() {
		return intersectionDestination;
	}
	
	public float getLongueur() {
		return longueur;
	}
	
	public String getNomRue() {
		return nomRue;
	}
	
	public float getTempsParcoursMinute() {
		return tempsParcoursMinute;
	}
}
