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
	LinkedList<Line> tronconsTraces = null;
	
	public IHM() { 
		 testFrame = new JFrame();
		 tronconsTraces = new LinkedList<Line>();
		 testFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);  
		 this.setSize(new Dimension(1200, 700));
		 this.setPreferredSize(new Dimension(1200, 700));
		 testFrame.getContentPane().add(this, BorderLayout.CENTER); 
	}

		private static class Line{
		    final int x1; 
		    final int y1;
		    final int x2;
		    final int y2;    
		
		    public Line(int x1, int y1, int x2, int y2) {
		        this.x1 = x1;
		        this.y1 = y1;
		        this.x2 = x2;
		        this.y2 = y2; 
		    }               
		}
 
		public void addLine(int x1, int x2, int x3, int x4) {  
		    tronconsTraces.add(new Line(x1,x2,x3,x4));  
		}  

		@Override
		protected void paintComponent(Graphics g) {
		    super.paintComponent(g);
		    for (Line line : tronconsTraces) { 
		        g.drawLine(line.x1, line.y1, line.x2, line.y2);
		    }
		}
		
		public void clearTronconsTraces() {
		    tronconsTraces.clear();
		    repaint();
		}
		
		public void afficherPlan(Plan p) { 
			      
				List<Troncon> troncons = p.getTroncons();  
				float lattitudeMax = p.getLattitudeMax();
				float lattitudeMin = p.getLattitudeMin();
				float longitudeMax = p.getLongitudeMax();
				float longitudeMin = p.getLongitudeMin();
				float intervalleLattitude = lattitudeMax-lattitudeMin;
				float intervalleLongitude = longitudeMax-longitudeMin;
				 
				for(int i= 0; i < troncons.size(); i++) {

					float x1 = troncons.get(i).getIntersectionOrigine().getLattitude();
					float y1 = troncons.get(i).getIntersectionOrigine().getLongitude();
					float x2 = troncons.get(i).getIntersectionDestination().getLattitude();
					float y2 = troncons.get(i).getIntersectionDestination().getLongitude();
					
					int x11 = (int) ((x1-lattitudeMin)*this.getWidth()/intervalleLattitude);
					int x21 = (int) ((x2-lattitudeMin)*this.getWidth()/intervalleLattitude);
					int y11 = (int) ((y1-longitudeMin)*this.getHeight()/intervalleLongitude);
					int y21 = (int) ((y2-longitudeMin)*this.getHeight()/intervalleLongitude);
					
					this.addLine(x11, y11, x21, y21); 
				} 
				
				testFrame.addComponentListener(new ComponentAdapter() {
				    public void componentResized(ComponentEvent componentEvent) {
				    	clearTronconsTraces();
				    	for(int i= 0; i < troncons.size(); i++) { 
							float x1 = troncons.get(i).getIntersectionOrigine().getLattitude();
							float y1 = troncons.get(i).getIntersectionOrigine().getLongitude();
							float x2 = troncons.get(i).getIntersectionDestination().getLattitude();
							float y2 = troncons.get(i).getIntersectionDestination().getLongitude();
							
							int x11 = (int) ((x1-lattitudeMin)*getWidth()/intervalleLattitude);
							int x21 = (int) ((x2-lattitudeMin)*getWidth()/intervalleLattitude);
							int y11 = (int) ((y1-longitudeMin)*getHeight()/intervalleLongitude);
							int y21 = (int) ((y2-longitudeMin)*getHeight()/intervalleLongitude);
							
							addLine(x11, y11, x21, y21);  
						}
				    }
				}); 
			
			    testFrame.pack();
			    testFrame.setVisible(true);
	}	
}