package modele;

import javafx.util.Pair;

/** Represente une livraison
*/
public class Livraison {
	
	Intersection adresseEnlevement;
	Intersection adresseDepot;
	int dureeEnlevement; //en secondes
	int dureeDepot; //en secondes
	int id;
	
public Livraison(Intersection adresseEnlevement, Intersection adresseDepot, int dureeEnlevement, int dureeDepot) {
		
		this.adresseEnlevement = adresseEnlevement;
		this.adresseDepot = adresseDepot;
		this.dureeEnlevement = dureeEnlevement/60;
		this.dureeDepot = dureeDepot/60;
		
	}
	
	public int getId() {
		return this.id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public Pair<Integer, Intersection> getAdresseEnlevement() {
		return new Pair<Integer, Intersection>(this.id, this.adresseEnlevement);
	}
	
	public Pair<Integer, Intersection> getAdresseDepot() {
		return new Pair<Integer, Intersection>(this.id, this.adresseDepot);
	}
	
	public int getDureeEnlevement() {
		return dureeEnlevement;
	}
	
	public int getDureeDepot() {
		return dureeDepot;
	}
	
}
