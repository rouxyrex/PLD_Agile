package Vue;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import XML.ExceptionXml;
import Controleur.Controleur;

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
		case Fenetre.CHARGER_PLAN: try {
				controleur.chargerPlan();
			} catch (NumberFormatException | IOException | ParserConfigurationException | SAXException
					| ExceptionXml e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} break;
		case Fenetre.CHARGER_DEMANDE_LIVRAISON: try {
				controleur.chargerDemandeLivraison();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} break;
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
}
