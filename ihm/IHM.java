package ihm;

import java.awt.BorderLayout; 
import java.awt.Dimension;
import java.awt.Graphics; 
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent; 
import java.util.LinkedList;
import java.util.List;
 
import javax.swing.JComponent;
import javax.swing.JFrame;
 
import plan.Plan;
import plan.Troncon; 

public class IHM extends JComponent{
	
	JFrame testFrame = null;
	public static float lattitudeMax;
	public static float lattitudeMin;
	public static float longitudeMax;
	public static float longitudeMin;
	public static float intervalleLattitude;
	public static float intervalleLongitude;
	List<Troncon> troncons = null;
	LinkedList<VueTroncon> tronconsTraces = null; 
	LinkedList<VueAdresseDepot> adressesDepot = null;
	LinkedList<VueAdresseEnlevement> adressesEnlevement = null;
	
	public IHM() { 
		 testFrame = new JFrame();
		 tronconsTraces = new LinkedList<VueTroncon>();
		 adressesDepot = new LinkedList<VueAdresseDepot>();
		 adressesEnlevement = new LinkedList<VueAdresseEnlevement>(); 
		 testFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);  
		 this.setSize(new Dimension(1200, 700));
		 this.setPreferredSize(new Dimension(1200, 700));
		 testFrame.getContentPane().add(this, BorderLayout.CENTER); 
		 testFrame.addComponentListener(new ComponentAdapter() {
			    public void componentResized(ComponentEvent componentEvent) { 
					repaint();
			    }
		}); 
	}  
	
	public void addLine(float x1, float x2, float x3, float x4) {  
	    tronconsTraces.add(new VueTroncon(x1,x2,x3,x4));  
	    repaint();
	} 
	
	public void addCircle(float x1, float x2) {  
		adressesDepot.add(new VueAdresseDepot(x1,x2));  
		repaint();
	} 
	
	public void addTriangle(float x1, float x2) {   
		adressesEnlevement.add(new VueAdresseEnlevement(x1,x2));  
		repaint();
	} 

	@Override
	protected void paintComponent(Graphics g) {
	    super.paintComponent(g);
	    for (VueTroncon troncon : tronconsTraces) { 
	    	troncon.dessiner(g, this.getWidth(), this.getHeight()); 
	    }
	    
	    for(VueAdresseDepot adresseDepot : adressesDepot) { 
	    	adresseDepot.dessiner(g, this.getWidth(), this.getHeight());
	    }
	    
	    for(VueAdresseEnlevement adresseEnlevement : adressesEnlevement) { 
	    	adresseEnlevement.dessiner(g, this.getWidth(), this.getHeight());
	    }
	}
		
	public void clearPlan() { 
	    tronconsTraces.clear();
	    adressesDepot.clear();
	    adressesEnlevement.clear();
	    repaint();
	}
		
		public void afficherPlan(Plan p) { 
			      
				troncons = p.getTroncons();  
				lattitudeMax = p.getLattitudeMax();
				lattitudeMin = p.getLattitudeMin();
				longitudeMax = p.getLongitudeMax();
				longitudeMin = p.getLongitudeMin();
				intervalleLattitude = lattitudeMax-lattitudeMin;
				intervalleLongitude = longitudeMax-longitudeMin;
				 
				for(int i= 0; i < troncons.size(); i++) {

					float x1 = troncons.get(i).getIntersectionOrigine().getLattitude();
					float y1 = troncons.get(i).getIntersectionOrigine().getLongitude();
					float x2 = troncons.get(i).getIntersectionDestination().getLattitude();
					float y2 = troncons.get(i).getIntersectionDestination().getLongitude();
					 
					this.addLine(x1, y1, x2, y2); 
				}  
			    testFrame.pack();
			    testFrame.setVisible(true);
	}
		
		public void afficherLivraisonPlan(/*Livraison l,*/Plan p) {
			  
			float lattitudeEnlevement = (float) 45.75406;
			float longitudeEnlevement = (float) 4.857418;
			
			float lattitudeDepot = (float) 45.747795;
			float longitudeDepot = (float) 4.872455;
			   
			this.addCircle(lattitudeDepot, longitudeDepot); 
			this.addTriangle(lattitudeEnlevement, longitudeEnlevement); 
		}
}