package vue;

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
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel; 

import controleur.Controleur;
import modele.DemandeLivraison; 
import modele.Livraison;
import modele.Plan;
import modele.Tournee;

public class VueTextuelle extends JPanel implements Observer {
	 
	private static final long serialVersionUID = 1L;
	Fenetre fenetre;
	private Plan plan;
	private DemandeLivraison dl;
	private Tournee tournee; 
	
	
	Color[] colors = {Color.cyan, Color.BLUE, Color.green, Color.RED, Color.magenta, Color.LIGHT_GRAY, Color.ORANGE, Color.YELLOW, Color.PINK, Color.white};
	
	protected static final String MODIFIER = "Modifier la tourn�e";
	protected static final String AJOUT = "Ajouter une livraison";
	protected static final String SUPRESSION = "Suprimer";
	protected static final String INVERSER = "Inverser"; 
	private EcouteurDeBoutons ecouteurDeBoutons;
	private ArrayList<JButton> boutons;
	private final String[] intitulesBoutons = new String[]{MODIFIER, AJOUT, SUPRESSION, INVERSER}; //, CHARGER_DEMANDE_LIVRAISON, CALCULER_TOURNEE, GENERER_FEUILLE_ROUTE};
	private final int hauteurBouton = 50;  
	LinkedList<VueLivraison> vueLivraisons = null;
	private boolean onMotion = false;
	private boolean supprimer = false;
	
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
		addMouseMotionListener(new MouseMotionListener() {
			@Override
			public void mouseDragged(MouseEvent e) {
				// TODO Auto-generated method stub 
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				// TODO Auto-generated method stub
				if(onMotion) onMotion(e.getX(), e.getY()); 
			}
		});
		 addMouseListener(new MouseAdapter() {
	         public void mousePressed(MouseEvent me) {
	        	 if(supprimer) {
	        		 controleur.supprimerLivraison(onClick(getMousePosition().x, getMousePosition().y));
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
			if(i != 0) bouton.setEnabled(false);
			this.add(bouton);
			bouton.setVisible(false);
		}
	}

	protected void onMotion(int x, int y) {
		// TODO Auto-generated method stub
		 for (int i = 1; i < vueLivraisons.size(); i++) {   
			vueLivraisons.get(i).onMotion(x, y);
		 }  
		 repaint(); 
	}
	
	protected Livraison onClick (int x, int y) {  
		for (int i = 1; i < vueLivraisons.size(); i++) {  
			Livraison l = vueLivraisons.get(i).onClick(x, y);
			if(l != null) {
				vueLivraisons.remove(vueLivraisons.get(i));
				repaint();
				return l;
			}
	    }   
		return null;
	} 
	
	
	public void initialiserVueDemandeLivraison() {
		for (JButton bouton : boutons){ 
			bouton.setVisible(true); 
		}
		
		vueLivraisons.add(new VueLivraison(new Livraison(dl.getEntrepot(), null, -1, -1)));
		for(Livraison livraison : dl.getLivraisons()) { 
			vueLivraisons.add(new VueLivraison(livraison)); 
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
			int k = i-1;
			if(k < 0) k = 0;
			vueLivraisons.get(i).dessiner(g, 20, (int) yOrigine, size, this.getWidth(), this.getHeight(), colors[k]);  
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

	public void modifierTournee() {
		// TODO Auto-generated method stub
		for(int i = 0; i < boutons.size(); i++) {
			boutons.get(i).setEnabled(true);
		}
	}

	public void supprimerLivraison() {
		// TODO Auto-generated method stub
		onMotion = true;
		supprimer = true;
	}
 
}
