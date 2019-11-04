package vue;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controleur.Controleur;
import modele.DemandeLivraison;
import modele.Plan;

public class Fenetre extends JFrame {
	// Intitulés des boutons de la fenêtre
		protected static final String CHARGER_PLAN = "Charger un plan";
		protected static final String CHARGER_DEMANDE_LIVRAISON = "Charger une demande de livraison";
		protected static final String CALCULER_TOURNEE = "Calculer la tournée";
		protected static final String GENERER_FEUILLE_ROUTE = "Générer la feuille de route";
		private ArrayList<JButton> boutons;
		private JLabel cadreMessages;
		private JPanel cadreBoutons;
		private VuePlan vuePlan;
		private VueDemandeLivraison demandeLivraison;
		//private VueTextuelle vueTextuelle;
		private EcouteurDeBoutons ecouteurDeBoutons;
		//private EcouteurDeSouris ecouteurDeSouris;
		//private EcouteurDeClavier ecouteurDeClavier;
		
		private final String[] intitulesBoutons = new String[]{CHARGER_PLAN, CHARGER_DEMANDE_LIVRAISON}; //, CHARGER_DEMANDE_LIVRAISON, CALCULER_TOURNEE, GENERER_FEUILLE_ROUTE};
		private final int hauteurBouton = 50;
		private final int largeurBouton = 300;
		private final int hauteurCadreMessages = 80;
		private final int largeurVueTextuelle = 400;
		
		
		/**
		 * Cree une fenetre avec des boutons, une zone graphique pour dessiner le plan p avec l'echelle e, 
		 * un cadre pour afficher des messages, une zone textuelle decrivant les formes de p,
		 * et des ecouteurs de boutons, de clavier et de souris qui envoient des messages au controleur c
		 * @param plan le plan
		 * @param echelle l'echelle
		 * @param controleur le controleur
		 */
		public Fenetre(Plan plan, int echelle, Controleur controleur){
			setLayout(new BorderLayout());
			
			cadreBoutons = new JPanel(); 
			cadreBoutons.setSize(200, 100); 
			cadreBoutons.setLayout(new BoxLayout(cadreBoutons, BoxLayout.PAGE_AXIS) );
		    getContentPane().add(cadreBoutons, BorderLayout.WEST);
		    
			creeBoutons(controleur);
			
			cadreMessages = new JLabel();
			cadreMessages.setBorder(BorderFactory.createTitledBorder("Infos complémentaires"));
			getContentPane().add(cadreMessages,  BorderLayout.SOUTH);
			
			vuePlan = new VuePlan(1 ,this);
			//vueTextuelle = new VueTextuelle(plan, this);
			//ecouteurDeSouris = new EcouteurDeSouris(controleur,vueGraphique,this);
			//addMouseListener(ecouteurDeSouris);
			//addMouseMotionListener(ecouteurDeSouris);
			//ecouteurDeClavier = new EcouteurDeClavier(controleur);
			//addKeyListener(ecouteurDeClavier);
			setTailleFenetre(); 
			setVisible(true);
			addComponentListener(new ComponentAdapter() {
			    public void componentResized(ComponentEvent componentEvent) { 
					repaint();
			    }
			}); 
		} 
		
		public void passerPlan (Plan p/*, DemandeLivraison dl */) {
			vuePlan.afficherPlan( p/*, dl*/);
			//demandeLivraison.afficherPlan(dl);
			repaint();
		}
		
		public void afficherDemandeLivraison(DemandeLivraison dl) {
			//demandeLivraison.afficherPlan(dl);
			vuePlan.afficherLivraisonDemande(dl);
			repaint();
		}
		
		
		/**
		 * Cree les boutons correspondant aux intitules contenus dans intitulesBoutons
		 * cree un ecouteur de boutons qui ecoute ces boutons
		 * @param controleur
		 */
		private void creeBoutons(Controleur controleur){
			ecouteurDeBoutons = new EcouteurDeBoutons(controleur);
			boutons = new ArrayList<JButton>();
			for (String intituleBouton : intitulesBoutons){
				JButton bouton = new JButton(intituleBouton);
				boutons.add(bouton);
				bouton.setSize(largeurBouton,hauteurBouton);
				bouton.setMaximumSize(new Dimension(largeurBouton,hauteurBouton));
				bouton.setLocation(0,(boutons.size()-1)*hauteurBouton);
				bouton.setFocusable(false);
				bouton.setFocusPainted(false);
				bouton.addActionListener(ecouteurDeBoutons);
				cadreBoutons.add(bouton);	
			}
		}
			
			
			
			
			
			
			/**
			 * Definit la taille du cadre et de ses composants en fonction de la taille de la vue
			 * @param largeurVue
			 * @param hauteurVue
			 */
			private void setTailleFenetre() {
				//int hauteurBoutons = hauteurBouton*intitulesBoutons.length;
				//int hauteurFenetre = Math.max(vueGraphique.getHauteur(),hauteurBoutons)+hauteurCadreMessages;
				//int largeurFenetre = vueGraphique.getLargeur()+largeurBouton+largeurVueTextuelle+10;
				
				//setSize(largeurFenetre, hauteurFenetre);
				setSize(500, 800);
		//		vuePlan.setLocation(0, 0);
				cadreMessages.setSize(500,60);
				cadreMessages.setLocation(0,800-hauteurCadreMessages);
				//vueTextuelle.setSize(largeurVueTextuelle,hauteurFenetre-hauteurCadreMessages);
				//vueTextuelle.setLocation(10+vueGraphique.getLargeur()+largeurBouton,0);
			}
			
			
			
			/**
			 * Affiche message dans la fenetre de dialogue avec l'utilisateur
			 * @param m le message a afficher
			 */
			public void afficheMessage(String m) {
				cadreMessages.setText(m);
			}
		
}
