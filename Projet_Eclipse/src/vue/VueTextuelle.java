package vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;
import java.util.Observable;
//import javafx.util.Pair<K,V>;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import controleur.Controleur;
import modele.DemandeLivraison;
import modele.Intersection;
import modele.Livraison;
import modele.Plan;
import modele.Tournee;

public class VueTextuelle extends JPanel implements Observer {

	private static final long serialVersionUID = 1L;
	Fenetre fenetre;
	private Plan plan;
	private DemandeLivraison dl;
	private Tournee tournee;


	Color[] colors = {Color.cyan, Color.green, Color.RED, Color.magenta, Color.ORANGE, Color.YELLOW, Color.PINK, new Color((float) 1.0, (float) 0.1, (float) 0.4), new Color((float) 0.9, (float) 0.5, (float) 0.2), new Color((float) 0.8, (float) 0.5, (float) 0.3), new Color((float) 0.7, (float) 1.0, (float) 0.7), new Color((float) 0.6, (float) 0.3, (float) 0.6), new Color((float) 0.1, (float) 0.4, (float) 0.2), new Color((float) 0.9, (float) 0.8, (float) 0.9), new Color((float) 0.3, (float) 0.0, (float) 0.4)};

	protected static final String AJOUT = "Ajouter une livraison";
	protected static final String SUPRESSION = "Suprimmer";
	protected static final String UNDO = "undo";
	protected static final String REDO = "redo";
	protected static final String VALIDER = "Valider l'ajout de livraison";
	private EcouteurDeBoutons ecouteurDeBoutons;
	private ArrayList<JButton> boutons;
	private final String[] intitulesBoutons = new String[]{AJOUT, SUPRESSION, UNDO, REDO, VALIDER};
	private final int hauteurBouton = 50;
	LinkedList<VueLivraison> vueLivraisons = null;
	private boolean supprimer = false;
	private boolean ajouter = false;
	JLabel textZoneDepot = new JLabel("Temps d�p�t : ");
	JLabel textZoneEnlevement = new JLabel("Temps enlevement : ");
	JTextArea textZone = new JTextArea("0");
	JTextArea textZone2 = new JTextArea("0");
	Intersection enlevementAjout;
	Intersection depotAjout;

