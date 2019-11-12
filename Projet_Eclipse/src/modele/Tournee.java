package modele;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Tournee {
	
	Map <Intersection, String> pointsPassage;
	List <Trajet> parcours;
	float duree;
	int numeroFichier = 1;
	
	public Tournee() {//Constructeur a faire
		
		pointsPassage = new HashMap<Intersection, String>();

		parcours = new LinkedList <Trajet>();
		
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

	public void genererFeuilleRoute() {
		// TODO Auto-generated method stub
		BufferedWriter writer;
		String res = "***Bienvenue sur la feuille de Route****\n";
		res += "Votre ordre de passage sera : \n";
		for (Map.Entry<Intersection, String> entry : pointsPassage.entrySet())
		{
			res += "Passage à l'intersection à : "+ entry.getKey().getId()+ " à "+ entry.getValue() +" heure\n";
		}
		res += "Ainsi, vous parcourerez les intersection dans l'ordre : \n";
		for(Trajet trajet : parcours) {
			res += trajet.getIntersectionOrigine().getId( )+ " jusqu'à " + trajet.getIntersectionDestination().getId();
		} 
		try {
			writer = new BufferedWriter(new FileWriter(new File("FeuilleRoute"+Integer.toString(numeroFichier)+".txt")));
			writer.write(res);
			writer.close();
			numeroFichier++;
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
		 
		
	}
	
}