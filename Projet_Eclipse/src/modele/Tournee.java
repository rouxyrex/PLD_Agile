package modele;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map; 
import java.util.Observable;

import javafx.util.Pair;

/** Represente une tournee
*/
public class Tournee extends Observable {
	
	Map <Pair<Integer, Intersection>, String> pointsPassage;
	List <Trajet> parcours;
	float duree;
	boolean initialise = false;
	private float coutMeilleureSolution;
	private boolean tempsLimiteAtteint = false;
	private Integer[] meilleureSolution;
	private Integer[] numPtAssocie;
	private int nbSommets;
	private int numeroFichier = 1;

	GraphePCC graphePCC;
	
	public Tournee() {
		pointsPassage = new HashMap<Pair<Integer, Intersection>, String>();
		
		
	}
	
	/** Initialise le graphe des plus courts chemins correspondant a cette tournee
	 * @param graphePCC le graphe a retenir
	*/
	public void initialiserGraphePCC(GraphePCC graphePCC) {
		this.graphePCC = graphePCC;
		
	}
	
	/** Calcule une tournee selon les paramètres donnes ( dont le graphe pcc )
	 * @param tempsLimite le temps de calcul a ne pas depasser en secondes 
	 * @param demandeLivraison la demande pour laquelle calculer la tournee
	*/
	public void calculerUneTournee(int tempsLimite, DemandeLivraison demandeLivraison) {
		this.initialise = true;
		parcours = new LinkedList <Trajet>();
		
		nbSommets = graphePCC.getNbSommets();
		HashMap<String,Integer> intersectionNum = new HashMap<String,Integer>();
		HashMap<Integer,Pair<Integer,Intersection>> numPtsPassages = new HashMap<Integer,Pair<Integer,Intersection>>();
		HashMap<Pair<Integer,Intersection>, Integer> ptsPassagesNum= new HashMap<Pair<Integer,Intersection>,Integer>();
		Trajet[][] trajets = new Trajet[nbSommets][nbSommets];
		float[][] cout = new float[nbSommets][nbSommets];
		float[] duree = new float[nbSommets];
		
		List<Pair<Integer,Intersection>> passages = demandeLivraison.getPtsPassage();
		Pair<Integer,Intersection> ptEntrepot = new Pair<Integer,Intersection>(0,demandeLivraison.getEntrepot());
		ptsPassagesNum.put(ptEntrepot, 0);
		numPtsPassages.put(0,ptEntrepot);
		intersectionNum.put(ptEntrepot.getValue().getId(), 0);
		int compteur=1;
		for(Pair<Integer,Intersection> p : passages){
			ptsPassagesNum.put(p, compteur);
			numPtsPassages.put(compteur,p);
			intersectionNum.put(p.getValue().getId(), compteur++);
		}
		
		//HashMap<Integer, Intersection> numPtPassage = new HashMap<Integer,Intersection>();
		Boolean[] isPtEnlevement = new Boolean[nbSommets];
		numPtAssocie = new Integer[nbSommets];
		Boolean[] ptVisitable = new Boolean[nbSommets];
		Boolean[] ptAssocieVus = new Boolean[nbSommets];

		numPtAssocie[0]=0;
		for(int i = 0;i<nbSommets;i++){
			ptVisitable[i]=true;
		}
		
		for(int i = 0; i<nbSommets;i++){
			for(Trajet t : graphePCC.getListeAdjacence()[i]){ 
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
		Iterator<Pair<Integer, Intersection>> it = passages.iterator();
		while(it.hasNext()){
			Pair<Integer, Intersection> adresseEnlevement = it.next();
			Pair<Integer, Intersection> adresseDepot = it.next();
			numPtAssocie[ptsPassagesNum.get(adresseEnlevement)]=ptsPassagesNum.get(adresseDepot);
			numPtAssocie[ptsPassagesNum.get(adresseDepot)]=ptsPassagesNum.get(adresseEnlevement);
			ptVisitable[ptsPassagesNum.get(adresseEnlevement)]=false;
		} 
		tempsLimiteAtteint = false;
		coutMeilleureSolution = Float.MAX_VALUE;
		meilleureSolution = new Integer[nbSommets];
		ArrayList<Integer> nonVus = new ArrayList<Integer>();
		for (int i=1; i<nbSommets; i++) nonVus.add(i);
		ArrayList<Integer> vus = new ArrayList<Integer>(nbSommets);
		vus.add(0); // le premier sommet visite est 0
		branchAndBound(0, nonVus, vus, 0, cout, duree, ptVisitable, System.currentTimeMillis(), tempsLimite);
		

		for (int i=0; i<nbSommets-1; i++){
			parcours.add(trajets[meilleureSolution[i]][meilleureSolution[i+1]]);
		}
		parcours.add(trajets[meilleureSolution[nbSommets-1]][0]);
		
	}
	

	/** Methode generique d'optimisation combinatoire 
	 * @param sommetCrt numero du sommet courant 
	 * @param nonVus liste des numeros des sommets non vus 
	 * @param vus liste des numeros des sommets vus 
	 * @param coutVus somme des couts des sommets vus 
	 * @param cout tableau des couts pour aller d'un sommet a un autre 
	 * @param duree duree de passage a un sommet donne 
	 * @param tpsDebut temps de calcul deja ecoule
	 * @param tempsLimite temps total de calcul limite
	*/
	

	private void branchAndBound(int sommetCrt, ArrayList<Integer> nonVus, ArrayList<Integer> vus, float coutVus, float [][] cout, float[] duree, Boolean[] ptVisitable, long tpsDebut, int tempsLimite){

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
	    } else if ((coutVus + bound(sommetCrt, nonVus, cout) < coutMeilleureSolution)/*&& (ptEnlevement[i] == true || ptAssocieVus(ptAssocie[i]))*/){ //donner bonne valeur a i
	    	 Iterator<Integer> iter = new IteratorSeq(nonVus);
	    	 Integer prochainSommet;
	    	 
				while(iter.hasNext()){
					prochainSommet=iter.next();
					if(ptVisitable[prochainSommet]==true){
						Boolean[] copiedArray = Arrays.copyOf(ptVisitable,nbSommets);
						copiedArray[numPtAssocie[prochainSommet]]=true; 
						vus.add(prochainSommet);
						nonVus.remove(prochainSommet);
						branchAndBound(prochainSommet, nonVus, vus, coutVus + cout[sommetCrt][prochainSommet] , cout, duree, copiedArray, tpsDebut, tempsLimite);
						vus.remove(prochainSommet);
						nonVus.add(prochainSommet);	
					}
					
				}
	    	 
	    }
	}
	
	/** Methode d'approximation du cout
	 * @param sommetCrt numero du sommet courant 
	 * @param nonVus liste des numeros des sommets non vus 
	 * @param cout tableau des couts pour aller d'un sommet a un autre 
	*/
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

	/** Reinitialisation de la tournee
	*/
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

	/** Suppression d'une livraison de la tournee
	 * @param livraison livraison a supprimer
	 * @return paire de deux intersections , celle visitee avant la livraison supprimee et celle a visiter ensuite
	*/
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

	/** Ajout d'une livraison a la tournee
	 * @param livraison livraison a ajouter
	 * @param interAvantEnlevement intersection a partir de laquelle ajouter la livraison 
	 * @param interAvantDepot intersection a rejoindre apres le depot de cette livraison
	*/
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
	
	public boolean getTempsLimiteAtteint(){
		return this.tempsLimiteAtteint;
	}

	/** Permet de generer une feuille de route textuelle
	 * 
	 */
	public void genererFeuilleRoute() {
        BufferedWriter writer;
        String res = "***Feuille de Route****\n";
 
        res += "Vous parcourerez les intersections dans l'ordre : \n";
        
        for(Trajet trajet : parcours) {
                res += "\n";
                res += "Depart :" + trajet.getIntersectionOrigine().getValue().getId( )+"\n";
                List<Troncon> troncs = trajet.getTrajet();
                for(Troncon tronc : troncs) {
                        res+=tronc.getNomRue()+"\n";
                }
                res +="Arrivee: " + trajet.getIntersectionDestination().getValue().getId()+"\n";
        }
        try {
                writer = new BufferedWriter(new FileWriter(new File("./Feuille_De_Route" , "FeuilleRoute"+Integer.toString(numeroFichier)+".txt")));
                writer.write(res);
                writer.close();
                numeroFichier++;
        } catch (IOException e1) {
                e1.printStackTrace();
        }
}
	
	public boolean estInitialise() {
		return this.initialise;
	}

}
