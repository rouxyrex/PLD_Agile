package modele;

import java.util.LinkedList;


public class GraphePCC {
	private int nbSommets;
	private LinkedList<Trajet>[] listeAdjacence;
	
	
	public GraphePCC(){
		
	}
	
	public void initialiserGraphePCC(Plan plan, DemandeLivraison demandeLivraison) {
		
		nbSommets = demandeLivraison.getNbPtsInteret();
		
		listeAdjacence = new LinkedList[nbSommets];
		
		LinkedList<Trajet> grapheIntermediaire;
		
		grapheIntermediaire = plan.Dijkstra(demandeLivraison, demandeLivraison.getEntrepot());
		ajouterGraphIntermediaire(grapheIntermediaire, 0);
		
		for(int i = 1; i < nbSommets; i++) {
			
			grapheIntermediaire = plan.Dijkstra(demandeLivraison, demandeLivraison.getPtsPassage().get(i));
			ajouterGraphIntermediaire(grapheIntermediaire, i);
			
		}
		
	}
	
	public void ajouterGraphIntermediaire(LinkedList<Trajet> grapheIntermediaire, int position) {
		
		listeAdjacence[position] = grapheIntermediaire;
		
	}
	
	public int getNbSommets() {
		return nbSommets;
	}
	
	public void setNbSommets(int nbSommets) {
		this.nbSommets = nbSommets;
	}
	
}
