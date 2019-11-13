package vue;

import java.awt.Graphics;

import modele.Intersection;

/** Representation graphique d'un troncon appartenant a un plan
*/
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
	   if(yDest != yOrigine) { 
		   float pente = -(xDest-xOrigine)/(yDest-yOrigine); 
		   float xC = (xOrigine+xDest)/2;
		   float yC = (yOrigine+yDest)/2;
		   float origine = yC-pente*xC;
		   float a = 1+pente*pente;
		   float b = -2*xC + 2*pente*origine - 2*pente*yC;
		   float c = xC*xC + origine*origine + yC*yC -2*origine*yC - TAILLE_FLECHE*TAILLE_FLECHE;
		   float delta = (b*b)-(4*a*c);
		   int xD =  (int) ((-b+Math.sqrt(delta)) / (2*a));
		   int xE = (int) ((-b-Math.sqrt(delta)) / (2*a));
		   int yD = (int) (pente*xD+origine);
		   int yE = (int) (pente*xE+origine); 
		   g.drawLine(xOrigine, yOrigine, xDest, yDest); 
		   if(fleche) {
			   int[] tab = {xOrigine, xD, xE};
			   int[] tab2 = {yOrigine, yD,  yE};
			   g.fillPolygon(tab, tab2, 3); 
		   }
	   }
   }

	public Intersection onClick(int x, int y) {
		// TODO Auto-generated method stub 
		if(Math.sqrt((x-xOrigine)*(x-xOrigine)) + ((y-yOrigine)*(y-yOrigine)) < 5) {return origine;}
		if(Math.sqrt((x-xDest)*(x-xDest)) + ((y-yDest)*(y-yDest)) < 5) {return destination; }
		return null;
	}
}