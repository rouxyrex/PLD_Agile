package modele;

import java.util.LinkedList;


public class GraphePCC {
	private int nbSommets;
	private LinkedList<Trajet>[] listeAdjacence;
	
	
	public GraphePCC(){
		
	}
	public void initialiserGraphePCC(Plan plan, DemandeLivraison demandeLivraison) {
		
		nbSommets = demandeLivraison.getPtsInteret();
		
		listeAdjacence = new LinkedList[nbSommets];
		
		LinkedList<Trajet> graphe1Sommet;
		
		graphe1Sommet = plan.Dijkstra(demandeLivraison, demandeLivraison.getEntrepot());
		ajouterGraphe1Sommet(graphe1Sommet, 0);
		
		for(int i = 1; i < nbSommets; i++) {
			
			graphe1Sommet = plan.Dijkstra(demandeLivraison, demandeLivraison.getPtsPassage().get(i));
			ajouterGraphe1Sommet(graphe1Sommet, i);
			
		}
		
	}
	
	public void ajouterGraphe1Sommet(LinkedList<Trajet> graphe1Sommet, int position) {
		
		listeAdjacence[position] = graphe1Sommet;
		
	}
	
	public int getNbSommets() {
		return nbSommets;
	}
	
	public void setNbSommets(int nbSommets) {
		this.nbSommets = nbSommets;
	}
	
}
