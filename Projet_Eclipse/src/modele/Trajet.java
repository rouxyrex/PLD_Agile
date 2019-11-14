package modele;

import java.util.LinkedList;
import java.util.List;

import javafx.util.Pair;

/** Represente un trajet entre deux intersections
*/
public class Trajet {

	List <Troncon> trajet;
	Pair<Integer, Intersection> intersectionOrigine;
	Pair<Integer, Intersection> intersectionDestination;
	float tempsParcours;

	public Trajet(List <Troncon> trajet, Pair<Integer, Intersection> intersectionOrigine, Pair<Integer, Intersection> intersectionDestination) {
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

	public Pair<Integer, Intersection> getIntersectionOrigine() {
		return this.intersectionOrigine;
	}

	public Pair<Integer, Intersection> getIntersectionDestination() {
		return this.intersectionDestination;
	}

	public float getTempsParcours() {
		return this.tempsParcours;
	}


}
