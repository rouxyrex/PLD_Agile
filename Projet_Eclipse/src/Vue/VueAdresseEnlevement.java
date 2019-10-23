package Vue;

import java.awt.Graphics;

class VueAdresseEnlevement{ 
    final float x1; 
    final float y1;   

    public VueAdresseEnlevement(float x1, float y1) {
        this.x1 = x1;
        this.y1 = y1; 
    }       
    
    public void dessiner(Graphics g, int width, int height) {
    	int x = (int) ((x1-VuePlan.lattitudeMin)*height/VuePlan.intervalleLattitude);
		int y = (int) ((y1-VuePlan.longitudeMin)*width/VuePlan.intervalleLongitude);
		int[] tab = {height-x, height-x-15, height-x+15};
		int[] tab2 = {y+15, y-15, y-15};
    	g.fillPolygon(tab2, tab, 3); 
    }
}

