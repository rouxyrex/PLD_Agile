package vue;

import java.awt.Color;
import java.awt.Graphics;

public class VueLivraison { 
    String idDepot;
    String idEnlevement;
    int tempsDepot;
    int tempsEnlevement;  

    public VueLivraison(String idDepot, String idEnlevement, int tempsDepot, int tempsEnlevement) {
    	this.idDepot = idDepot;
    	this.idEnlevement = idEnlevement;
    	this.tempsDepot = tempsDepot;
    	this.tempsEnlevement = tempsEnlevement; 
    }             
    
    public void dessiner(Graphics g, int xHautDroite, int yHautDroite, float size, int width, int height) { 
    	
		g.fillRect(xHautDroite, yHautDroite, 15, 30);
    	g.fillOval(xHautDroite+30, yHautDroite+5, 10, 10);
    	int[] tab = {yHautDroite+25, yHautDroite+35, yHautDroite+35};
		int[] tab2 = {xHautDroite+35, xHautDroite+30, xHautDroite+40};
    	g.fillPolygon(tab2, tab, 3); 
    	g.setColor(Color.BLACK);
		g.drawString(idDepot, xHautDroite+60, yHautDroite+5);
		g.drawString(idEnlevement, xHautDroite+60, yHautDroite+25);
		g.drawString(Integer.toString(tempsDepot), xHautDroite+200, yHautDroite+5);
		g.drawString(Integer.toString(tempsEnlevement), xHautDroite+200, yHautDroite+25); 
	/*	g.drawLine(xHautDroite, yHautDroite, width, yHautDroite);
		g.drawLine(xHautDroite+size, yHautDroite, width, yHautDroite);*/
    }
     
}
