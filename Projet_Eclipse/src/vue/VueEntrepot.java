package vue;

import java.awt.Color;
import java.awt.Graphics;

class VueEntrepot{ 
    final float x1; 
    final float y1;   
    final int tailleEntrepot = 15;

    public VueEntrepot(float x1, float y1) {
        this.x1 = x1;
        this.y1 = y1; 
    }       
    
    public void dessiner(Graphics g, int width, int height) {
    	g.setColor(Color.black);
    	int x = (int) ((x1-VuePlan.latitudeMin)*height/VuePlan.intervalleLatitude);
		int y = (int) ((y1-VuePlan.longitudeMin)*width/VuePlan.intervalleLongitude);
		g.fillRect(y-(tailleEntrepot/2), height-x-(tailleEntrepot/2), tailleEntrepot, tailleEntrepot);
    }
}
