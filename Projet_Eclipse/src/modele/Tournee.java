package modele;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
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
	private float coutMeilleureSolution;
	private boolean tempsLimiteAtteint;
	private Integer[] meilleureSolution;

	GraphePCC graphePCC;

	public Tournee() {
		pointsPassage = new HashMap<Pair<Integer, Intersection>, String>();
		parcours = new LinkedList <Trajet>();

	}

	public void initialiserGraphePCC(GraphePCC graphePCC) {
		this.graphePCC = graphePCC;
	}

	public void calculerUneTournee(int tempsLimite, DemandeLivraison demandeLivraison) {
		this.initialise = true;

		int nbSommets = graphePCC.getNbSommets();
		HashMap<String,Integer> intersectionNum = new HashMap<String,Integer>();
		HashMap<Integer,Intersection> numIntersection= new HashMap<Integer,Intersection>();
		Trajet[][] trajets = new Trajet[nbSommets][nbSommets];
		float[][] cout = new float[nbSommets][nbSommets];
		float[] duree = new float[nbSommets];

		int compteur=1;
		intersectionNum.put(demandeLivraison.getEntrepot().getId(), 0);
		numIntersection.put(0, demandeLivraison.getEntrepot());
		for(int i = 0; i<nbSommets;i++){
			for(Trajet t : graphePCC.getListeAdjacence()[i]){

				if(intersectionNum.get(t.getIntersectionOrigine().getValue().getId())==null){
					intersectionNum.put(t.getIntersectionOrigine().getValue().getId(), compteur);
					numIntersection.put(compteur, t.getIntersectionOrigine().getValue());
					compteur++;
				}

				if(intersectionNum.get(t.getIntersectionDestination().getValue().getId())==null){
					intersectionNum.put(t.getIntersectionDestination().getValue().getId(), compteur);
					numIntersection.put(compteur, t.getIntersectionDestination().getValue());
					compteur++;
				}


				cout[intersectionNum.get(t.getIntersectionOrigine().getValue().getId())]
						[intersectionNum.get(t.getIntersectionDestination().getValue().getId())]=t.getTempsParcours();


				trajets[intersectionNum.get(t.getIntersectionOrigine().getValue().getId())]
						[intersectionNum.get(t.getIntersectionDestination().getValue().getId())] = t;


			}
		}

		for(Livraison l : demandeLivraison.getLivraisons())
		{
			duree[intersectionNum.get(l.getAdresseDepot().getValue().getId())]=l.getDureeDepot();
			duree[intersectionNum.get(l.getAdresseEnlevement().getValue().getId())]=l.getDureeEnlevement();

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
			parcours.add(trajets[meilleureSolution[i]][meilleureSolution[i+1]]);
		}
		parcours.add(trajets[meilleureSolution[nbSommets-1]][0]);
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
	    } else if (coutVus + bound(sommetCrt, nonVus, cout) < coutMeilleureSolution){
	    	 Iterator<Integer> iter = new IteratorSeq(nonVus);
	    	 Integer prochainSommet;
	        while(iter.hasNext()){
	        	prochainSommet=iter.next();
	        	vus.add(prochainSommet);
	        	nonVus.remove(prochainSommet);
	        	branchAndBound(prochainSommet, nonVus, vus, coutVus + cout[sommetCrt][prochainSommet] , cout, duree, tpsDebut, tempsLimite);
	        	vus.remove(prochainSommet);
	        	nonVus.add(prochainSommet);
	        }
	    }
	}

	private float bound(int SommetCrt, ArrayList<Integer> nonVus, float[][] cout){
		float bound = 0;
		for(Integer i : nonVus){
			float min = 0;
			for(int j = 0 ; j<cout.length ; j++) {
				if(min>cout[i][j]) {
					min = cout[i][j];
				}
			}
			bound = min;
		}
		return bound;
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
		meilleureSolution = null;

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

		graphePCC.initialiserGraphePCC();

		int idLivraisonAAjouter = livraison.getId();
		Pair<Integer, Intersection> intersectionEnlevement = livraison.getAdresseEnlevement();
		Pair<Integer, Intersection> intersectionDepot = livraison.getAdresseDepot();

		//Mise a jour de pointsPassage
		pointsPassage.put(intersectionEnlevement, "");
		pointsPassage.put(intersectionDepot, "");

		//Mise a jour de parcours
		for(int i = 0 ; i < parcours.size(); i++) {

			if((parcours.get(i).getIntersectionOrigine().getKey() == interAvantEnlevement.getKey()) && (parcours.get(i).getIntersectionOrigine().getValue().getId() == interAvantEnlevement.getValue().getId())) {

				if((parcours.get(i).getIntersectionOrigine().getKey() == interAvantDepot.getKey()) && (parcours.get(i).getIntersectionOrigine().getValue().getId() == interAvantDepot.getValue().getId())) {

					Trajet nouveauTrajet1 = graphePCC.obtenirTrajetEntreIntersections(parcours.get(i).getIntersectionOrigine(), intersectionEnlevement);
					Trajet nouveauTrajet2 = graphePCC.obtenirTrajetEntreIntersections(intersectionEnlevement, intersectionDepot);
					Trajet nouveauTrajet3 = graphePCC.obtenirTrajetEntreIntersections(intersectionDepot, parcours.get(i).getIntersectionDestination());

					parcours.remove(i);

					parcours.add(i, nouveauTrajet3);
					parcours.add(i, nouveauTrajet2);
					parcours.add(i, nouveauTrajet1);
				}
				else {
					Trajet nouveauTrajet1 = graphePCC.obtenirTrajetEntreIntersections(parcours.get(i).getIntersectionOrigine(), intersectionEnlevement);
					Trajet nouveauTrajet2 = graphePCC.obtenirTrajetEntreIntersections(intersectionEnlevement, parcours.get(i).getIntersectionDestination());

					parcours.remove(i);

					parcours.add(i, nouveauTrajet2);
					parcours.add(i, nouveauTrajet1);
				}
			}
			else if((parcours.get(i).getIntersectionOrigine().getKey() == interAvantDepot.getKey()) && (parcours.get(i).getIntersectionOrigine().getValue().getId() == interAvantDepot.getValue().getId())) {

				Trajet nouveauTrajet1 = graphePCC.obtenirTrajetEntreIntersections(parcours.get(i).getIntersectionOrigine(), intersectionDepot);
				Trajet nouveauTrajet2 = graphePCC.obtenirTrajetEntreIntersections(intersectionDepot, parcours.get(i).getIntersectionDestination());

				parcours.remove(i);

				parcours.add(i, nouveauTrajet2);
				parcours.add(i, nouveauTrajet1);
			}
		}

		calculerHeuresEtDuree();

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

	public void genererFeuilleRoute() {
		// TODO Auto-generated method stub
		BufferedWriter writer;
		String res = "***Bienvenue sur la feuille de Route****\n";
		res += "Votre ordre de passage sera : \n";
		for (Map.Entry<Intersection, String> entry : pointsPassage.entrySet())
		{
			res += "Passage � l'intersection � : "+ entry.getKey().getId()+ " � "+ entry.getValue() +" heure\n";
		}
		res += "Ainsi, vous parcourerez les intersection dans l'ordre : \n";
		for(Trajet trajet : parcours) {
			res += trajet.getIntersectionOrigine().getId( )+ " jusqu'� " + trajet.getIntersectionDestination().getId();
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
