package modele;

import java.util.List;
import java.util.Map;

public class Tournee {
	
	Map <Intersection, String> pointsPassage;
	List <Trajet> parcours;
	float duree;
	
	public Tournee() {//Constructeur a faire
		
		
		
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