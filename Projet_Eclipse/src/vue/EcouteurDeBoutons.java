package vue;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import controleur.Controleur;

public class EcouteurDeBoutons implements ActionListener {
	
	private Controleur controleur;
	private Fenetre fenetre;

	public EcouteurDeBoutons(Controleur controleur, Fenetre fenetre){
		this.controleur = controleur;
		this.fenetre = fenetre;
	}

	@Override
	public void actionPerformed(ActionEvent e) { 
		// Methode appelee par l'ecouteur de boutons a chaque fois qu'un bouton est clique
		// Envoi au controleur du message correspondant au bouton clique
		switch (e.getActionCommand()){
			case Fenetre.CHARGER_PLAN: controleur.chargerPlan(); break;
			case Fenetre.CHARGER_DEMANDE_LIVRAISON: controleur.chargerDemandeLivraison(); break;
			case Fenetre.CALCULER_TOURNEE: controleur.calculerTournee(); break;
			case Fenetre.GENERER_FEUILLE_ROUTE: controleur.genererFeuilleRoute(); break;
			case VuePlan.ZOOM: fenetre.zoom(); break;
			case VuePlan.DEZOOM: fenetre.dezoom(); break;
			case VuePlan.HAUT: fenetre.haut(); break;
			case VuePlan.BAS: fenetre.bas(); break;
			case VuePlan.GAUCHE: fenetre.gauche(); break;
			case VuePlan.DROITE: fenetre.droite(); break;
		}
		/*case Fenetre.AJOUTER_RECTANGLE: controleur.ajouterRectangle(); break;
		case Fenetre.SUPPRIMER: controleur.supprimer(); break;
		case Fenetre.SAUVER: controleur.sauver(); break;
		case Fenetre.OUVRIR: controleur.ouvrir(); break;
		case Fenetre.UNDO: controleur.undo(); break;
		case Fenetre.REDO: controleur.redo(); break;
		case Fenetre.DEPLACER: controleur.deplacer();break;
		case Fenetre.DIMINUER_ECHELLE: controleur.diminuerEchelle(); break;
		case Fenetre.AUGMENTER_ECHELLE: controleur.augmenterEchelle(); break;*/
		}
	
}
