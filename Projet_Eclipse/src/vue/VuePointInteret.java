package vue;

import java.awt.Color;
import java.awt.Graphics; 

import javafx.util.Pair;
import modele.Intersection;
import modele.Livraison;

public class VuePointInteret {
    String id; 
    Intersection i;
    int temps; 
    Color colorRect = Color.LIGHT_GRAY; 
    Color color;
    int xHautDroite;
    int yHautDroite;
    float size;
    int width; 
    int type; //0 --> depot, 1 --> enlevement

    public VuePointInteret(Intersection i, Color color, int type, int temps) {
    	if(i == null) this.id = "Adresse de depart : ";
    	else this.id = i.getId(); 
    	this.temps = temps;
    	this.i = i;
    	this.color = color;
    	this.type = type;
    }

    public void dessiner(Graphics g, int xHautDroite, int yHautDroite, float size, int width, int height) {
    	this.xHautDroite = xHautDroite;
    	this.yHautDroite = yHautDroite;
    	this.size = size;
    	this.width = width;
    	g.setColor(colorRect);
    	g.fillRect(xHautDroite, yHautDroite, width-40, (int)size); 
    	int size2 = (int) (size/2);
    	if(temps  != -1) {
			g.setColor(color);
			g.fillRect(xHautDroite, yHautDroite, 15, (int)size);
			if(type == 0) g.fillOval(xHautDroite+30, yHautDroite, 10, 10);
			if(type == 1) { 
				int[] tab = {(int) (yHautDroite), (int) (yHautDroite+10), (int) (yHautDroite+10)};
				int[] tab2 = {xHautDroite+35, xHautDroite+30, xHautDroite+40};
				g.fillPolygon(tab2, tab, 3);
			}
			
    	}
    	g.setColor(Color.BLACK);
		g.drawString(id, xHautDroite+60, yHautDroite+size2+10); 
		if(temps != -1) {
			g.drawString(Integer.toString(temps)+" secondes.", xHautDroite+200, yHautDroite+size2+10); 
		}
		g.drawLine(xHautDroite, yHautDroite, width-20, yHautDroite);
		g.drawLine(xHautDroite, yHautDroite+(int)size, width-20, yHautDroite+(int)size);
		g.drawLine(xHautDroite, yHautDroite, xHautDroite, yHautDroite+(int)size);
		g.drawLine(width-20, yHautDroite, width-20, yHautDroite+(int)size);
    }

	public void onMotion(int x, int y) {
		// TODO Auto-generated method stub
		if(x >= xHautDroite && y >= yHautDroite && x <= width-40 && y <= yHautDroite+(int)size) {
			colorRect =  Color.GRAY; 
		} 
		else {
			colorRect = Color.lightGray; 
		}
	}

	public Intersection onClick(int x, int y) {
		// TODO Auto-generated method stub
		if(x >= xHautDroite && y >= yHautDroite && x <= width-40 && y <= yHautDroite+(int)size) { 
			colorRect = Color.lightGray; 
			return i;
		} 
		else {
			return null;
		}
	}
	
	public Intersection getIntersection() {
		return i;
	}

}
