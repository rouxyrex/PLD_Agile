package modele;

import java.util.ArrayList;
import java.util.List;

public class DemandeLivraison {
	
	List<Livraison> livraisons;
	Intersection entrepot;
	String heureDepart; //A modifier en une vraie heure? 
	
	List<Intersection> ptsPassage;
	
	public DemandeLivraison(List<Livraison> livraisons, Intersection entrepot, String heureDepart) {
		
		this.livraisons = livraisons;
		this.entrepot = entrepot;
		this.heureDepart = heureDepart;
		
		creerPtsPassage();
	}
	
	public void creerPtsPassage() {
		
		ptsPassage = new ArrayList<Intersection>();
		
		for(Livraison l : livraisons) {
			
			ptsPassage.add(l.getAdresseDepot());
			ptsPassage.add(l.getAdresseEnlevement());
			
		}
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
	
}
