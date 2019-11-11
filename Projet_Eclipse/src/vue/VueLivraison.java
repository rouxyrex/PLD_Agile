package vue;

import java.awt.Color;
import java.awt.Graphics;

import modele.Livraison;

public class VueLivraison { 
    String idDepot;
    String idEnlevement;
    Livraison l;
    int tempsDepot;
    int tempsEnlevement;  
    Color color2 = Color.LIGHT_GRAY;
    int xHautDroite;
    int yHautDroite;
    float size;
    int width;

    public VueLivraison(Livraison l) {
    	if(l.getAdresseDepot() == null) this.idDepot = "Adresse de depart : ";
    	else this.idDepot = l.getAdresseDepot().getId();
    	if(l.getAdresseEnlevement() == null) this.idEnlevement = "Adresse de enlevement : ";
    	else this.idEnlevement = l.getAdresseEnlevement().getId();
    	this.tempsDepot = l.getDureeDepot();
    	this.tempsEnlevement = l.getDureeEnlevement(); 
    	this.l = l;
    	
    }             
    
    public void dessiner(Graphics g, int xHautDroite, int yHautDroite, float size, int width, int height, Color color) {  
    	this.xHautDroite = xHautDroite;
    	this.yHautDroite = yHautDroite;
    	this.size = size;
    	this.width = width;
    	g.setColor(color2);
    	g.fillRect(xHautDroite, yHautDroite, width-40, (int)size);  
    	int size2 = (int) (size/4);
    	if(tempsDepot != -1) {
			g.setColor(color);
			g.fillRect(xHautDroite, yHautDroite, 15, (int)size); 
			g.fillOval(xHautDroite+30, yHautDroite+size2, 10, 10);
			int[] tab = {(int) (yHautDroite+2.5*size2), (int) (yHautDroite+2.5*size2+10), (int) (yHautDroite+2.5*size2+10)};
			int[] tab2 = {xHautDroite+35, xHautDroite+30, xHautDroite+40};
			g.fillPolygon(tab2, tab, 3); 
    	}
    	g.setColor(Color.BLACK);
		g.drawString(idDepot, xHautDroite+60, yHautDroite+size2+10);
		g.drawString(idEnlevement, xHautDroite+60, (int)(yHautDroite+2.5*size2+10));
		if(tempsDepot != -1) {
			g.drawString(Integer.toString(tempsDepot), xHautDroite+200, yHautDroite+size2+10);
			g.drawString(Integer.toString(tempsEnlevement), xHautDroite+200, (int)(yHautDroite+2.5*size2+10)); 
		} 
		g.drawLine(xHautDroite, yHautDroite, width-20, yHautDroite);
		g.drawLine(xHautDroite, yHautDroite+(int)size, width-20, yHautDroite+(int)size);
		g.drawLine(xHautDroite, yHautDroite, xHautDroite, yHautDroite+(int)size);
		g.drawLine(width-20, yHautDroite, width-20, yHautDroite+(int)size);
    }

	public void onMotion(int x, int y) {
		// TODO Auto-generated method stub 
		if(x >= xHautDroite && y >= yHautDroite && x <= width-40 && y <= yHautDroite+(int)size) color2 =  Color.GRAY;
		else color2 = Color.lightGray;
	}
	
	public Livraison onClick(int x, int y) {
		// TODO Auto-generated method stub 
		if(x >= xHautDroite && y >= yHautDroite && x <= width-40 && y <= yHautDroite+(int)size) {
			color2 =  Color.GRAY;
			return l;
		}
		else color2 = Color.lightGray;
		return null;
	}
     
}
