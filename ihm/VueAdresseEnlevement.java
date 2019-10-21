package ihm;

import java.awt.Graphics;

class VueAdresseEnlevement{
  /*  final int[] x1; 
    final int[] y1;  */
    final float x1; 
    final float y1;   

    public VueAdresseEnlevement(float x1, float y1) {
        this.x1 = x1;
        this.y1 = y1; 
    }       
    
    public void dessiner(Graphics g, int width, int height) {
    	int x = (int) ((x1-IHM.lattitudeMin)*width/IHM.intervalleLattitude);
		int y = (int) ((y1-IHM.longitudeMin)*height/IHM.intervalleLongitude);
		int[] tab = {x, x-15, x+15};
		int[] tab2 = {y+15, y-15, y-15};
    	g.fillPolygon(tab, tab2, 3); 
    }
}
