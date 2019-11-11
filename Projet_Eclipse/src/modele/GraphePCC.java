package modele;

import java.util.LinkedList;


public class GraphePCC {
	private int nbSommets;
	private LinkedList<Trajet>[] listeAdjacence;
	
	
	public GraphePCC(int nbSommets){
		this.nbSommets = nbSommets;
		listeAdjacence= new LinkedList[nbSommets];
	}
	
	public void ajouterGraphouille(LinkedList<Trajet> graphouille, int position) {
		
		listeAdjacence[position] = graphouille;
		
	}
	
	
	
}
