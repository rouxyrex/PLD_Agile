package modele;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Tournee {
	
	private Map <Intersection, String> pointsPassage;
	private List <Trajet> parcours;
	private float duree;
	private float coutMeilleureSolution;
	private boolean tempsLimiteAtteint;
	private Integer[] meilleureSolution;
	
	public Tournee() {//Constructeur a faire
		pointsPassage=new HashMap<Intersection, String>();
		parcours=new ArrayList<Trajet>();
		
	}
	
	public void CalculerTournee(int tempsLimite, GraphePCC graphe, DemandeLivraison demandeLivraison){
		int nbSommets = graphe.getNbSommets();
		HashMap<String,Integer> intersectionNum = new HashMap<String,Integer>();
		HashMap<Integer,Intersection> numIntersection= new HashMap<Integer,Intersection>();
		Trajet[][] trajets = new Trajet[nbSommets][nbSommets];
		float[][] cout = new float[nbSommets][nbSommets];
		float[] duree = new float[nbSommets];
		
		int compteur=1;
		intersectionNum.put(demandeLivraison.getEntrepot().getId(), 0);
		numIntersection.put(0, demandeLivraison.getEntrepot());
		for(int i = 0; i<nbSommets;i++){
			for(Trajet t : graphe.getListeAdjacence()[i]){
				
				if(intersectionNum.get(t.getIntersectionOrigine().getId())==null){
					intersectionNum.put(t.getIntersectionOrigine().getId(), compteur);
					numIntersection.put(compteur, t.getIntersectionOrigine());
					compteur++;
				}
				
				if(intersectionNum.get(t.getIntersectionDestination().getId())==null){
					intersectionNum.put(t.getIntersectionDestination().getId(), compteur);
					numIntersection.put(compteur, t.getIntersectionDestination());
					compteur++;
				}
				
				System.out.println("compteur : "+compteur);
				System.out.println("nombre de sommets: "+nbSommets);
				System.out.println("i : "+intersectionNum.get(t.getIntersectionOrigine().getId()));
				System.out.println("j : " + intersectionNum.get(t.getIntersectionDestination().getId()));
				
				cout[intersectionNum.get(t.getIntersectionOrigine().getId())]
						[intersectionNum.get(t.getIntersectionDestination().getId())]=t.getTempsParcours();
				
				System.out.println(cout[intersectionNum.get(t.getIntersectionOrigine().getId())]
						[intersectionNum.get(t.getIntersectionDestination().getId())]);
				
				trajets[intersectionNum.get(t.getIntersectionOrigine().getId())]
						[intersectionNum.get(t.getIntersectionDestination().getId())] = t;
				
				
			}
		}
		
		for(Livraison l : demandeLivraison.getLivraisons())
		{
			System.out.println(l.getDureeDepot());
			duree[intersectionNum.get(l.getAdresseDepot().getId())]=l.getDureeDepot();
			duree[intersectionNum.get(l.getAdresseEnlevement().getId())]=l.getDureeEnlevement();
			
		}
		
		
		tempsLimiteAtteint = false;
		coutMeilleureSolution = Float.MAX_VALUE;
		meilleureSolution = new Integer[nbSommets];
		ArrayList<Integer> nonVus = new ArrayList<Integer>();
		for (int i=1; i<nbSommets; i++) nonVus.add(i);
		ArrayList<Integer> vus = new ArrayList<Integer>(nbSommets);
		vus.add(0); // le premier sommet visite est 0
		branchAndBound(0, nonVus, vus, 0, cout, duree, System.currentTimeMillis(), tempsLimite);
		
		
		for (int i=0; i<nbSommets-1; i++){
			parcours.add(trajets[i][i+1]);
		}
		System.out.println(Arrays.deepToString(trajets));
		for(Trajet t : parcours){
			System.out.println(t);
		}
	}
	
	private void branchAndBound(int sommetCrt, ArrayList<Integer> nonVus, ArrayList<Integer> vus, float coutVus, float [][] cout, float[] duree, long tpsDebut, int tempsLimite){
		 if (System.currentTimeMillis() - tpsDebut > tempsLimite){
			 tempsLimiteAtteint = true;
			 return;
		 }
	    if (nonVus.size() == 0){ // tous les sommets ont ete visites
	    	coutVus += cout[sommetCrt][0];
	    	if (coutVus < coutMeilleureSolution){ // on a trouve une solution meilleure que meilleureSolution
	    		vus.toArray(meilleureSolution);
	    		coutMeilleureSolution = coutVus;
	    	}
	    } else if (coutVus + bound(sommetCrt, nonVus, cout, duree) < coutMeilleureSolution){
	    	 Iterator<Integer> iter = new IteratorSeq(nonVus);
	    	 Integer prochainSommet;
	        while(iter.hasNext()){
	        	prochainSommet=iter.next();
	        	vus.add(prochainSommet);
	        	nonVus.remove(prochainSommet);
	        	branchAndBound(prochainSommet, nonVus, vus, coutVus + cout[sommetCrt][prochainSommet] + duree[prochainSommet], cout, duree, tpsDebut, tempsLimite);
	        	vus.remove(prochainSommet);
	        	nonVus.add(prochainSommet);
	        }	    
	    }
	}
	
	private float bound(int SommetCrt, ArrayList<Integer> nonVus, float[][] cout, float duree[]){
		float bound = 0;
		for(Integer i : nonVus){
			float min = 0;
			for(int j = 0 ; j<cout.length ; j++) {
				if(min>cout[i][j]) {
					min = cout[i][j];
				}
			}
			bound += min;
		}
		return bound;
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