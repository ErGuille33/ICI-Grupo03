package es.ucm.fdi.ici.c2021.practica2.Grupo3.actions;

import es.ucm.fdi.ici.fsm.Action;
import pacman.game.Game;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;

public class RunAway implements Action {

    GHOST ghost;
    DM euristic;
	public RunAway(GHOST ghost, DM euristic) {
		this.ghost = ghost;
		this.euristic = euristic;
	}

	@Override
	public MOVE execute(Game game) {
        if (game.doesGhostRequireAction(ghost))        //if it requires an action
        {
        	double limit = 20;
    		double d = game.getShortestPathDistance(game.getPacmanCurrentNodeIndex(), game.getGhostCurrentNodeIndex(ghost));
    		double f = Double.MAX_VALUE;
    		GHOST newG = ghost;
    		for(GHOST g : GHOST.values()) {
    			double distance = game.getShortestPathDistance(game.getGhostCurrentNodeIndex(g), game.getGhostCurrentNodeIndex(ghost));
    			if(f > distance && g != ghost) {
    				f = distance;
    				newG = g;
    			}
    		}
    		if(d < limit) {
    			return game.getNextMoveAwayFromTarget(game.getGhostCurrentNodeIndex(ghost), game.getGhostCurrentNodeIndex(newG), euristic);
    		}
    		else if(f < limit) {
    			return game.getNextMoveAwayFromTarget(game.getGhostCurrentNodeIndex(ghost), game.getPacmanCurrentNodeIndex(), euristic);
    		}
    		else {		
    			return game.getNextMoveTowardsTarget(game.getGhostCurrentNodeIndex(ghost),
    					game.getPowerPillIndices()[ghost.ordinal()], game.getGhostLastMoveMade(ghost), euristic);
    		}
        }        
            
        return MOVE.NEUTRAL;	
	}
}

