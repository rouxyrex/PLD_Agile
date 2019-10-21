package ihm;

import java.awt.Graphics;

class VueTroncon{
    float x1; 
    float y1;
    float x2;
    float y2;    

   public VueTroncon(float x1, float y1, float x2, float y2) {
       this.x1 = x1;
       this.y1 = y1;
       this.x2 = x2;
       this.y2 = y2; 
   } 
   
   public void dessiner(Graphics g, int width, int height) {  
	   int x11 = (int) ((x1-IHM.lattitudeMin)*width/IHM.intervalleLattitude);
	   int x21 = (int) ((x2-IHM.lattitudeMin)*width/IHM.intervalleLattitude);
	   int y11 = (int) ((y1-IHM.longitudeMin)*height/IHM.intervalleLongitude);
	   int y21 = (int) ((y2-IHM.longitudeMin)*height/IHM.intervalleLongitude); 
	   g.drawLine( x11, y11, x21,  y21); 
   }
}