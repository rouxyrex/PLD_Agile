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
import modele.Tournee;

public class Fenetre extends JFrame {
	
	private static final long serialVersionUID = 1L;
	// Intitulés des boutons de la fenêtre
	protected static final String CHARGER_PLAN = "Charger un plan";
	protected static final String CHARGER_DEMANDE_LIVRAISON = "Charger une demande de livraison";
	protected static final String CALCULER_TOURNEE = "Calculer la tournée";
	protected static final String GENERER_FEUILLE_ROUTE = "Générer la feuille de route";
	private ArrayList<JButton> boutons;
	private JLabel cadreMessages;
	private JPanel cadreBoutons;
	private VuePlan vuePlan;
	private VueTextuelle vueTextuelle;
	private EcouteurDeBoutons ecouteurDeBoutons;
	
	private final String[] intitulesBoutons = new String[]{CHARGER_PLAN, CHARGER_DEMANDE_LIVRAISON, CALCULER_TOURNEE, GENERER_FEUILLE_ROUTE}; //, CHARGER_DEMANDE_LIVRAISON, CALCULER_TOURNEE, GENERER_FEUILLE_ROUTE};

	private final int hauteurBouton = 50;
	private final int largeurBouton = 300;
	private final int hauteurCadreMessages = 80;
	
	
	/**
	 * Cree une fenetre avec des boutons, une zone graphique pour dessiner le plan p avec l'echelle e, 
	 * un cadre pour afficher des messages, une zone textuelle decrivant les formes de p,
	 * et des ecouteurs de boutons, de clavier et de souris qui envoient des messages au controleur c
	 * @param plan le plan
	 * @param echelle l'echelle
	 * @param controleur le controleur
	 */
	public Fenetre(Plan plan, DemandeLivraison demandeLivraison, Tournee tournee, int echelle, Controleur controleur){
		setLayout(new BorderLayout());
		
		cadreBoutons = new JPanel();
		cadreBoutons.setSize(300, 100);
		cadreBoutons.setLayout(new BoxLayout(cadreBoutons, BoxLayout.PAGE_AXIS) );
	    getContentPane().add(cadreBoutons, BorderLayout.WEST);

		creeBoutons(controleur);

		cadreMessages = new JLabel();
		cadreMessages.setBorder(BorderFactory.createTitledBorder("Infos complementaires"));
		getContentPane().add(cadreMessages, BorderLayout.SOUTH);
		
		vuePlan = new VuePlan(this, plan, demandeLivraison, tournee, controleur);
		
		vueTextuelle = new VueTextuelle(this, plan, demandeLivraison, tournee, controleur);
		vueTextuelle.setVisible(false);
		
		setTailleFenetre();
		setVisible(true);
		getContentPane().add(vuePlan, BorderLayout.CENTER);
		addComponentListener(new ComponentAdapter() {
		    public void componentResized(ComponentEvent componentEvent) {
				repaint();
		    }
		});
	} 
	
	/**
	 * Cree les boutons correspondant aux intitules contenus dans intitulesBoutons
	 * cree un ecouteur de boutons qui ecoute ces boutons
	 * @param controleur
	 */
	private void creeBoutons(Controleur controleur){
		ecouteurDeBoutons = new EcouteurDeBoutons(controleur, this);
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
		setSize(1000, 800);
		cadreMessages.setSize(500,60);
		cadreMessages.setLocation(0,800-hauteurCadreMessages);
	}
	
	/**
	 * Affiche message dans la fenetre de dialogue avec l'utilisateur
	 * @param m le message a afficher
	 */
	public void afficheMessage(String m) {
		cadreMessages.setText(m);
	}
	
	public void zoom() {
		vuePlan.zoom();
	}
	public void dezoom() {
		vuePlan.dezoom();
	}
	public void haut() {
		vuePlan.haut();
	}
	public void bas() {
		vuePlan.bas();
	}
	public void droite() {
		vuePlan.droite();
	}
	public void gauche() {
		vuePlan.gauche();
	}
	
	
	public void initialiserVuePlan() {
		vuePlan.initialiserVuePlan();
	}
	
	public void effacerVuePlan() {
		vuePlan.effacerVuePlan();
	}
	
	public void initialiserVueDemandeLivraison() {
		vuePlan.initialiserVueDemandeLivraison();
		vueTextuelle.initialiserVueDemandeLivraison();
		vueTextuelle.setVisible(true);
	}
	
	public void effacerVueDemandeLivraison() {
		vuePlan.effacerVueDemandeLivraison();
		vueTextuelle.effacerVueDemandeLivraison();
		vueTextuelle.setVisible(false);
	}
	
	public void initialiserVueTournee() {
		vuePlan.initialiserVueTournee();
	}
	
	public void effacerVueTournee() {
		vuePlan.effacerVueTournee();
	}

	public void modifierTournee() {
		// TODO Auto-generated method stub
		vueTextuelle.modifierTournee();
	}

	public void supprimerLivraison() {
		// TODO Auto-generated method stub
		vueTextuelle.supprimerLivraison();
	}
	
	
}
