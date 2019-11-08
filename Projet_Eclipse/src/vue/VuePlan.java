package vue;
 
import java.util.LinkedList;
import java.util.List;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;

import modele.DemandeLivraison;
import modele.Livraison;
import modele.Plan;
import modele.Trajet;
import vue.VueTroncon;
import modele.Troncon;

public class VuePlan extends JPanel {

	private static final long serialVersionUID = 1L;
	private int echelle;
	private int modifLatitude;
	private int modifLongitude; 
	public static float latitudeMax;
	public static float latitudeMin;
	public static float longitudeMax;
	public static float longitudeMin;
	public static float intervalleLatitude;
	public static float intervalleLongitude; 
	
	Color[] colors = {Color.cyan, Color.BLUE, Color.green, Color.RED, Color.magenta, Color.LIGHT_GRAY, Color.ORANGE, Color.YELLOW, Color.PINK, Color.white};
	LinkedList<VueTroncon> tronconsTraces = null;  
	LinkedList<VueTroncon> tronconsTournee = null;  
	LinkedList<VueAdresseDepot> adressesDepot = null;
	LinkedList<VueAdresseEnlevement> adressesEnlevement = null;
	VueEntrepot entrepot = null; 

	/**
	 * Cree la vue graphique permettant de dessiner plan avec l'echelle e dans la fenetre f
	 * @param plan leplan
	 * @param e l'echelle
	 * @param f la fenetre
	 */
	public VuePlan(int e, Fenetre f) {
		super(); 
		tronconsTraces = new LinkedList<VueTroncon>(); 
		tronconsTournee = new LinkedList<VueTroncon>(); 
		adressesDepot = new LinkedList<VueAdresseDepot>();
		adressesEnlevement = new LinkedList<VueAdresseEnlevement>();  
		echelle = 1; 
		modifLatitude = 0;
		modifLongitude = 0;
		setLayout(null);
		setBackground(Color.white);   
        f.getContentPane().add(this,  BorderLayout.CENTER);    
		repaint();
	} 
	
	/**
	 * Methode appelee a chaque fois que VueGraphique doit etre redessinee
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g); 
		g.setColor(Color.black);
		for (VueTroncon troncon : tronconsTraces) {   
	    	troncon.dessiner(g, echelle*this.getWidth(), echelle*this.getHeight(), modifLatitude, modifLongitude, false); 
	    }
		
		g.setColor(Color.blue);
		for (VueTroncon troncon : tronconsTournee) {   
	    	troncon.dessiner(g, echelle*this.getWidth(), echelle*this.getHeight(), modifLatitude, modifLongitude, true); 
	    }
		 
		for(int i = 0; i < adressesDepot.size(); i++) {
			g.setColor(colors[i]);  
			adressesDepot.get(i).dessiner(g, echelle*this.getWidth(), echelle*this.getHeight(), modifLatitude, modifLongitude);
	    }  
		
		for(int i = 0; i < adressesEnlevement.size(); i++) {
			g.setColor(colors[i]);  
			adressesEnlevement.get(i).dessiner(g, echelle*this.getWidth(), echelle*this.getHeight(), modifLatitude, modifLongitude);
	    }   
		
		if(entrepot != null) entrepot.dessiner(g, echelle*this.getWidth(), echelle*this.getHeight(), modifLatitude, modifLongitude); 
	 
	} 

	public int getEchelle() {
		return echelle;
	}

	public void afficherPlan(Plan plan) { 
		adressesEnlevement.clear();
		adressesDepot.clear();
		entrepot = null;
		echelle = 1; 
		modifLatitude = 0;
		modifLongitude = 0;
		// TODO Auto-generated method stub
		List<Troncon> troncons = plan.getTroncons();  
		latitudeMax = plan.getLatitudeMax();
		latitudeMin = plan.getLatitudeMin();
		longitudeMax = plan.getLongitudeMax();
		longitudeMin = plan.getLongitudeMin();
		intervalleLatitude = latitudeMax-latitudeMin;
		intervalleLongitude = longitudeMax-longitudeMin; 
		
		for(int i= 0; i < troncons.size(); i++) {

			float x1 = troncons.get(i).getIntersectionOrigine().getLatitude();
			float y1 = troncons.get(i).getIntersectionOrigine().getLongitude();
			float x2 = troncons.get(i).getIntersectionDestination().getLatitude();
			float y2 = troncons.get(i).getIntersectionDestination().getLongitude();
			tronconsTraces.add(new VueTroncon(x1,y1,x2,y2)); 
		}   
	}
	
	public void afficherLivraisonDemande(DemandeLivraison dl) { 
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

	public void zoom() { 
		echelle = echelle + 1;
		repaint();
	} 
	public void dezoom() { 
		echelle = echelle - 1;
		if(echelle <= 0) echelle = 1;
		boolean res = (int) (((longitudeMax-VuePlan.longitudeMin)*getWidth()*echelle/VuePlan.intervalleLongitude)+modifLongitude)  < getWidth();
		if(res) modifLongitude = 0;    
		if((int) (((latitudeMax-VuePlan.latitudeMin)*getHeight()*echelle/VuePlan.intervalleLatitude)+modifLatitude)  < getHeight()) modifLatitude = 0;
		repaint();
	} 
	public void droite() { 
		modifLongitude = (int) (modifLongitude + ((longitudeMax-longitudeMin)/5)*getWidth()); 
		if(modifLongitude >= 0) modifLongitude = 0;
		repaint();
	} 
	public void gauche() { 
		int memoire = (int) (modifLongitude - ((longitudeMax-longitudeMin)/5)*getWidth());  
		if((((longitudeMax-longitudeMin)*getWidth()/intervalleLongitude - memoire) < (getWidth()*echelle))) modifLongitude = memoire;
		repaint();
	} 
	public void haut() { 
		modifLatitude = (int) (modifLatitude + ((latitudeMax-latitudeMin)/5)*getHeight()); 
		if(modifLatitude >= 0) modifLatitude = 0;
		repaint();
		
	} 
	public void bas() { 
		int memoire = (int) (modifLatitude - ((latitudeMax-latitudeMin)/5)*getHeight());   
		if((((latitudeMax-latitudeMin)*getHeight()/intervalleLatitude - memoire) < (getHeight()*echelle))) modifLatitude = memoire;
		repaint();
		
	}

	public void afficherTournee(List<Trajet> trajets) { 
		for (Trajet trajet : trajets) {   
			List<Troncon> troncons = trajet.getTrajet();
			for (Troncon troncon : troncons) {  
				float x1 = troncon.getIntersectionOrigine().getLatitude();
				float y1 = troncon.getIntersectionOrigine().getLongitude();
				float x2 = troncon.getIntersectionDestination().getLatitude();
				float y2 = troncon.getIntersectionDestination().getLongitude();
				tronconsTournee.add(new VueTroncon(x1,y1,x2,y2)); 
			}  
		}
		repaint();
	}  


}
