package vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel; 

import controleur.Controleur;
import modele.DemandeLivraison; 
import modele.Livraison;

public class VueTextuelle extends JPanel{
	 
	private static final long serialVersionUID = 1L;
	Fenetre fenetre;
	Controleur controleur;
	Color[] colors = {Color.cyan, Color.BLUE, Color.green, Color.RED, Color.magenta, Color.LIGHT_GRAY, Color.ORANGE, Color.YELLOW, Color.PINK, Color.white};
	DemandeLivraison dl = null;
	protected static final String MODIFIER = "Modifier la tournée";
	protected static final String AJOUT = "Ajouter une livraison";
	protected static final String SUPRESSION = "Suprimer";
	protected static final String INVERSER = "Inverser"; 
	private EcouteurDeBoutons ecouteurDeBoutons;
	private ArrayList<JButton> boutons;
	private final String[] intitulesBoutons = new String[]{MODIFIER, AJOUT, SUPRESSION, INVERSER}; //, CHARGER_DEMANDE_LIVRAISON, CALCULER_TOURNEE, GENERER_FEUILLE_ROUTE};
	private final int hauteurBouton = 50;  
	LinkedList<VueLivraison> vueLivraisons = null;
	
	public VueTextuelle(Fenetre fenetre, Controleur controleur) {
		this.fenetre = fenetre;
		this.controleur = controleur; 
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
				onMotion(e.getX(), e.getY()); 
			}
		});
		repaint();
	}
	
	private void creeBoutons(Controleur controleur){
		ecouteurDeBoutons = new EcouteurDeBoutons(controleur);
		boutons = new ArrayList<JButton>();
		for (String intituleBouton : intitulesBoutons){
			JButton bouton = new JButton(intituleBouton); 
			boutons.add(bouton);  
		/*	Image img = null;
			try { 
				img = ImageIO.read(new FileInputStream(intituleBouton+".png"));
			} catch (IOException e) { 
				e.printStackTrace();
			}
			bouton.setIcon(new ImageIcon(img));   */
			bouton.setLocation(0,(boutons.size()-1)*hauteurBouton); 
			bouton.addActionListener(ecouteurDeBoutons); 
			this.add(bouton);
			bouton.setVisible(false);
		}
	}

	protected void onMotion(int x, int y) {
		// TODO Auto-generated method stub
		for (int i = 0; i < vueLivraisons.size(); i++) {   
			vueLivraisons.get(i).onMotion(x, y);
	    }  
		repaint();
	}

	public void afficherDemandeLivraison() {  
		for (JButton bouton : boutons){ 
			bouton.setVisible(true); 
		}
		dl = controleur.getDemandeLivraison(); 
		vueLivraisons.clear(); 
		vueLivraisons.add(new VueLivraison("Adresse de depart : ", dl.getEntrepot().getId(), -1, -1));
		for(Livraison livraison : dl.getLivraisons()) { 
			vueLivraisons.add(new VueLivraison(livraison.getAdresseDepot().getId(), livraison.getAdresseEnlevement().getId(), livraison.getDureeDepot(), livraison.getDureeEnlevement())); 
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
 
}
