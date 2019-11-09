package vue;

import java.awt.Graphics;

import modele.Intersection;

class VueTroncon{
    float latitudeOrigine; 
    float longitudeOrigine;
    float latitudeDest;
    float longitudeDest;    
    Intersection origine;
    Intersection destination;
    int xOrigine = -1;
    int yOrigine = -1;
    int xDest = -1;
    int yDest = -1;
    final int TAILLE_FLECHE = 10;

   public VueTroncon(Intersection origine, Intersection destination) {
	   this.origine = origine;
	   this.destination = destination;
       this.latitudeOrigine = origine.getLatitude();
       this.longitudeOrigine = origine.getLongitude();
       this.latitudeDest = destination.getLatitude();
       this.longitudeDest = destination.getLongitude(); 
   } 
   
   public void dessiner(Graphics g, int width, int height, int modifLatitude, int modifLongitude, boolean fleche) {   
	   int x11 = (int) ((latitudeOrigine-VuePlan.latitudeMin)*height/VuePlan.intervalleLatitude);
	   int x21 = (int) ((latitudeDest-VuePlan.latitudeMin)*height/VuePlan.intervalleLatitude);
	   int y11 = (int) ((longitudeOrigine-VuePlan.longitudeMin)*width/VuePlan.intervalleLongitude);
	   int y21 = (int) ((longitudeDest-VuePlan.longitudeMin)*width/VuePlan.intervalleLongitude);  
	   xOrigine = y11+modifLongitude;
	   yOrigine = height-x11+modifLatitude;
	   xDest = y21+modifLongitude;
	   yDest = height-x21+modifLatitude;
	   g.drawLine( xOrigine, yOrigine, xDest, yDest); 
	   if(fleche) {
		   int[] tab = {x11+modifLatitude, x11+modifLatitude+TAILLE_FLECHE, x11+modifLatitude};
		   int[] tab2 = {y11+modifLongitude, y11+modifLongitude,  y11+modifLongitude+TAILLE_FLECHE};
		   g.fillPolygon(tab2, tab, 3); 
	   }
   }

	public Intersection onClick(int x, int y) {
		// TODO Auto-generated method stub 
		if(Math.sqrt((x-xOrigine)*(x-xOrigine)) + ((y-yOrigine)*(y-yOrigine)) < 5) {return origine;}
		if(Math.sqrt((x-xDest)*(x-xDest)) + ((y-yDest)*(y-yDest)) < 5) {return destination; }
		return null;
	}
}