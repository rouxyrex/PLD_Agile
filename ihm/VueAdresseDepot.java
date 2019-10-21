package ihm;

import java.awt.Graphics;

class VueAdresseDepot{
    final float x1; 
    final float y1;    

    public VueAdresseDepot(float x1, float y1) {
        this.x1 = x1;
        this.y1 = y1; 
    }             
    
    public void dessiner(Graphics g, int width, int height) {
    	int x = (int) ((x1-IHM.lattitudeMin)*width/IHM.intervalleLattitude);
		int y = (int) ((y1-IHM.longitudeMin)*height/IHM.intervalleLongitude);
    	g.fillOval(x, y, 20, 20);
    }
}
