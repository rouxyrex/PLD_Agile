package vue;

import java.awt.Color;
import java.awt.Graphics;

import modele.Intersection;

class VueEntrepot{ 
    final float x1; 
    final float y1;   
    int x = -1;
    int y = -1;
    Intersection adresse;
    final int tailleEntrepot = 15;

    public VueEntrepot(Intersection adresse) {
        this.x1 = adresse.getLatitude();
        this.y1 = adresse.getLongitude();; 
        this.adresse = adresse;
    }       
    
    public void dessiner(Graphics g, int width, int height, int modifLatitude, int modifLongitude) {
    	g.setColor(Color.black);
    	int x = (int) ((x1-VuePlan.latitudeMin)*height/VuePlan.intervalleLatitude);
		int y = (int) ((y1-VuePlan.longitudeMin)*width/VuePlan.intervalleLongitude);
		this.x = y-(tailleEntrepot/2)+modifLongitude;
		this.y = height-x-(tailleEntrepot/2)+modifLatitude;
		g.fillRect(this.x, this.y, tailleEntrepot, tailleEntrepot);
    }

	public Intersection onClick(int x, int y) {
		// TODO Auto-generated method stub 
		if(x >= this.x && x <= (this.x+tailleEntrepot) && y >= (this.y) && y <= (this.y + tailleEntrepot)) {
			return adresse;
		}
		return null;
	}
}
