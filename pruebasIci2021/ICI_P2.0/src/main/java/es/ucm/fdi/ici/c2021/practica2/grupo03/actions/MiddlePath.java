package es.ucm.fdi.ici.c2021.practica2.grupo03.actions;

import es.ucm.fdi.ici.fsm.Action;
import pacman.game.Game;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;

public class MiddlePath implements Action{
	
	GHOST ghost;
    DM euristic;
	public MiddlePath(GHOST ghost, DM euristic) {
		this.ghost = ghost;
		this.euristic = euristic;
	}
	
	@Override
	public MOVE execute(Game game) {
		if (game.doesGhostRequireAction(ghost)) {
			int path[] = game.getShortestPath(game.getPacmanCurrentNodeIndex(), game.getGhostCurrentNodeIndex(ghost), game.getPacmanLastMoveMade());
			
			int n = path.length/2;
			
			return game.getNextMoveTowardsTarget(game.getGhostCurrentNodeIndex(ghost), path[n], euristic);
			
		}
		
		return MOVE.NEUTRAL;
	}
}
