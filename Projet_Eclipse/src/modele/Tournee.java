package modele;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Observable;

import javafx.util.Pair;

public class Tournee extends Observable {
	
	Map <Pair<Integer, Intersection>, String> pointsPassage;
	List <Trajet> parcours;
	float duree;
	
	GraphePCC graphePCC;
	
	public Tournee() {//Constructeur a faire
		pointsPassage = new HashMap<Pair<Integer, Intersection>, String>();
		parcours = new LinkedList <Trajet>();
		
	}
	
	public void initialiserGraphePCC(GraphePCC graphePCC) {
		this.graphePCC = graphePCC;
	}
	
	public void calculerUneTournee() {
		
		
	}
	
	public void reset() {
		
		pointsPassage.clear();
		
		Iterator<Trajet> it2 = parcours.iterator();
		
		while (it2.hasNext()){
			it2.next();
			it2.remove();
		}
		
		duree = 0;
		
		setChanged();
		notifyObservers();	
		
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
	
}