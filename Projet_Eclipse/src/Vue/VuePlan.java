package Vue;

import java.util.LinkedList;
import java.util.List;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
 
import javax.swing.JPanel;

import Modele.DemandeLivraison;
import Modele.Livraison;
import Modele.Plan; 
import Vue.VueTroncon;
import Modele.Troncon;

public class VuePlan extends JPanel {

	private static final long serialVersionUID = 1L;
	private int echelle; 
	private Plan plan;
	private DemandeLivraison dl;
	private Fenetre f; 
	public static float lattitudeMax;
	public static float lattitudeMin;
	public static float longitudeMax;
	public static float longitudeMin;
	public static float intervalleLattitude;
	public static float intervalleLongitude; 
	
	Color[] colors = {Color.cyan, Color.BLUE, Color.DARK_GRAY, Color.green, Color.LIGHT_GRAY, Color.magenta, Color.RED, Color.ORANGE, Color.YELLOW, Color.WHITE};
	LinkedList<VueTroncon> tronconsTraces = null;  
	LinkedList<VueAdresseDepot> adressesDepot = null;
	LinkedList<VueAdresseEnlevement> adressesEnlevement = null;

	/**
	 * Cree la vue graphique permettant de dessiner plan avec l'echelle e dans la fenetre f
	 * @param plan leplan
	 * @param e l'echelle
	 * @param f la fenetre
	 */
	public VuePlan(/*Plan plan,*/ int e, Fenetre f) {
		super();
	//	this.plan = plan;
		tronconsTraces = new LinkedList<VueTroncon>(); 
		adressesDepot = new LinkedList<VueAdresseDepot>();
		adressesEnlevement = new LinkedList<VueAdresseEnlevement>(); 
		this.echelle = e; 
		setLayout(null);
		setBackground(Color.white);
		setSize(1366, 723);
		f.getContentPane().add(this,  BorderLayout.CENTER); 
		this.f = f;
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
	    	troncon.dessiner(g, f.getWidth(), f.getHeight()); 
	    }   
		 
		for(int i = 0; i < adressesDepot.size(); i++) {
			g.setColor(colors[i]);  
			adressesDepot.get(i).dessiner(g, f.getWidth(), f.getHeight());
	    }  
		
		for(int i = 0; i < adressesEnlevement.size(); i++) {
			g.setColor(colors[i]);  
			adressesEnlevement.get(i).dessiner(g, f.getWidth(), f.getHeight());
	    }   
	//	this.g = g; 

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

	public void afficherPlan(Plan plan/*, DemandeLivraison dl*/) {
		this.plan = plan;
		// TODO Auto-generated method stub
		List<Troncon> troncons = plan.getTroncons();  
		lattitudeMax = plan.getLatitudeMax();
		lattitudeMin = plan.getLatitudeMin();
		longitudeMax = plan.getLongitudeMax();
		longitudeMin = plan.getLongitudeMin();
		intervalleLattitude = lattitudeMax-lattitudeMin;
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
		this.dl = dl; 
		adressesDepot.clear();
		adressesEnlevement.clear();
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

	/**
	 * Methode appelee par les objets observes par this a chaque fois qu'ils ont ete modifies
	 */
/*	@Override
	public void update(Observable o, Object arg) {
		if (arg != null){ // arg est une forme qui vient d'etre ajoutee a plan
			Forme f = (Forme)arg;
			f.addObserver(this);  // this observe la forme f
		}
		repaint();
	}*/

	/**
	 * Methode appelee par l'objet visite (un cercle) a chaque fois qu'il recoit le message affiche
	 */
/*	@Override
	public void affiche(Cercle c) {
		int r = echelle*c.getRayon();
		if (c.getEstSelectionne())
			g.drawOval(echelle*c.getCentre().getX()-r, echelle*c.getCentre().getY()-r, 2*r, 2*r);
		else
			g.fillOval(echelle*c.getCentre().getX()-r, echelle*c.getCentre().getY()-r, 2*r, 2*r);
	}*/

	/**
	 * Methode appelee par l'objet visite (un rectangle) a chaque fois qu'il recoit le message affiche
	 */
/*	@Override
	public void affiche(Rectangle r) {
		if (r.getEstSelectionne())
			g.drawRect(echelle*r.getCoin().getX(),echelle*r.getCoin().getY(),echelle*(r.getLargeur()),echelle*(r.getHauteur()));
		else
			g.fillRect(echelle*r.getCoin().getX(),echelle*r.getCoin().getY(),echelle*(r.getLargeur()),echelle*(r.getHauteur()));
	}*/

}