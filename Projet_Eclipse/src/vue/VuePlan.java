package vue;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import java.util.LinkedList;
import java.util.List;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JTextArea;

import controleur.Controleur;
import modele.DemandeLivraison;
import modele.Intersection;
import modele.Livraison;
import modele.Plan;
import modele.Tournee;
import modele.Trajet;
import vue.VueTroncon;
import modele.Troncon;

public class VuePlan extends JPanel implements Observer {

	private static final long serialVersionUID = 1L;
	private int echelle;
	private int modifLatitude;
	private int modifLongitude;
	private JPanel cadreBoutons;
	
	private Plan plan;
	private DemandeLivraison dl;
	private Tournee tournee;
	private Fenetre f;
	
	
	public static float latitudeMax = 0;
	public static float latitudeMin;
	public static float longitudeMax;
	public static float longitudeMin;
	public static float intervalleLatitude;
	public static float intervalleLongitude; 
	
	protected static final String ZOOM = "Zoom";
	protected static final String DEZOOM = "Dezoom";
	protected static final String DROITE = "Droite";
	protected static final String GAUCHE = "Gauche";
	protected static final String HAUT = "Haut";
	protected static final String BAS = "Bas";
	private EcouteurDeBoutons ecouteurDeBoutons;
	private ArrayList<JButton> boutons;
	private final String[] intitulesBoutons = new String[]{ZOOM, DEZOOM, DROITE, GAUCHE, HAUT, BAS}; //, CHARGER_DEMANDE_LIVRAISON, CALCULER_TOURNEE, GENERER_FEUILLE_ROUTE};
	private final int hauteurBouton = 50;
	
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
	public VuePlan(Fenetre f, Plan plan, DemandeLivraison demandeLivraison, Tournee tournee, Controleur c) {
		super();
		
		this.f = f;
		this.plan = plan;
		this.dl = demandeLivraison;
		this.tournee = tournee;
		
		plan.addObserver(this); // this observe plan
		demandeLivraison.addObserver(this); // this observe demandeLivraison
		tournee.addObserver(this); // this observe tournee
		
		tronconsTraces = new LinkedList<VueTroncon>(); 
		tronconsTournee = new LinkedList<VueTroncon>();
		adressesDepot = new LinkedList<VueAdresseDepot>();
		adressesEnlevement = new LinkedList<VueAdresseEnlevement>();  
		echelle = 1;
		modifLatitude = 0;
		modifLongitude = 0;
		this.setPreferredSize(new Dimension(300,100));
		creeBoutons(c);

	/*	JScrollBar scrollBarVertical = new JScrollBar();
		scrollBarVertical.setOrientation(1);
		scrollBarVertical.setVisible(true);
		this.add(scrollBarVertical, BorderLayout.EAST);
		JScrollBar scrollBarHorizontal = new JScrollBar();
		scrollBarHorizontal.setOrientation(0);
		scrollBarHorizontal.setVisible(true);
		this.add(scrollBarHorizontal, BorderLayout.SOUTH);*/

		setBackground(Color.white);
        addMouseListener(new MouseAdapter() {
	         public void mousePressed(MouseEvent me) {
	           onClick(getMousePosition().x, getMousePosition().y);
	         }
	    });

		addMouseMotionListener(new MouseMotionListener() {
			@Override
			public void mouseDragged(MouseEvent e) {
				// TODO Auto-generated method stub
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				// TODO Auto-generated method stub
				Intersection i = onClick(e.getX(), e.getY());
				if (i != null) f.afficheMessage("La souris est sur l'intersection "+i.getId());

			}
		});
		f.getContentPane().add(this,  BorderLayout.CENTER);
		
		repaint();
	} 
	
	private void creeBoutons(Controleur controleur){
		ecouteurDeBoutons = new EcouteurDeBoutons(controleur, f);
		boutons = new ArrayList<JButton>();
		for (String intituleBouton : intitulesBoutons){
			JButton bouton = new JButton(intituleBouton);
			boutons.add(bouton);
			Image img = null;
			try {
				img = ImageIO.read(new FileInputStream("src/vue/Images/"+intituleBouton+".png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			bouton.setIcon(new ImageIcon(img));
			bouton.setLocation(0,(boutons.size()-1)*hauteurBouton);
			bouton.addActionListener(ecouteurDeBoutons);
		//	cadreBoutons.add(bouton);
			this.add(bouton);
			bouton.setVisible(false);
		}
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

	
	/**
	 * Methode appelee par les objets observes par this a chaque fois qu'ils ont ete modifies
	 */
	@Override
	public void update(Observable o, Object arg) {
		repaint();
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

	public void initialiserVuePlan() {
		
		for (JButton bouton : boutons){
		      bouton.setVisible(true);
		}
		
		// TODO Auto-generated method stub
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
	
	public void effacerVuePlan() {
		tronconsTraces.clear();
		echelle = 1;
	    modifLatitude = 0;
	    modifLongitude = 0;
	    for (JButton bouton : boutons){
	        bouton.setVisible(false);
	    }
	}
	
	public void initialiserVueDemandeLivraison() {
		entrepot = new VueEntrepot(dl.getEntrepot());
		dl.getEntrepot().getLatitude();
		List<Livraison> livraisons = dl.getLivraisons();
		for(int i= 0; i < livraisons.size(); i++) {
			float xDepot = livraisons.get(i).getAdresseDepot().getValue().getLatitude();
			float yDepot = livraisons.get(i).getAdresseDepot().getValue().getLongitude();
			float xEnlevement = livraisons.get(i).getAdresseEnlevement().getValue().getLatitude();
			float yEnlevement = livraisons.get(i).getAdresseEnlevement().getValue().getLongitude();
			adressesDepot.add(new VueAdresseDepot(xDepot,yDepot));
			adressesEnlevement.add(new VueAdresseEnlevement(livraisons.get(i).getAdresseEnlevement().getValue()));
		}
	} 
	
	public void effacerVueDemandeLivraison() {
		adressesEnlevement.clear();
		adressesDepot.clear();
		entrepot = null;
	}
	
	
	/**
	 * Methode appelee pour initialiser le vue de la tournee
	 * Parametre : rien
	 * Retour : rien
	 */
	public void initialiserVueTournee() {
		
		List<Trajet> trajets = tournee.getParcours();
		
		for (Trajet trajet : trajets) {
			List<Troncon> troncons = trajet.getTrajet();
			for (Troncon troncon : troncons) {
				tronconsTournee.add(new VueTroncon(troncon.getIntersectionOrigine(), troncon.getIntersectionDestination()));
			}
		}
	}
	
	public void effacerVueTournee() {
		tronconsTournee.clear();
	}
	
	/**
	 * Methode appelee pour zommer sur le plan
	 * Parametre : aucun
	 * Retour : rien
	 */
	public void zoom() {
		echelle = echelle + 1;
		repaint();
	}
	
	
	/**
	 * Methode appelee pour dezoomer sur le plan
	 * Parametre : aucun
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
	 * Methodes appelees pour se deplacer a droite, gauche, haut, bas sur le plan
	 * Parametre : aucun
	 * Retour : rien
	 */
	public void droite() {
		modifLongitude = (int) (modifLongitude + ((longitudeMax-longitudeMin)/3)*getWidth());
		if(modifLongitude >= 0) modifLongitude = 0;
		repaint();
	}
	public void gauche() {
		int memoire = (int) (modifLongitude - ((longitudeMax-longitudeMin)/3)*getWidth());
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
	 * Methode appelee lors d'un click sur le plan
	 * Parametre : les coordonnees (x et y) du point sur lequel on a clicke
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