	public VueTextuelle(Fenetre fenetre, Plan plan, DemandeLivraison demandeLivraison, Tournee tournee, Controleur controleur) {

		this.fenetre = fenetre;
		this.plan = plan;
		this.dl = demandeLivraison;
		this.tournee = tournee;

		plan.addObserver(this); // this observe plan
		demandeLivraison.addObserver(this); // this observe demandeLivraison

		vueLivraisons = new LinkedList<VueLivraison>();
		this.setPreferredSize(new Dimension(300,100));
		setBorder(BorderFactory.createTitledBorder("Demande de livraison"));
		fenetre.getContentPane().add(this, BorderLayout.EAST);
		creeBoutons(controleur);
		add(textZoneDepot);
		add(textZone);
		add(textZoneEnlevement);
		add(textZone2);
		textZone.setVisible(false);
		textZone2.setVisible(false);
		textZoneDepot.setVisible(false);
		textZoneEnlevement.setVisible(false);
		addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseMoved(MouseEvent e) {
				// TODO Auto-generated method stub
				if(supprimer|| ajouter) onMotion(e.getX(), e.getY());
			}

			@Override
			public void mouseDragged(MouseEvent e) { }
		});
		 addMouseListener(new MouseAdapter() {
	         public void mousePressed(MouseEvent me) {
	        	 if(supprimer) {
	        		 controleur.supprimerLivraison(onClick(getMousePosition().x, getMousePosition().y));
	        		 supprimer = false;
	        	 }
	         }
	    });
		repaint();
	}

	private void creeBoutons(Controleur controleur){
		ecouteurDeBoutons = new EcouteurDeBoutons(controleur, fenetre);
		boutons = new ArrayList<JButton>();
		for (int i = 0; i < intitulesBoutons.length; i++){
			JButton bouton = new JButton(intitulesBoutons[i]);
			boutons.add(bouton);
			bouton.setLocation(0,(boutons.size()-1)*hauteurBouton);
			bouton.addActionListener(ecouteurDeBoutons);
			this.add(bouton);
			bouton.setVisible(false);
		}
	}

	protected void onMotion(int x, int y) {
		// TODO Auto-generated method stub
		 for (int i = 1; i < vueLivraisons.size(); i++) {
			if(supprimer) vueLivraisons.get(i).onMotion(x, y, 0);
			if(ajouter)  vueLivraisons.get(i).onMotion(x, y, 1);
		 }
		 repaint();
	}

	protected Livraison onClick (int x, int y) {
		for (int i = 1; i < vueLivraisons.size(); i++) {
			int choix = -1;
			if(supprimer) choix = 0;
			if(ajouter) choix = 1;
			Map<Livraison, Intersection> map = vueLivraisons.get(i).onClick(x, y, choix);
			Livraison l ;
			if(map != null) {
				for (Map.Entry<Livraison, Intersection> entry : map.entrySet())
				{
					l =  entry.getKey();
					if(l != null) {
						vueLivraisons.remove(vueLivraisons.get(i));
						repaint();
						return l;
					}
				}
			}

	    }
		return null;
	}


	public void initialiserVueDemandeLivraison() {
		for (JButton bouton : boutons){
			bouton.setVisible(true);
		}
		boutons.get(4).setVisible(false);

		vueLivraisons.add(new VueLivraison(new Livraison(dl.getEntrepot(), null, -1, -1), Color.LIGHT_GRAY));
		for(int i = 0; i <  dl.getLivraisons().size(); i++) {
			vueLivraisons.add(new VueLivraison(dl.getLivraisons().get(i), colors[i]));
		}
	}

	public void effacerVueDemandeLivraison() {
		vueLivraisons.clear();
		for (JButton bouton : boutons){
			bouton.setVisible(false);
		}

	}


	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		float yOrigine = 130;
		int nbLivraisons =  vueLivraisons.size();
		float size = (this.getHeight()-150)/nbLivraisons;
		for (int i = 0; i < nbLivraisons; i++) {
			vueLivraisons.get(i).dessiner(g, 20, (int) yOrigine, size, this.getWidth(), this.getHeight());
			yOrigine += size;
	    }
	}

	/**
	 * Methode appelee par les objets observes par this a chaque fois qu'ils ont ete modifies
	 */
	@Override
	public void update(Observable o, Object arg) {
		repaint();
	}

	public void supprimerLivraison() {
		// TODO Auto-generated method stub
		fenetre.setAjouterValue(false);
		supprimer = true;
	}

	public void ajouterLivraison() {
		supprimer = false;
		fenetre.setAjouterValue(true);
		fenetre.afficheMessage("Cliquer sur l'adresse d'enlevement");
	}

	public void transfertIntersection(Intersection enlevement, Intersection depot) {

		fenetre.afficheMessage("Veuillez entrer les temps de d�pot et d'enlevement, une fois entr�s, appuyer � nouveau sur ajouter une livraison");
		boutons.get(4).setVisible(true);
		textZone.setVisible(true);
		textZone2.setVisible(true);
		textZoneDepot.setVisible(true);
		textZoneEnlevement.setVisible(true);
		repaint();
	}

	public void validerAjout(Controleur c) {
		textZone.setVisible(false);
		textZone2.setVisible(false);
		boutons.get(4).setVisible(false);
		textZoneDepot.setVisible(false);
		textZoneEnlevement.setVisible(false);
		fenetre.afficheMessage(" ");
		c.validerAjoutLivraison(new Livraison(enlevementAjout, depotAjout, Integer.parseInt(textZone.getText()), Integer.parseInt(textZone2.getText())));
	}

}
