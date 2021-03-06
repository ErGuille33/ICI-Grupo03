package es.ucm.fdi.ici.c2021.practica0.grupo03.Practica0;


import pacman.game.Game;
import pacman.controllers.PacmanController;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;

public final class MsPacManRunAway extends PacmanController{
	
	@Override
	public MOVE getMove(Game game, long timeDue) {
		double distance = Double.MAX_VALUE;
		GHOST ghost = GHOST.BLINKY;
		for(GHOST ghostType : GHOST.values()) {
			double d = game.getDistance(game.getPacmanCurrentNodeIndex(), game.getGhostCurrentNodeIndex(ghostType), DM.MANHATTAN);
			if(distance > d) {
				distance = d;
				ghost = ghostType;
			}
		}
		System.out.println(ghost.name());
		return game.getNextMoveAwayFromTarget(game.getPacmanCurrentNodeIndex(), game.getGhostCurrentNodeIndex(ghost), DM.MANHATTAN);
	}
}
