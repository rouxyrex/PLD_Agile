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
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel; 

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
	private EcouteurDeBoutons ecouteurDeBoutons;
	private ArrayList<JButton> boutons;
	private final String[] intitulesBoutons = new String[]{AJOUT, SUPRESSION, UNDO, REDO};
	private final int hauteurBouton = 50;  
	LinkedList<VueLivraison> vueLivraisons = null; 
	private boolean supprimer = false;
	private boolean ajouter = false;
	private boolean ajouter2 = false;
	private boolean ajout = false;
	private JLabel texte = null;
	private String idEnlevement = "";
	
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
			public void mouseMoved(MouseEvent e) {
				// TODO Auto-generated method stub
				if(supprimer|| ajout) onMotion(e.getX(), e.getY());  
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
	        	 if(ajouter) {
	        		 //on stocke la première intersection
	        		 ajouter2 = true;
	        		 idEnlevement = fenetre.getMessage(); 
	        		 System.out.println("ajoute fenetre");
	        		 ajouter = false;
	        	 }
	        	 
	        	 if(ajouter2) {
	        		 texte = new JLabel("Cliquer sur l'adresse de dépot");
	        		 repaint();
	        		//on stocke la deuxième intersection
	        		Intersection enlevement = plan.getIntersectionById(idEnlevement);
	        		String idDepot = fenetre.getMessage();
	        		Intersection depot = plan.getIntersectionById(idDepot);
	        		//on ajoute la nouvelle livraison
	        		Livraison l = new Livraison(enlevement, depot, 0, 0);
	        //		vueLivraisons.add(new VueLivraison(l)); 
	        	//	controleur
	        		repaint();
	        		ajouter2 = false;
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
			if(ajout)  vueLivraisons.get(i).onMotion(x, y, 1);
		 }  
		 repaint(); 
	}
	
	protected Livraison onClick (int x, int y) {  
		for (int i = 1; i < vueLivraisons.size(); i++) {  
			int choix = -1;
			if(supprimer) choix = 0;
			if(ajout) choix = 1;
			Map<Livraison, Intersection> map = vueLivraisons.get(i).onClick(x, y, choix);
			Livraison l = null;
			for (Map.Entry<Livraison, Intersection> entry : map.entrySet())
			{
				l =  entry.getKey();
			} 
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
		ajout = false;
		supprimer = true;
	}

	public void ajouterLivraison() {
		supprimer = false;
		ajout = true;
		// TODO Auto-generated method stub
	/*	ajouter = true;
		texte = new JLabel("Cliquer sur l'adresse d'enlevement");
		this.add(texte);
		repaint(); */
	}
 
}
