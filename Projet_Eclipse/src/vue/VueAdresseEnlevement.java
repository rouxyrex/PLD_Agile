package vue;

import java.awt.Graphics;

class VueAdresseEnlevement{ 
    final float x1; 
    final float y1;   
    final int tailleRectangle = 10;

    public VueAdresseEnlevement(float x1, float y1) {
        this.x1 = x1;
        this.y1 = y1; 
    }       
    
    public void dessiner(Graphics g, int width, int height) {
    	int x = (int) ((x1-VuePlan.lattitudeMin)*height/VuePlan.intervalleLattitude);
		int y = (int) ((y1-VuePlan.longitudeMin)*width/VuePlan.intervalleLongitude);
		int[] tab = {height-x-tailleRectangle, height-x+tailleRectangle, height-x+tailleRectangle};
		int[] tab2 = {y, y-tailleRectangle, y+tailleRectangle};
    	g.fillPolygon(tab2, tab, 3); 
    }
}