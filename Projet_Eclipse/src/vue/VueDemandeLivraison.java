package vue;
 
import java.util.LinkedList;
import java.util.List; 
import java.awt.Color;
import java.awt.Graphics;
 
import javax.swing.JPanel;

import modele.DemandeLivraison;
import modele.Livraison; 
import vue.VueAdresseDepot;
import vue.VueAdresseEnlevement;

public class VueDemandeLivraison {

	private static final long serialVersionUID = 1L;
	private int echelle; 
	private DemandeLivraison dl;
	private Fenetre f;  
	VueEntrepot entrepot = null;
	
	LinkedList<VueAdresseDepot> adressesDepot = null;
	LinkedList<VueAdresseEnlevement> adressesEnlevement = null;
	

	/**
	 * Cree la vue graphique permettant de dessiner plan avec l'echelle e dans la fenetre f
	 * @param plan leplan
	 * @param e l'echelle
	 * @param f la fenetre
	 */
	public VueDemandeLivraison(int e, Fenetre f) {
		super(); 
		adressesDepot = new LinkedList<VueAdresseDepot>();
		adressesEnlevement = new LinkedList<VueAdresseEnlevement>(); 
		this.echelle = e;  
		this.f = f; 
	} 
	
	/**
	 * Methode appelee a chaque fois que VueGraphique doit etre redessinee
	 */
	public void dessiner(Graphics g, int width, int height) { 
		this.dl = dl; 
		adressesDepot.clear();
		adressesEnlevement.clear();
		entrepot = new VueEntrepot(dl.getEntrepot().getLatitude(), dl.getEntrepot().getLongitude());
		dl.getEntrepot().getLatitude();
		List<Livraison> livraisons = dl.getLivraisons();    
		for(int i= 0; i < livraisons.size(); i++) {
			float xDepot = livraisons.get(i).getAdresseDepot().getLatitude();
			float yDepot = livraisons.get(i).getAdresseDepot().getLongitude();
			float xEnlevement = livraisons.get(i).getAdresseEnlevement().getLatitude();
			float yEnlevement = livraisons.get(i).getAdresseEnlevement().getLongitude(); 
			adressesDepot.add(new VueAdresseDepot(xDepot,yDepot));  
			adressesEnlevement.add(new VueAdresseEnlevement(xEnlevement, yEnlevement));  
		}  
	}

/*	public void setEchelle(int e) {
		largeurVue = (largeurVue/echelle)*e;
		hauteurVue = (hauteurVue/echelle)*e;
		setSize(largeurVue, hauteurVue);
		echelle = e;
	}*/

	public int getEchelle() {
		return echelle;
	}

	public void afficherPlan(/*DemandeLivraison dl*/) {
	//	this.dl = dl;
		// TODO Auto-generated method stub
		
	/*	List<Livraison> livraisons = dl.getLivraisons();   
		
		for(int i= 0; i < livraisons.size(); i++) {
			float xDepot = livraisons.get(i).getAdresseDepot().getLattitude();
			float yDepot = livraisons.get(i).getAdresseDepot().getLongitude();
			float xEnlevement = livraisons.get(i).getAdresseEnlevement().getLattitude();
			float yEnlevement = livraisons.get(i).getAdresseEnlevement().getLongitude(); 
			adressesDepot.add(new VueAdresseDepot(xDepot,yDepot));  
			adressesEnlevement.add(new VueAdresseEnlevement(xEnlevement, yEnlevement));  
		}  */
	}

	/*public int getHauteur() {
		return hauteurVue;
	}

	public int getLargeur() {
		return largeurVue;
	}*/


}
