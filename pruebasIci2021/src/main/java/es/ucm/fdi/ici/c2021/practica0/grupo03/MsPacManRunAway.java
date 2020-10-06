package es.ucm.fdi.ici.c2021.practica0.grupo03;

import pacman.game.Game;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.controllers.PacmanController;

public final class MsPacManRunAway extends PacmanController {
	
	@Override 

	public MOVE getMove(Game game, long timeDue) {
		GHOST closestGhost = null;
		int lastDistance = Integer.MAX_VALUE;
		int currDistance = 0;
		for (GHOST ghostType : GHOST.values()) {
			
			currDistance = game.getShortestPathDistance(game.getPacmanCurrentNodeIndex(), game.getGhostCurrentNodeIndex(ghostType));
			
			if(currDistance < lastDistance) {
				lastDistance = currDistance;
				closestGhost = ghostType;
			}
		}
		System.out.println(
				closestGhost.name()
				);
		return game.getApproximateNextMoveAwayFromTarget(game.getPacmanCurrentNodeIndex(), game.getGhostCurrentNodeIndex(closestGhost), game.getPacmanLastMoveMade(), DM.PATH);
	}
}
