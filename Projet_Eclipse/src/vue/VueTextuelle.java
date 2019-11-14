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
import java.util.List; 
import java.util.Observable; 
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import controleur.Controleur;
import javafx.util.Pair;
import modele.DemandeLivraison;
import modele.Intersection;
import modele.Livraison;
import modele.Plan;
import modele.Tournee;
import modele.Trajet; 

/** Contient la representation graphique textuelle : les boutons d'option pour Modifier, Ajouter, Supprimer ou inverser des livraisons,
 *  la liste des livraisons et les informations sur celles-ci
*/
public class VueTextuelle extends JPanel implements Observer {

	private static final long serialVersionUID = 1L;
	Fenetre fenetre;
	private Plan plan;
	private DemandeLivraison dl;
	private Tournee tournee;


	Color[] colors2 = {Color.cyan, Color.green, Color.RED, Color.magenta, Color.ORANGE, Color.YELLOW, Color.PINK, new Color((float) 1.0, (float) 0.1, (float) 0.4), new Color((float) 0.9, (float) 0.5, (float) 0.2), new Color((float) 0.8, (float) 0.5, (float) 0.3), new Color((float) 0.7, (float) 1.0, (float) 0.7), new Color((float) 0.6, (float) 0.3, (float) 0.6), new Color((float) 0.1, (float) 0.4, (float) 0.2), new Color((float) 0.9, (float) 0.8, (float) 0.9), new Color((float) 0.3, (float) 0.0, (float) 0.4)};
	LinkedList<Color> colors = new LinkedList<Color>();
	LinkedList<Color> colorsSave = new LinkedList<Color>();
	protected static final String AJOUT = "Ajouter une livraison";
	protected static final String SUPRESSION = "Suprimmer";
	protected static final String UNDO = "undo";
	protected static final String REDO = "redo";
	protected static final String VALIDER = "Valider l'ajout de livraison";
	private EcouteurDeBoutons ecouteurDeBoutons;
	private ArrayList<JButton> boutons;
	private final String[] intitulesBoutons = new String[]{AJOUT, SUPRESSION, UNDO, REDO, VALIDER};
	private final int hauteurBouton = 50;
	LinkedList<VuePointInteret> stock = null;
	LinkedList<VuePointInteret> vueLivraisons = null;
	private boolean supprimer = false;
	private boolean ajouter = false;
	JLabel textZoneDepot = new JLabel("Temps depot : ");
	JLabel textZoneEnlevement = new JLabel("Temps enlevement : ");
	JTextArea textZone = new JTextArea("0");
	JTextArea textZone2 = new JTextArea("0");
	Intersection enlevementAjout;
	Intersection depotAjout;
	private Pair<Integer, Intersection> interAvantEnlevement;
	private Pair<Integer,Intersection> interAvantDepot;
	private boolean ajouter2 = false;
	

	public VueTextuelle(Fenetre fenetre, Plan plan, DemandeLivraison demandeLivraison, Tournee tournee, Controleur controleur) {

		this.fenetre = fenetre;
		this.plan = plan;
		this.dl = demandeLivraison;
		this.tournee = tournee;

		plan.addObserver(this); // this observe plan
		demandeLivraison.addObserver(this); // this observe demandeLivraison
		tournee.addObserver(this); // this observe tournee
		
		for(int i = 0; i < colors2.length; i++) {
			colors.add(colors2[i]);
			colorsSave.add(colors2[i]);
		}

		vueLivraisons = new LinkedList<VuePointInteret>();
		stock = new LinkedList<VuePointInteret>();
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
				if(supprimer|| ajouter || ajouter2)  onMotion(e.getX(), e.getY()); 
			}

