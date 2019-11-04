package modele;

import java.util.List;

public class DemandeLivraison {
	
	List<Livraison> livraisons;
	Intersection entrepot;
	String heureDepart; //A modifier en une vraie heure? 
	
	public DemandeLivraison(List<Livraison> livraisons, Intersection entrepot, String heureDepart) {
		
		this.livraisons = livraisons;
		this.entrepot = entrepot;
		this.heureDepart = heureDepart;
		
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
	
}
