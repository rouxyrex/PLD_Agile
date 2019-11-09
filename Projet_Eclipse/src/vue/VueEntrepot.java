package vue;

import java.awt.Color;
import java.awt.Graphics;

class VueEntrepot{ 
    final float x1; 
    final float y1;   
    int x = -1;
    int y = -1;
    final int tailleEntrepot = 15;

    public VueEntrepot(float x1, float y1) {
        this.x1 = x1;
        this.y1 = y1; 
    }       
    
    public void dessiner(Graphics g, int width, int height, int modifLatitude, int modifLongitude) {
    	g.setColor(Color.black);
    	int x = (int) ((x1-VuePlan.latitudeMin)*height/VuePlan.intervalleLatitude);
		int y = (int) ((y1-VuePlan.longitudeMin)*width/VuePlan.intervalleLongitude);
		this.x = y-(tailleEntrepot/2)+modifLongitude;
		this.y = height-x-(tailleEntrepot/2)+modifLatitude;
		g.fillRect(this.x, this.y, tailleEntrepot, tailleEntrepot);
    }

	public boolean onClick(int x, int y) {
		// TODO Auto-generated method stub
		boolean touche = false; 
		if(x >= this.x && x <= (this.x+tailleEntrepot) && y >= (this.y) && y <= (this.y + tailleEntrepot)) {
			System.out.println("touche");
		}
		return touche;
	}
}