			@Override
			public void mouseDragged(MouseEvent e) { }
		});
		
		 addMouseListener(new MouseAdapter() {
	         public void mousePressed(MouseEvent me) {
	        	 if(supprimer) {
	        		 //ici à partir de l'intersection on se débrouille pour récupérer la livraison
	        		 Livraison l = onClick2(getMousePosition().x, getMousePosition().y); 
	        		 if(l != null) { 
		        		controleur.supprimerLivraison(l);
		        		supprimer = false;
		        		for(JButton bouton : boutons) {
		        			bouton.setEnabled(true);
		        		}
	        		 }
	        	 }
	        	 
	        	 if(ajouter2) {
		        		//on stocke la deuxieme intersection
		        		interAvantDepot = onClick(getMousePosition().x, getMousePosition().y);
		        		 if(interAvantDepot != null) { 
			        		ajouter2 = false; 
			        		validerAjoutLivraison(controleur, false);
		        		 }
		        	 }
		        	 if(ajouter) {
		        		 //on stocke la premiere intersection
		        		 interAvantEnlevement = onClick(getMousePosition().x, getMousePosition().y);  
		        		 if(interAvantEnlevement != null) {  
			        		 ajouter = false;
			        		 ajouter2 = true;
			        		 fenetre.afficheMessage("Veuillez cliquer sur l'intersection que vous souhaitez avant le depot");
		        		 }
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
		 for (int i = 1; i < vueLivraisons.size(); i++) {
			if(supprimer || ajouter || ajouter2) vueLivraisons.get(i).onMotion(x, y); 
		 }
		 repaint();
	}

	protected Pair<Integer, Intersection> onClick (int x, int y) {
		for (int i = 1; i < vueLivraisons.size(); i++) {
			 
			Pair<Integer, Intersection> inter = vueLivraisons.get(i).onClick(x, y);  
			if(inter != null) { 
				repaint();
				return inter;
			}  
	    } 
		return null;
	}
	
	protected Livraison onClick2 (int x, int y) {
		for (int i = 1; i < vueLivraisons.size(); i++) {
			 
			Livraison l = vueLivraisons.get(i).onClick2(x, y);  
			if(l != null) { 
				return l;
			}  
	    } 
		return null;
	}


	public void initialiserVueDemandeLivraison() {
		for (JButton bouton : boutons){
			bouton.setVisible(true);
			bouton.setEnabled(true);
		}
		boutons.get(4).setVisible(false);

		vueLivraisons.add(new VuePointInteret(null, null, Color.LIGHT_GRAY, -1, -1));
		vueLivraisons.add(new VuePointInteret(null, dl.getEntrepot(), Color.LIGHT_GRAY, -1, -1));
		for(int i = 0; i <  dl.getLivraisons().size(); i++) {
			
			VuePointInteret ptinterretDepot = new VuePointInteret(dl.getLivraisons().get(i), dl.getLivraisons().get(i).getAdresseDepot().getValue(), colors.getFirst(), 0, dl.getLivraisons().get(i).getDureeDepot());
			VuePointInteret ptinterretEnelevement = new VuePointInteret(dl.getLivraisons().get(i), dl.getLivraisons().get(i).getAdresseEnlevement().getValue(), colors.getFirst(), 1, dl.getLivraisons().get(i).getDureeEnlevement());
			colors.removeFirst();
			vueLivraisons.add(ptinterretDepot);
			vueLivraisons.add(ptinterretEnelevement);
			stock.add(ptinterretDepot);
			stock.add(ptinterretEnelevement);
		}
	}

	public void initialiserVueTournee() { 
		//copier stock et ne pas le modifier derriere
		for(JButton bouton : boutons) {
			bouton.setEnabled(true);
		}
		LinkedList<VuePointInteret> nouvStock = (LinkedList<VuePointInteret>) stock.clone();
		
		vueLivraisons.clear();
		List<Trajet> trajets = tournee.getParcours();
		vueLivraisons.add(new VuePointInteret(null, null, Color.LIGHT_GRAY, -1, -1));
		vueLivraisons.add(new VuePointInteret(null, dl.getEntrepot(), Color.LIGHT_GRAY, -1, -1));
		for(int i = 0; i < trajets.size(); i++) {
			for(int j = 0; j < nouvStock.size(); j++) {
				if(nouvStock.get(j).getIntersection().getId().equals(trajets.get(i).getIntersectionOrigine().getValue().getId())) {
					vueLivraisons.add(nouvStock.get(j));
					nouvStock.remove(nouvStock.get(j));
				}
				if(nouvStock.get(j).getIntersection().getId().equals(trajets.get(i).getIntersectionDestination().getValue().getId())) {
					vueLivraisons.add(nouvStock.get(j));
					nouvStock.remove(nouvStock.get(j));
				}
			}
 
		}  
		repaint();
	}

	public void effacerVueDemandeLivraison() {
		vueLivraisons.clear();
		
		colors = (LinkedList<Color>)colorsSave.clone();
		
		for (JButton bouton : boutons){
			bouton.setVisible(false);
		}

	} 

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		float yOrigine = 130;
		int nbLivraisons =  vueLivraisons.size()*2;
		float size = (this.getHeight()-150)/nbLivraisons*2;
		for (int i = 0; i < nbLivraisons/2; i++) {
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
		fenetre.setAjouterValue(false);
		supprimer = true;
		for(JButton bouton : boutons) {
			bouton.setEnabled(false);
		}
	}

	public void ajouterLivraison() {
		supprimer = false;
		fenetre.setAjouterValue(true);
		fenetre.afficheMessage("Cliquer sur l'adresse d'enlevement");
		for(JButton bouton : boutons) {
			bouton.setEnabled(false);
		}
	}

	public void transfertIntersection(Intersection enlevement, Intersection depot) {

		fenetre.afficheMessage("Entrez les temps (depot et enlevement), puis appuyez sur valider une livraison, puis cliquez sur les intersections précédante enlevement et depot");
		this.enlevementAjout = enlevement;
		this.depotAjout = depot;
		textZone.setVisible(true);
		textZone2.setVisible(true);
		textZoneDepot.setVisible(true);
		textZoneEnlevement.setVisible(true);
		ajouter = true;
		repaint();
	} 
	
	public void validerAjoutLivraison(Controleur c, boolean valider) {
		if(!valider) {
			boutons.get(4).setVisible(true);
			for(JButton bouton : boutons) {
				bouton.setEnabled(true);
			}
		}
		if(valider) {
			c.validerAjoutLivraison(new Livraison(enlevementAjout, depotAjout, Integer.parseInt(textZone.getText()), Integer.parseInt(textZone2.getText())), interAvantEnlevement, interAvantDepot);
			boutons.get(4).setVisible(false);
			textZone.setVisible(false);
			textZone2.setVisible(false);
			textZoneDepot.setVisible(false);
			textZoneEnlevement.setVisible(false);
		}
		
	}

	public void ajouterVueDemandeLivraison(Livraison l) {  
		vueLivraisons.add(new VuePointInteret(l, l.getAdresseDepot().getValue(), colors.getFirst(), 0, l.getDureeDepot())) ;
		vueLivraisons.add(new VuePointInteret(l, l.getAdresseEnlevement().getValue(), colors.getFirst(), 1, l.getDureeEnlevement())) ;
		stock.add(new VuePointInteret(l, l.getAdresseDepot().getValue(), colors.getFirst(), 0, l.getDureeDepot())) ;
		stock.add(new VuePointInteret(l, l.getAdresseEnlevement().getValue(), colors.getFirst(), 1, l.getDureeEnlevement())) ;
		colors.removeFirst();
		repaint();
	}
	
	public void supprimerVueDemandeLivraison(Livraison l) { 
		
		int i1 = 0;
		int i2 = 0;
		
		for(int i = 2; i < vueLivraisons.size(); i++) {  
			
			if(vueLivraisons.get(i).getLivraison().getId() == l.getId()){ 
				if(i1 == 0) {
					i1 = i;
				}
				else {
					i2 = i;
				}
			}
		}
		
		colors.addFirst(vueLivraisons.get(i2).getColor());
		vueLivraisons.remove(vueLivraisons.get(i2));
		vueLivraisons.remove(vueLivraisons.get(i1));
		
		i1 = 0;
		i2 = 0;
		for(int i = 0; i < stock.size(); i++) {  
			
			if(stock.get(i).getLivraison().getId() == l.getId()){ 
				if(i1 == 0) {
					i1 = i;
				}
				else {
					i2 = i;
				}
			}
		}

		stock.remove(stock.get(i2)); 
		stock.remove(stock.get(i1)); 
		
		/*for(int i = 2; i < vueLivraisons.size(); i++) {  
			
			if(vueLivraisons.get(i).getLivraison().getId() == l.getId()){ 
				vueLivraisons.remove(vueLivraisons.get(i));  
				stock.remove(vueLivraisons.get(i));  
			}
		}*/
	}

}
