package vue;

import java.awt.Color;
import java.awt.Graphics;
import java.util.HashMap;
import java.util.Map;

import modele.Intersection;
import modele.Livraison;

public class VueLivraison {
    String idDepot;
    String idEnlevement;
    Livraison l;
    int tempsDepot;
    int tempsEnlevement;
    Color colorDepot = Color.LIGHT_GRAY;
    Color colorEnlevement = Color.LIGHT_GRAY;
    int xHautDroite;
    int yHautDroite;
    float size;
    int width;
    Color color;

    public VueLivraison(Livraison l, Color color) {
    	if(l.getAdresseDepot() == null) this.idDepot = "Adresse de depart : ";
    	else this.idDepot = l.getAdresseDepot().getId();
    	if(l.getAdresseEnlevement() == null) this.idEnlevement = "Adresse de enlevement : ";
    	else this.idEnlevement = l.getAdresseEnlevement().getId();
    	this.tempsDepot = l.getDureeDepot();
    	this.tempsEnlevement = l.getDureeEnlevement();
    	this.l = l;
    	this.color = color;

    }

    public void dessiner(Graphics g, int xHautDroite, int yHautDroite, float size, int width, int height) {
    	this.xHautDroite = xHautDroite;
    	this.yHautDroite = yHautDroite;
    	this.size = size;
    	this.width = width;
    	g.setColor(colorDepot);
    	g.fillRect(xHautDroite, yHautDroite, width-40, (int)size/2);
    	g.setColor(colorEnlevement);
    	g.fillRect(xHautDroite, yHautDroite+(int)size/2, width-40, (int)size/2);
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

	public void onMotion(int x, int y, int choix) {
		// TODO Auto-generated method stub
		if(choix == 0 && x >= xHautDroite && y >= yHautDroite && x <= width-40 && y <= yHautDroite+(int)size) {
			colorDepot =  Color.GRAY;
			colorEnlevement = Color.GRAY;
		}
		else if(choix == 1 && x >= xHautDroite && y >= yHautDroite && x <= width-40 && y <= yHautDroite+((int)size/2)) {
			colorDepot =  Color.GRAY;
			colorEnlevement = Color.lightGray;
		}
		else if(choix == 1 && x >= xHautDroite && y >= yHautDroite+((int)size/2) && x <= width-40 && y <= yHautDroite+(int)size) {
			colorEnlevement =  Color.GRAY;
			colorDepot = Color.lightGray;
		}
		else {
			colorDepot = Color.lightGray;
			colorEnlevement = Color.lightGray;
		}
	}

	public Map<Livraison, Intersection> onClick(int x, int y, int choix) {
		// TODO Auto-generated method stub
		Map<Livraison, Intersection> map = new HashMap<Livraison, Intersection>();
		if(choix== 0 && x >= xHautDroite && y >= yHautDroite && x <= width-40 && y <= yHautDroite+(int)size) {
			map.put(l, null);
			return map;
		}
		else if(choix == 1 && x >= xHautDroite && y >= yHautDroite && x <= width-40 && y <= yHautDroite+((int)size/2)) {
			map.put(l, l.getAdresseDepot());
			return map;
		}
		else if(choix == 1 && x >= xHautDroite && y >= yHautDroite+((int)size/2) && x <= width-40 && y <= yHautDroite+(int)size) {
			map.put(l, l.getAdresseEnlevement());
			return map;
		} return null;
	}

}
