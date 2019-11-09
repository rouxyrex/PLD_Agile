package vue;

import java.util.LinkedList;
import java.util.List;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

import modele.DemandeLivraison;
import modele.Intersection;
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
	public static float latitudeMax = 0;
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
        addMouseListener(new MouseAdapter() {
	         public void mousePressed(MouseEvent me) {
	           onClick(getMousePosition().x, getMousePosition().y);
	         }
	    });

		addMouseMotionListener(new MouseMotionListener() {
			@Override
			public void mouseDragged(MouseEvent e) {
				// TODO Auto-generated method stub
				System.out.println("****" + e.getX());
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				// TODO Auto-generated method stub
				Intersection i = onClick(e.getX(), e.getY());
				if (i != null) f.afficheMessage("La souris est sur l'intersection "+i.getId());

			}
		});
		repaint();
	}

	/**
	 * Methode appelee a chaque fois que VuePlan doit etre redessinee
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

	/**
	 * Methode appelee pour dessiner un plan
	 * Param�tre : le plan a afficher
	 * Retour : rien
	 */

	public void afficherPlan(Plan plan) {
		adressesEnlevement.clear();
		adressesDepot.clear();
		entrepot = null;
		echelle = 1;
		modifLatitude = 0;
		modifLongitude = 0;
		List<Troncon> troncons = plan.getTroncons();
		latitudeMax = plan.getLatitudeMax();
		latitudeMin = plan.getLatitudeMin();
		longitudeMax = plan.getLongitudeMax();
		longitudeMin = plan.getLongitudeMin();
		intervalleLatitude = latitudeMax-latitudeMin;
		intervalleLongitude = longitudeMax-longitudeMin;

		for(int i= 0; i < troncons.size(); i++) {
			tronconsTraces.add(new VueTroncon(troncons.get(i).getIntersectionOrigine(), troncons.get(i).getIntersectionDestination()));
		}
	}

	/**
	 * Methode appelee pour afficher les points d'int�r�t d'une demande de livraison
	 * Param�tre : la demande de livraison a afficher
	 * Retour : rien
	 */

	public void afficherLivraisonDemande(DemandeLivraison dl) {
		adressesDepot.clear();
		adressesEnlevement.clear();
		entrepot = new VueEntrepot(dl.getEntrepot());
		dl.getEntrepot().getLatitude();
		List<Livraison> livraisons = dl.getLivraisons();
		for(int i= 0; i < livraisons.size(); i++) {
			float xDepot = livraisons.get(i).getAdresseDepot().getLatitude();
			float yDepot = livraisons.get(i).getAdresseDepot().getLongitude();
			adressesDepot.add(new VueAdresseDepot(xDepot,yDepot));
			adressesEnlevement.add(new VueAdresseEnlevement(livraisons.get(i).getAdresseEnlevement()));
		}
	}

	/**
	 * Methode appelee pour zommer sur le plan
	 * Param�tre : aucun
	 * Retour : rien
	 */

	public void zoom() {
		echelle = echelle + 1;
		repaint();
	}

	/**
	 * Methode appelee pour dezoomer sur le plan
	 * Param�tre : aucun
	 * Retour : rien
	 */

	public void dezoom() {
		echelle = echelle - 1;
		if(echelle <= 0) echelle = 1;
		boolean res = (int) (((longitudeMax-VuePlan.longitudeMin)*getWidth()*echelle/VuePlan.intervalleLongitude)+modifLongitude)  < getWidth();
		if(res) modifLongitude = 0;
		if((int) (((latitudeMax-VuePlan.latitudeMin)*getHeight()*echelle/VuePlan.intervalleLatitude)+modifLatitude)  < getHeight()) modifLatitude = 0;
		repaint();
	}

	/**
	 * Methodes appelees pour se deplacer � droite, gauche, haut, bas sur le plan
	 * Param�tre : aucun
	 * Retour : rien
	 */

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

	/**
	 * Methode appelee pour afficher une tournee (tous les troncons correspondant � la tournee)
	 * Param�tre : la liste des trajets a afficher
	 * Retour : rien
	 */

	public void afficherTournee(List<Trajet> trajets) {
		for (Trajet trajet : trajets) {
			List<Troncon> troncons = trajet.getTrajet();
			for (Troncon troncon : troncons) {
				tronconsTournee.add(new VueTroncon(troncon.getIntersectionOrigine(), troncon.getIntersectionDestination()));
			}
		}
		repaint();
	}

	/**
	 * Methode appelee lors d'un click sur le plan
	 * Param�tre : les coordonnees (x et y) du point sur lequel on a click�
	 * Retour : rien
	 */

	public Intersection onClick(int x, int y) {

		for(int i = 0; i < adressesDepot.size(); i++) {
			if(adressesDepot.get(i).onClick(x, y) != null) return adressesDepot.get(i).onClick(x, y);
	    }

		for(int i = 0; i < adressesEnlevement.size(); i++) {
			if(adressesEnlevement.get(i).onClick(x, y) != null) return adressesEnlevement.get(i).onClick(x, y);
	    }

		if(entrepot != null && entrepot.onClick(x, y) != null) return entrepot.onClick(x, y);

		for (VueTroncon troncon : tronconsTraces) {
	    	if(troncon.onClick(x, y) != null) return troncon.onClick(x, y);
	    }
		return null;
	}


}
