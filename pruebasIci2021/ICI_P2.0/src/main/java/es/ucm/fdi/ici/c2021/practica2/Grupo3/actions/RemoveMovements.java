package es.ucm.fdi.ici.c2021.practica2.Grupo3.actions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import pacman.game.Game;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;

public class RemoveMovements {
	
	DM euristic = DM.PATH;
	
	double maxPathDistance = 50;
	double eatDistance = 40;
	
	double bonusPill = 1;
	double ghostEdibleBonus = 40;
	double ghostChasingPenalty = 50;
	
	RemoveMovements(){
		
	}
	
	
	//Este método devuelve los movimientos que no ponen en peligro a PacMan 
	public ArrayList<MOVE> getPossibleMoves(Game game) {
		int pacManNode = game.getPacmanCurrentNodeIndex();

		MOVE[] moves = game.getPossibleMoves(pacManNode, game.getPacmanLastMoveMade());

		ArrayList<MOVE> possibleMoves = new ArrayList<MOVE>();
		// Guarda los movimientos posibles
		for (MOVE m : moves) {
			possibleMoves.add(m);
		}
		for (GHOST ghostType : GHOST.values()) {

			int ghostNode = game.getGhostCurrentNodeIndex(ghostType);

			// Si estan dentro del radio, no son comestibles.
			if (!game.isGhostEdible(ghostType)) {
				// Si el estan dentro del radio maximo siguiendo el camino
				if (game.getShortestPathDistance(game.getPacmanCurrentNodeIndex(),
						game.getGhostCurrentNodeIndex(ghostType)) <= maxPathDistance) {
					// Borran los posibles movimientos que le dirijan hacia un fantasma demasiado
					// cercano que vaya hacia él
					if (game.getGhostLastMoveMade(ghostType) == game.getNextMoveTowardsTarget(ghostNode, pacManNode,
							euristic)) {
						possibleMoves.remove(game.getNextMoveTowardsTarget(pacManNode, ghostNode, euristic));
					}
				}
			} // Si es comestible y está cerca.
		}
		
		return possibleMoves;
	}
	public HashMap<MOVE, Double> getScoredMoves(Game game){
		HashMap<MOVE, Double>scoredMoves=new HashMap<MOVE, Double>();
		
		MOVE[] moves = game.getPossibleMoves(game.getPacmanCurrentNodeIndex(), game.getPacmanLastMoveMade());

		for(MOVE m: moves) {
			double score=calculateScore(game, m);
			scoredMoves.put(m, score);

			if(game.isJunction(game.getPacmanCurrentNodeIndex())) 
				System.out.println("MOVE "+ m + " SCORE " + score);
		}
		
		return scoredMoves;
	}
	private double calculateScore(Game game, MOVE direction) {
		double score = 0;
		int pacManNode = game.getPacmanCurrentNodeIndex();
		for(int pill : game.getActivePillsIndices()) {
			if(game.getNextMoveTowardsTarget(pacManNode, pill, euristic) == direction) {
				score += bonusPill/game.getDistance(pacManNode, pill, euristic);
			}
		}
		for(GHOST ghost: GHOST.values()) {
			int ghostNode = game.getGhostCurrentNodeIndex(ghost);
			double distanceToGhost = game.getDistance(pacManNode, ghostNode, euristic);
			if(!game.isGhostEdible(ghost)) {
				if(game.getGhostLastMoveMade(ghost) == game.getNextMoveTowardsTarget(ghostNode, pacManNode, euristic)
						&& game.getNextMoveTowardsTarget(pacManNode, ghostNode, euristic) == direction 
						&& game.getDistance(ghostNode, pacManNode, euristic) <= maxPathDistance){
					score -= ghostChasingPenalty/distanceToGhost;
				}
			}
			else if(distanceToGhost<=eatDistance && game.getNextMoveTowardsTarget(pacManNode, ghostNode, euristic)==direction) {
				score += ghostEdibleBonus/distanceToGhost;
			}
		}
		
		return score;
	}
	
	public MOVE getHighestScoreMove(HashMap<MOVE, Double> scoredMoves) {
		MOVE bestMove = MOVE.NEUTRAL;
		double highestScore = Double.NEGATIVE_INFINITY;
		for(Map.Entry<MOVE, Double> entry: scoredMoves.entrySet()) {
			if(entry.getValue() >= highestScore) {
				bestMove = entry.getKey();
				highestScore = entry.getValue();
			}
		}
		return bestMove;
	}
	
}