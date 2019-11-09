package vue;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;

import controleur.Controleur;
import modele.DemandeLivraison;

public class VueTextuelle extends JPanel{
	
	Fenetre fenetre;
	Controleur controleur;

	public VueTextuelle(Fenetre fenetre, Controleur controleur) {
		this.fenetre = fenetre;
		this.controleur = controleur; 
	}

	public void afficherDemandeLivraison() { 
		DefaultListModel listModel = new DefaultListModel();
		DemandeLivraison dl = controleur.getDemandeLivraison();
		
		for(int i = 0; i < dl.getNbLivraisons(); i++) { 
			listModel.addElement(dl.getLivraisons().get(i).toString());
		} 
		
		JList list = new JList(listModel);
		list.setLayoutOrientation(JList.VERTICAL);
		this.add(list, BorderLayout.EAST);
		this.setPreferredSize(new Dimension(300,100)); 
		setBorder(BorderFactory.createTitledBorder("Demande de livraison")); 
		
	/*	add(new JTextArea(""));
		add(new JTextArea("Départ"));
		add(new JTextArea("Drapeau"));
		add(new JTextArea(""));
		add(new JTextArea("Adresse n°1"));
		add(new JTextArea("8h30"));
		add(new JTextArea("****"));
		add(new JTextArea("***Départ"));
		add(new JTextArea("***Drapeau"));
		add(new JTextArea("***"));
		add(new JTextArea("***Adresse n°1"));
		add(new JTextArea("****8h30")); */
		fenetre.getContentPane().add(this, BorderLayout.EAST); 
		repaint();
	}

	public void afficherTournee() {
		DefaultListModel listModel = new DefaultListModel();
		JList list = new JList(listModel);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}
}
