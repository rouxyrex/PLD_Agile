package vue;

import java.awt.Color;
import java.awt.Graphics;

import javafx.util.Pair;
import modele.Intersection;

class VueAdresseDepot{
    final float latitude;
    final float longitude;
    Pair<Integer, Intersection> adresse;
    int x = -1;
    int y = -1;
    final int tailleDepot = 20;
    Color color;

    public VueAdresseDepot(Pair<Integer, Intersection> pair, Color color) {
        this.adresse = pair;
        this.latitude = pair.getValue().getLatitude();
        this.longitude = pair.getValue().getLongitude();
        this.color = color;
    }

    public void dessiner(Graphics g, int width, int height, int modifLatitude, int modifLongitude) {
    	g.setColor(color);
    	int x = (int) ((latitude-VuePlan.latitudeMin)*height/VuePlan.intervalleLatitude);
		int y = (int) ((longitude-VuePlan.longitudeMin)*width/VuePlan.intervalleLongitude);
		this.x = y-(tailleDepot/2)+modifLongitude;
		this.y = height-x-(tailleDepot/2)+modifLatitude;
    	g.fillOval(this.x, this.y, tailleDepot, tailleDepot);
    }

	public Intersection onClick(int x, int y) {
		// TODO Auto-generated method stub
		int centrex = this.x+(tailleDepot/2);
		int centrey = this.y+(tailleDepot/2);
		if(Math.sqrt((x-centrex)*(x-centrex)+(y-centrey)*(y-centrey)) <= (tailleDepot/2)) return adresse.getValue();
		return null;
	}
}
