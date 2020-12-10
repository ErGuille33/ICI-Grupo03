package es.ucm.fdi.ici.c2021.practica3.grupo03.pacman.Actions;
import java.util.ArrayList;

import pacman.game.Game;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;

public class RemoveMovements {
	
	DM euristic = DM.PATH;
	
	double maxPathDistance = 50;
	double eatDistance = 40;
	
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
	
}