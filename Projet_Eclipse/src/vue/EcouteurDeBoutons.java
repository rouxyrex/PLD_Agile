package vue;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener; 
import controleur.Controleur;

public class EcouteurDeBoutons implements ActionListener {
	
	private Controleur controleur;

	public EcouteurDeBoutons(Controleur controleur){
		this.controleur = controleur;
	}

	@Override
	public void actionPerformed(ActionEvent e) { 
		// Methode appelee par l'ecouteur de boutons a chaque fois qu'un bouton est clique
		// Envoi au controleur du message correspondant au bouton clique
		switch (e.getActionCommand()){
			case Fenetre.CHARGER_PLAN: 
					try {
						controleur.chargerPlan();
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} 
				 break;
			case Fenetre.CHARGER_DEMANDE_LIVRAISON: 
				try {
					controleur.chargerDemandeLivraison();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} break;
				
			case Fenetre.CALCULER_TOURNEE:
				controleur.calculerTournee();
				break;
			case Fenetre.GENERER_FEUILLE_ROUTE:
				controleur.genererFeuilleRoute();
				break;
			case VuePlan.ZOOM: 
				controleur.zoom();
				break; 
			case VuePlan.DEZOOM: 
				controleur.dezoom();
				break;
			case VuePlan.HAUT: 
				controleur.haut();
				break;
			case VuePlan.BAS: 
				controleur.bas();
				break;
			case VuePlan.GAUCHE: 
				controleur.gauche();
				break;
			case VuePlan.DROITE: 
				controleur.droite();
				break;
		} 
	}
	
}
