package vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.LinkedList;  
import javax.swing.BorderFactory; 
import javax.swing.JPanel; 

import controleur.Controleur;
import modele.DemandeLivraison;
import modele.Intersection;
import modele.Livraison;

public class VueTextuelle extends JPanel{
	
	Fenetre fenetre;
	Controleur controleur;
	Color[] colors = {Color.cyan, Color.BLUE, Color.green, Color.RED, Color.magenta, Color.LIGHT_GRAY, Color.ORANGE, Color.YELLOW, Color.PINK, Color.white};
	DemandeLivraison dl = null;
	LinkedList<VueLivraison> vueLivraisons = null;
	
	public VueTextuelle(Fenetre fenetre, Controleur controleur) {
		this.fenetre = fenetre;
		this.controleur = controleur; 
		vueLivraisons = new LinkedList<VueLivraison>(); 
		this.setPreferredSize(new Dimension(300,100)); 
		setBorder(BorderFactory.createTitledBorder("Demande de livraison"));  
		fenetre.getContentPane().add(this, BorderLayout.EAST);  
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

	protected void onMotion(int x, int y) {
		// TODO Auto-generated method stub
		for (int i = 0; i < vueLivraisons.size(); i++) {   
			vueLivraisons.get(i).onMotion(x, y);
	    }  
		repaint();
	}

	public void afficherDemandeLivraison() {  
		dl = controleur.getDemandeLivraison(); 
		vueLivraisons.clear(); 
		for(Livraison livraison : dl.getLivraisons()) { 
			vueLivraisons.add(new VueLivraison(livraison.getAdresseDepot().getId(), livraison.getAdresseEnlevement().getId(), livraison.getDureeDepot(), livraison.getDureeEnlevement())); 
		}   
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);   
		int xDebut = 0;
		int yDebut = 0;
		float yOrigine; 
		int nbLivraisons =  dl.getNbLivraisons();
		float size = (fenetre.getHeight()-100-100)/nbLivraisons; 
		if(nbLivraisons + 3 <= 6) { yOrigine = 130; size = 100;}
		else yOrigine = 130;
		g.drawLine(xDebut, yDebut, getWidth(), yDebut);
    	g.drawString("Depart", xDebut+30, yDebut+30);
    	g.drawString("Adresse n°"+dl.getEntrepot().getId(), xDebut+30, yDebut+60);
    	g.drawLine(xDebut, yDebut+100, getWidth(), yDebut+100);
		for (int i = 0; i < vueLivraisons.size(); i++) {   
			vueLivraisons.get(i).dessiner(g, 20, (int) yOrigine, size, this.getWidth(), this.getHeight(), colors[i]);  
			yOrigine += size; 
	    }  
	}
 
}
