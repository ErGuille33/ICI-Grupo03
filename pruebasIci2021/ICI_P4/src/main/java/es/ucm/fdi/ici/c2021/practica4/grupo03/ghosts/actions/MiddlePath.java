package es.ucm.fdi.ici.c2021.practica4.grupo03.ghosts.actions;

import java.util.Random;

import es.ucm.fdi.ici.fuzzy.Action;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class MiddlePath implements Action {
	
	private Random rnd = new Random();
    private MOVE[] allMoves = MOVE.values();
    
    int mid;
    GHOST ghost;
    DM euristic;
	public MiddlePath(GHOST ghost, DM euristic) {
		this.ghost = ghost;
		this.euristic = euristic;
	}
	
	@Override
	public MOVE execute(Game game) {
		game = game.copy(ghost);
		if (game.doesGhostRequireAction(ghost)) {
			if(game.getPacmanCurrentNodeIndex() != -1) {
				if(game.isGhostEdible(ghost)) {
					return game.getNextMoveAwayFromTarget(game.getPacmanCurrentNodeIndex(), 
							game.getGhostCurrentNodeIndex(ghost), euristic);
				}
				int path[] = game.getShortestPath(game.getPacmanCurrentNodeIndex(), 
						game.getGhostCurrentNodeIndex(ghost), game.getPacmanLastMoveMade());
				int n = path.length/2;				
				mid = path[n];
			}
			
			
			return game.getNextMoveTowardsTarget(game.getGhostCurrentNodeIndex(ghost), mid, euristic);
			
		}
		
		return MOVE.NEUTRAL;
    }
}
