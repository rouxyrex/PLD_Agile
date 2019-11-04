package modele;


public class Livraison {
	
	Intersection adresseEnlevement;
	Intersection adresseDepot;
	int dureeEnlevement; //en secondes
	int dureeDepot; //en secondes
	
public Livraison(Intersection adresseEnlevement, Intersection adresseDepot, int dureeEnlevement, int dureeDepot) {
		
		this.adresseEnlevement = adresseEnlevement;
		this.adresseDepot = adresseDepot;
		this.dureeEnlevement = dureeEnlevement;
		this.dureeDepot = dureeDepot;
		
	}
	
	public Intersection getAdresseEnlevement() {
		return adresseEnlevement;
	}
	
	public Intersection getAdresseDepot() {
		return adresseDepot;
	}
	
	public int getDureeEnlevement() {
		return dureeEnlevement;
	}
	
	public int getDureeDepot() {
		return dureeDepot;
	}
	
}
