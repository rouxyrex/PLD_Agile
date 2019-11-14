package modele;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;

import javafx.util.Pair;

/** Represente une demande de livraison
*/
public class DemandeLivraison extends Observable {

	List<Livraison> livraisons;
	Intersection entrepot;
	String heureDepart; //A modifier en une vraie heure?
	int derniereIdLivraison;

	List<Pair<Integer, Intersection>> ptsPassage;

	public DemandeLivraison() {
		livraisons = new ArrayList<Livraison>();
		ptsPassage = new ArrayList<Pair<Integer, Intersection>>();
		derniereIdLivraison = 0;
	}

	/** Initialise la demande de livraison
	 * @param livraisonsAInserer liste des livraisons composant la demande
	 * @param entrepot intersection correspondant a l'entrepot  
	 * @param heureDepart heure de depart de la demande de livraison
	*/
	public void initialiser(List<Livraison> livraisonsAInserer, Intersection entrepot, String heureDepart) {

		for(Livraison l : livraisonsAInserer) {
			ajouterLivraison(l);
		}

		this.entrepot = entrepot;
		this.heureDepart = heureDepart;

		setChanged();
		notifyObservers();
	}


	public Iterator<Livraison> getIterateurLivraisons(){
		return livraisons.iterator();
	}


	/** Reinitialise la demande de tournee
	*/
	public void reset() {

		Iterator<Livraison> it = livraisons.iterator();

		while (it.hasNext()){
			it.next();
			it.remove();
		}

		Iterator<Pair<Integer, Intersection>> it2 = ptsPassage.iterator();

		while (it2.hasNext()){
			it2.next();
			it2.remove();
		}

		entrepot = null;
		heureDepart = null;
		derniereIdLivraison = 0;

		setChanged();
		notifyObservers();
	}


	/** Ajoute une livraison a la demande
	 * @param l La livraison à ajouter
	*/
	public void ajouterLivraison(Livraison l){

		derniereIdLivraison++;
		l.setId(derniereIdLivraison);

		livraisons.add(l);

		ptsPassage.add(l.getAdresseEnlevement());
		ptsPassage.add(l.getAdresseDepot());

		setChanged();
		notifyObservers();
	}

	/** Supprime une livraison de la demande
	 * @param l La livraison a supprimer
	*/
	public void supprimerLivraison(Livraison l) {
		livraisons.remove(l);
		ptsPassage.remove(l.getAdresseDepot());
		ptsPassage.remove(l.getAdresseEnlevement());

		setChanged();
		notifyObservers();
	}

	public List<Livraison> getLivraisons() {
		return livraisons;
	}

	public Intersection getEntrepot() {
		return entrepot;
	}

	public String getHeureDepart() {
		return heureDepart;
	}

	/** Renvoie la liste des points de passage
	*/
	public List<Pair<Integer, Intersection>> getPtsPassage() {
		return ptsPassage;
	}

	public int getDerniereIdLivraison() {
		return this.derniereIdLivraison;
	}

	/** Renvoie le nombre de points d'interet
	*/
	public int getNbPtsInteret() {

		if(entrepot == null) {
			return 0;
		}
		else {
			return 1 + ptsPassage.size();
		}
	}

}
