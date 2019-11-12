package modele;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Tournee {
	
	Map <Intersection, String> pointsPassage;
	List <Trajet> parcours;
	float duree;
	
	GraphePCC graphePCC;
	
	public Tournee() {//Constructeur a faire
		pointsPassage = new HashMap<Intersection, String>();
		parcours = new LinkedList <Trajet>();
		
	}
	
	public void initialiserGraphePCC(GraphePCC graphePCC) {
		this.graphePCC = graphePCC;
	}
	
	public void calculerUneTournee() {
		
		
	}
	
	public Map <Intersection, String> getPointsPassage(){
		return this.pointsPassage;
	}
	
	public List <Trajet> getParcours(){
		return this.parcours;
	}
	
	public float getDuree(){
		return this.duree;
	}
	
}