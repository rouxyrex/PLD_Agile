package vue;

import java.awt.Graphics;

class VueAdresseEnlevement{ 
    final float latitude; 
    final float longitude;   
    final int TAILLE_TRIANGLE = 10;
    int[] tab;
    int[] tab2;

    public VueAdresseEnlevement(float x1, float y1) {
        this.latitude = x1;
        this.longitude = y1; 
    }       
    
    public void dessiner(Graphics g, int width, int height, int modifLatitude, int modifLongitude) {
    	int x = (int) ((latitude-VuePlan.latitudeMin)*height/VuePlan.intervalleLatitude);
		int y = (int) ((longitude-VuePlan.longitudeMin)*width/VuePlan.intervalleLongitude);
		int[] tab = {height-x-TAILLE_TRIANGLE+modifLatitude, height-x+TAILLE_TRIANGLE+modifLatitude, height-x+TAILLE_TRIANGLE+modifLatitude};
		int[] tab2 = {y+modifLongitude, y-TAILLE_TRIANGLE+modifLongitude, y+TAILLE_TRIANGLE+modifLongitude};
		this.tab = tab;
		this.tab2 = tab2;
    	g.fillPolygon(tab2, tab, 3); 
    }

	public boolean onClick(int x, int y) {
		// TODO Auto-generated method stub
		boolean touche= false; 
		return touche;
	}
}
