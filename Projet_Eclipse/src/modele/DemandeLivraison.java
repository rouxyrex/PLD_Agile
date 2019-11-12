package modele;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;

public class DemandeLivraison extends Observable {
	
	List<Livraison> livraisons;
	Intersection entrepot;
	String heureDepart; //A modifier en une vraie heure? 
	
	List<Intersection> ptsPassage;
	
	public DemandeLivraison() {
		livraisons = new ArrayList<Livraison>();
		ptsPassage = new ArrayList<Intersection>();
	}
	
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
	
	
	public void reset() {
		
		Iterator<Livraison> it = livraisons.iterator();
		
		while (it.hasNext()){
			it.next();
			it.remove();
		}
		
		Iterator<Intersection> it2 = ptsPassage.iterator();
		
		while (it2.hasNext()){
			it2.next();
			it2.remove();
		}
		
		entrepot = null;
		heureDepart = null;
		
		setChanged();
		notifyObservers();	
	}
	
	
	public void ajouterLivraison(Livraison l){
		livraisons.add(l);
		
		ptsPassage.add(l.getAdresseDepot());
		ptsPassage.add(l.getAdresseEnlevement());
		
		setChanged();
		notifyObservers();
	}
	
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
	
	public List<Intersection> getPtsPassage() {
		return ptsPassage;
	}
	
	public int getPtsInteret() {
		
		if(entrepot == null) {
			return 0;
		}
		else {
			return 1 + ptsPassage.size();
		}
	}
	
}
