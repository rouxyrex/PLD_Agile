package vue;
 
import java.util.LinkedList;
import java.util.List; 
import java.awt.Color;
import java.awt.Graphics;
 
import javax.swing.JPanel;

import Modele.Plan; 
import vue.VueTroncon;
import Modele.Troncon;

public class VuePlan extends JPanel {

	private static final long serialVersionUID = 1L;
	private int echelle; 
	private Plan plan;
	private Fenetre f; 
	public static float lattitudeMax;
	public static float lattitudeMin;
	public static float longitudeMax;
	public static float longitudeMin;
	public static float intervalleLattitude;
	public static float intervalleLongitude; 
	
	LinkedList<VueTroncon> tronconsTraces = null;  

	/**
	 * Cree la vue graphique permettant de dessiner plan avec l'echelle e dans la fenetre f
	 * @param plan leplan
	 * @param e l'echelle
	 * @param f la fenetre
	 */
	public VuePlan(Plan plan, int e, Fenetre f) {
		super();
		this.plan = plan;
		tronconsTraces = new LinkedList<VueTroncon>(); 
		this.echelle = e; 
		setLayout(null);
		setBackground(Color.white);
		setSize(1366, 723);
		f.getContentPane().add(this); 
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

	public void afficherPlan() {
		// TODO Auto-generated method stub
		List<Troncon> troncons = plan.getTroncons();  
		/*lattitudeMax = plan.getLattitudeMax();
		lattitudeMin = plan.getLattitudeMin();
		longitudeMax = plan.getLongitudeMax();
		longitudeMin = plan.getLongitudeMin();
		intervalleLattitude = lattitudeMax-lattitudeMin;
		intervalleLongitude = longitudeMax-longitudeMin;*/
		
		lattitudeMax = (float) 45.780518;
		lattitudeMin = (float) 45.727352;
		longitudeMax = (float) 4.9075384;
		longitudeMin = (float) 4.8314376;
		intervalleLattitude = (float) 0.053165436;
		intervalleLongitude = (float) 0.076100826; 
		
		for(int i= 0; i < troncons.size(); i++) {

			float x1 = troncons.get(i).getIntersectionOrigine().getLattitude();
			float y1 = troncons.get(i).getIntersectionOrigine().getLongitude();
			float x2 = troncons.get(i).getIntersectionDestination().getLattitude();
			float y2 = troncons.get(i).getIntersectionDestination().getLongitude();
			tronconsTraces.add(new VueTroncon(x1,y1,x2,y2)); 
		}  
	}

	/*public int getHauteur() {
		return hauteurVue;
	}

	public int getLargeur() {
		return largeurVue;
	}*/

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
