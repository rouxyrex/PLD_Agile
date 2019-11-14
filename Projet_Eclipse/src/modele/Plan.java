package modele;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Observable;

import javafx.util.Pair;

/** Represente un plan
*/
public class Plan extends Observable {
	Map<String, Intersection> intersections;
	List<Troncon> troncons;

	float latitudeMin;
	float latitudeMax;
	float longitudeMin;
	float longitudeMax;

	public Plan() {

	}

	/** Initialise le plan en renseignant les variables et en notifiant les observateurs
	 * @param intersections map de l'id des intersections et de celles-ci constituant le plan
	 * @param troncons liste des troncons constituant le plan
	*/
	public void initialiser(Map<String, Intersection> intersections, List<Troncon> troncons) {
		this.intersections = intersections;
		this.troncons = troncons;

		setChanged();
		notifyObservers();
	}


	/** Calcule le trajet optimal entre un point d'interet de la demande de livraison et tous les autres par l'algorithme de Dijkstra
	 * @param demandeLivraison la demande de livraison etudiee 
	 * @param intersectionInitiale intersection de depart de la recherche (on utilisera un point d'interet)
	 * @return Une linkedList de trajets representant l'ensemble des trajets allant du depart a chacun des points d'interet
	*/
	public LinkedList<Trajet> Dijkstra(DemandeLivraison demandeLivraison, Pair<Integer, Intersection> intersectionInitiale){

		//declaration
				LinkedList<Trajet> listeTrajets = new LinkedList<Trajet>();
				LinkedList<Intersection> interAVisiter = new LinkedList<>(); //queue
				Map<Intersection, Float> temps = new HashMap<>();
				Map<Intersection, Troncon> tronconOrigine = new HashMap<>();
				Map<Intersection, Boolean> visite = new HashMap<>();

				List<Pair<Integer, Intersection>> ptsInteret =new  ArrayList<Pair<Integer, Intersection>>(demandeLivraison.getPtsPassage());
				ptsInteret.add(new Pair<Integer, Intersection>(0, demandeLivraison.getEntrepot()));

				Intersection curr_i;
				Intersection interVoisine;
				float zero = 0;

				String idInterInit = intersectionInitiale.getValue().getId();

				//initialisation
				intersections.forEach((key,value)->temps.put(value, Float.POSITIVE_INFINITY));
				temps.replace(intersections.get(idInterInit), zero);

				intersections.forEach((key,value)->visite.put(value, false));

				interAVisiter.add(intersections.get(idInterInit));

				//algo
				while(! interAVisiter.isEmpty()) {
					curr_i = interAVisiter.poll();
					if (! visite.get(curr_i)) {
						for(Troncon curr_tv : curr_i.getTronconsVoisins()) {
							interVoisine = curr_tv.getIntersectionDestination();
							interAVisiter.add(intersections.get(interVoisine.getId()));

							if (temps.get(curr_i) + curr_tv.getTempsParcoursMinute() < temps.get(interVoisine)) {
								temps.replace(interVoisine, temps.get(curr_i) + curr_tv.getTempsParcoursMinute());
								tronconOrigine.put(interVoisine, curr_tv);
							}
						}
						visite.replace(curr_i, true);
					}
				}

				for (Pair<Integer, Intersection> i : ptsInteret) {
					LinkedList<Troncon> tronconsParcourus = new LinkedList<Troncon>();
					curr_i = intersections.get(i.getValue().getId());
					Troncon curr_tv;

					while (curr_i.getId() != idInterInit) {
						curr_tv = tronconOrigine.get(curr_i);
						tronconsParcourus.addFirst(curr_tv);
						curr_i = curr_tv.getIntersectionOrigine();
					}
					Trajet curr_trajet = new Trajet(tronconsParcourus, intersectionInitiale, i);
					listeTrajets.add(curr_trajet);
				}

		return listeTrajets;
	}


	public Map<String, Intersection> getIntersections() {
		return intersections;
	}

	/** Renvoie l'intersection correspondant à l'id demandee
	 * @param id l'id demandee
	 * @return l'intersection correspondante
	*/
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
