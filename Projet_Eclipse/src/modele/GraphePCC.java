package modele;

import java.util.Iterator;
import java.util.LinkedList;

import javafx.util.Pair;

/** Represente un graphe des plus courts chemins pour les trajets d'une demande
*/
public class GraphePCC {
	private int nbSommets;
	private LinkedList<Trajet>[] listeAdjacence;
	Plan plan;
	DemandeLivraison demandeLivraison;
	
	public GraphePCC(Plan plan, DemandeLivraison demandeLivraison){
		this.plan = plan;
		this.demandeLivraison = demandeLivraison;
	}
	
	/** Calcule les plus courts chemins entre toutes les points de passage de la demande de livraison pour le plan donne
	*/
	public void initialiserGraphePCC() {
		
		nbSommets = demandeLivraison.getNbPtsInteret();
		
		listeAdjacence = new LinkedList[nbSommets];
		
		LinkedList<Trajet> grapheIntermediaire;
		
		grapheIntermediaire = plan.Dijkstra(demandeLivraison, new Pair<Integer, Intersection>(0, demandeLivraison.getEntrepot()));
		ajouterGraphIntermediaire(grapheIntermediaire, 0);
		
		for(int i = 1; i < nbSommets; i++) {
			
			grapheIntermediaire = plan.Dijkstra(demandeLivraison, demandeLivraison.getPtsPassage().get(i-1));
			ajouterGraphIntermediaire(grapheIntermediaire, i);
			
		}
		
	}
	
	//a revoir
	/** Ajoute un graphe intermediaire ( gcc partant d'une intersection ) a la liste d'adjacence 
	 * @param grapheIntermediaire  
	 * @param position la position a laquelle ajouter le graphe
	*/
	public void ajouterGraphIntermediaire(LinkedList<Trajet> grapheIntermediaire, int position) {
		
		listeAdjacence[position] = grapheIntermediaire;
		
	}
	
	/** Calcule le trajet le plus court entre deux intersections données
	 * @param interOrigine pair constituee de l'id de l'intersection de depart et de celle-ci
	 * @param interDestination pair constituee de l'id de l'intersection cible et de celle-ci
	 * @return le trajet le plus court entre ces deux intersections
	*/
	public Trajet obtenirTrajetEntreIntersections(Pair<Integer, Intersection> interOrigine, Pair<Integer, Intersection> interDestination) {
		
		for(int i = 0; i < nbSommets; i++) {
			
			LinkedList<Trajet> grapheIntermediaire = listeAdjacence[i];
			
			if( (grapheIntermediaire.getFirst().getIntersectionOrigine().getKey() == interOrigine.getKey()) && (grapheIntermediaire.getFirst().getIntersectionOrigine().getValue().getId() == interOrigine.getValue().getId()) ) {
				
				for(int j = 0 ; j < grapheIntermediaire.size(); j++) {
					
					if( (grapheIntermediaire.get(j).getIntersectionDestination().getKey() == interDestination.getKey()) && (grapheIntermediaire.get(j).getIntersectionDestination().getValue().getId() == interDestination.getValue().getId()) ) {
						return grapheIntermediaire.get(j);
					}
				}
			}
		}
		
		return null;
	}
	
	/** Supprime une livraison du graphe : enleve les deux intersections correspondantes de la liste d'adjacence
	 * @param livraison La livraison a retirer
	*/
	public void supprimerLivraison(Livraison livraison) {
		
		int idLivraisonASupprimer = livraison.getId();
		
		LinkedList<Trajet>[] nouvListeAdjacence = new LinkedList[nbSommets - 2];
		int compteur = 0;
		
		for(int i = 0; i < nbSommets; i++) {
			
			LinkedList<Trajet> grapheIntermediaire = listeAdjacence[i];
			
			if(grapheIntermediaire.getFirst().getIntersectionOrigine().getKey() != idLivraisonASupprimer ) {
				nouvListeAdjacence[compteur] = grapheIntermediaire;
				compteur++;
			}
		}
		
		this.nbSommets = this.nbSommets - 2;
		
		for(int i = 0; i < nbSommets; i++) {
			
			LinkedList<Trajet> grapheIntermediaire = nouvListeAdjacence[i];
			
			for(int j = 0 ; j < grapheIntermediaire.size(); j++) {
				
				if(grapheIntermediaire.get(j).getIntersectionDestination().getKey() == idLivraisonASupprimer) {
					grapheIntermediaire.remove(j);
				}
			}
			
			nouvListeAdjacence[i] = grapheIntermediaire;
		}
		
		this.listeAdjacence = nouvListeAdjacence;
	}
	
	/** Reinitialise la liste d'adjacence
	*/
	public void reset() {
		
		listeAdjacence = null;
		
		nbSommets = 0;
	}
	
	/** Renvoie la liste d'adjacence
	*/
	public LinkedList<Trajet>[] getListeAdjacence() {
		return listeAdjacence;
	}
	
	public int getNbSommets() {
		return nbSommets;
	}
	
	public void setNbSommets(int nbSommets) {
		this.nbSommets = nbSommets;
	}
	
}
