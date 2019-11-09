package vue;

import java.awt.Color;
import java.awt.Graphics;

import modele.Intersection;

class VueAdresseDepot{
    final float latitude; 
    final float longitude;    
    int x = -1;
    int y = -1;
    final int tailleDepot = 20;

    public VueAdresseDepot(float x1, float y1) {
        this.latitude = x1;
        this.longitude = y1; 
    }             
    
    public void dessiner(Graphics g, int width, int height, int modifLatitude, int modifLongitude) {
    	int x = (int) ((latitude-VuePlan.latitudeMin)*height/VuePlan.intervalleLatitude);
		int y = (int) ((longitude-VuePlan.longitudeMin)*width/VuePlan.intervalleLongitude); 
		this.x = y-(tailleDepot/2)+modifLongitude;
		this.y = height-x-(tailleDepot/2)+modifLatitude;
    	g.fillOval(this.x, this.y, tailleDepot, tailleDepot);
    }

	public Intersection onClick(int x, int y) {
		// TODO Auto-generated method stub
		boolean touche = false;
		return null;
	}
}
