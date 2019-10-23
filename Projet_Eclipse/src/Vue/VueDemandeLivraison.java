package Vue;

import java.util.LinkedList;
import java.util.List; 
import java.awt.Color;
import java.awt.Graphics;
 
import javax.swing.JPanel;

import Modele.DemandeLivraison;
import Modele.Livraison; 
import Vue.VueAdresseDepot;
import Vue.VueAdresseEnlevement;

public class VueDemandeLivraison extends JPanel {

	private static final long serialVersionUID = 1L;
	private int echelle; 
	private DemandeLivraison dl;
	private Fenetre f;  
	
	LinkedList<VueAdresseDepot> adressesDepot = null;
	LinkedList<VueAdresseEnlevement> adressesEnlevement = null;
	

	/**
	 * Cree la vue graphique permettant de dessiner plan avec l'echelle e dans la fenetre f
	 * @param plan leplan
	 * @param e l'echelle
	 * @param f la fenetre
	 */
	public VueDemandeLivraison(/*Plan plan,*/ int e, Fenetre f) {
		super(); 
		adressesDepot = new LinkedList<VueAdresseDepot>();
		adressesEnlevement = new LinkedList<VueAdresseEnlevement>(); 
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
		g.setColor(Color.RED);
		g.drawLine(0, 0, 200, 200);
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