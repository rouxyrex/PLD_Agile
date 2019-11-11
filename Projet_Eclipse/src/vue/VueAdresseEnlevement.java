package vue;

import java.awt.Graphics;

import modele.Intersection;

class VueAdresseEnlevement{ 
    final float latitude; 
    final float longitude;   
    final int TAILLE_TRIANGLE = 10;
    int[] tab = new int[3];
    int[] tab2 = new int[3];
    Intersection adresse;

    public VueAdresseEnlevement(Intersection adresse) {
        this.latitude = adresse.getLatitude();
        this.longitude = adresse.getLongitude(); 
        this.adresse = adresse;
        tab[0] = -1;
        tab[1] = -1;
        tab[2] = -1;
        tab2[0] = -1;
        tab2[1] = -1;
        tab2[2] = -1;
        
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

	public Intersection onClick(int x, int y) { 
		//on va dire que le triangle est dans un rectangle
		if(x >= tab2[1] && x <= tab2[2] && y >= tab[0] && y <= tab[1])  return adresse; 
		else return null;
	}
}