package vue;

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
	   /*int x11 = (int) ((x1-VuePlan.lattitudeMin)*width/VuePlan.intervalleLattitude);
	   int x21 = (int) ((x2-VuePlan.lattitudeMin)*width/VuePlan.intervalleLattitude);
	   int y11 = (int) ((y1-VuePlan.longitudeMin)*height/VuePlan.intervalleLongitude);
	   int y21 = (int) ((y2-VuePlan.longitudeMin)*height/VuePlan.intervalleLongitude);  
	   g.drawLine( x11, y11, x21, y21); */
	   int x11 = (int) ((x1-VuePlan.latitudeMin)*height/VuePlan.intervalleLatitude);
	   int x21 = (int) ((x2-VuePlan.latitudeMin)*height/VuePlan.intervalleLatitude);
	   int y11 = (int) ((y1-VuePlan.longitudeMin)*width/VuePlan.intervalleLongitude);
	   int y21 = (int) ((y2-VuePlan.longitudeMin)*width/VuePlan.intervalleLongitude);  
	   g.drawLine( y11, height-x11, y21, height-x21); 
   }
}