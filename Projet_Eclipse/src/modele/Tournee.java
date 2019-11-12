package modele;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Observable;

import javafx.util.Pair;

public class Tournee extends Observable {
	
	Map <Pair<Integer, Intersection>, String> pointsPassage;
	List <Trajet> parcours;
	float duree;
	boolean initialise = false;
	
	GraphePCC graphePCC;
	
	public Tournee() {
		pointsPassage = new HashMap<Pair<Integer, Intersection>, String>();
		parcours = new LinkedList <Trajet>();
		
	}
	
	public void initialiserGraphePCC(GraphePCC graphePCC) {
		this.graphePCC = graphePCC;
	}
	
	public void calculerUneTournee() {
		this.initialise = true;
		
		
		
	}
	
	public void calculerHeuresEtDuree() {
		
		
	}
	
	public void reset() {
		
		pointsPassage.clear();
		
		Iterator<Trajet> it2 = parcours.iterator();
		
		while (it2.hasNext()){
			it2.next();
			it2.remove();
		}
		
		graphePCC = null;
		
		duree = 0;
		this.initialise = false;
		
		setChanged();
		notifyObservers();	
		
	}
	
	public Pair <Pair<Integer, Intersection>, Pair<Integer, Intersection> > supprimerLivraison(Livraison livraison) {
		
		graphePCC.supprimerLivraison(livraison);
		
		int idLivraisonASupprimer = livraison.getId();
		Pair<Integer, Intersection> intersectionEnlevement = livraison.getAdresseEnlevement();
		Pair<Integer, Intersection> intersectionDepot = livraison.getAdresseDepot();
		
		//Mise a jour de pointsPassage
		Iterator<Map.Entry<Pair<Integer, Intersection>, String> > iterator = pointsPassage.entrySet().iterator(); 
		
		while (iterator.hasNext()) { 
 
		    Map.Entry<Pair<Integer, Intersection>, String> entry = iterator.next(); 
		
		    if (idLivraisonASupprimer == entry.getKey().getKey()) { 
		        iterator.remove(); 
		    } 
		} 
		
		Pair<Integer, Intersection> interAvantEnlevement = null;
		Pair<Integer, Intersection> interAvantDepot = null;
		
		//Mise a jour de parcours
		for(int i = 0 ; i < parcours.size(); i++) {
			
			//Si le trajet va sur une inter a supprimer
			if(parcours.get(i).getIntersectionDestination().getKey() == idLivraisonASupprimer) {
				//Si le trajet encore suivant est l'autre inter a supprimer
				if(parcours.get(i+1).getIntersectionDestination().getKey() == idLivraisonASupprimer) {
					
					Pair<Integer, Intersection> interOrigine = parcours.get(i).getIntersectionOrigine();
					Pair<Integer, Intersection> interDestination = parcours.get(i+2).getIntersectionDestination();
					
					interAvantEnlevement = interOrigine;
					interAvantDepot = interOrigine;
					
					Trajet nouveauTrajet = graphePCC.obtenirTrajetEntreIntersections(interOrigine, interDestination);
					
					parcours.remove(i);
					parcours.remove(i);
					parcours.remove(i);
					
					parcours.add(i, nouveauTrajet);
				}
				else {
					Pair<Integer, Intersection> interOrigine = parcours.get(i).getIntersectionOrigine();
					Pair<Integer, Intersection> interDestination = parcours.get(i+1).getIntersectionDestination();
					
					if(parcours.get(i).getIntersectionDestination().getValue().getId() == intersectionEnlevement.getValue().getId()) {
						interAvantEnlevement = interOrigine;
					}
					else {
						interAvantDepot = interOrigine;
					}
					
					Trajet nouveauTrajet = graphePCC.obtenirTrajetEntreIntersections(interOrigine, interDestination);
					
					parcours.remove(i);
					parcours.remove(i);
					
					parcours.add(i, nouveauTrajet);
				}
			}
		}
		
		calculerHeuresEtDuree();
		
		setChanged();
		notifyObservers();
		
		return new Pair<Pair<Integer, Intersection>, Pair<Integer, Intersection>>(interAvantEnlevement, interAvantDepot);
	}
	
	public void ajouterLivraison(Livraison livraison, Pair<Integer, Intersection> interAvantEnlevement, Pair<Integer, Intersection> interAvantDepot) {
		
		
		
	}
	
	public Map <Pair<Integer, Intersection>, String> getPointsPassage(){
		return this.pointsPassage;
	}
	
	public List <Trajet> getParcours(){
		return this.parcours;
	}
	
	public float getDuree(){
		return this.duree;
	}
	
	public boolean estInitialise() {
		return this.initialise;
	}
	
}