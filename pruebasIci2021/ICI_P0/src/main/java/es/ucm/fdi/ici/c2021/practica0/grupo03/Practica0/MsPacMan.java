package es.ucm.fdi.ici.c2021.practica0.grupo03.Practica0;

import pacman.game.Game;
import pacman.controllers.PacmanController;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;

public final class MsPacMan extends PacmanController{
	@Override
	public MOVE getMove(Game game, long timeDue) {
		int limitGhost = 20;
		int limitPowerPill = 30;
		double distance = Double.MAX_VALUE;
		GHOST ghost = GHOST.BLINKY;
		DM euristic = DM.EUCLID;
		
		/*Busca el fantasma mas cercano*/
		for(GHOST ghostType : GHOST.values()) {
			double d = game.getDistance(game.getPacmanCurrentNodeIndex(), game.getGhostCurrentNodeIndex(ghostType), euristic);
			if(distance > d) {
				distance = d;
				ghost = ghostType;
			}
		}
		
		//Si es comestible
		if(game.isGhostEdible(ghost)) {
			return game.getNextMoveTowardsTarget(game.getPacmanCurrentNodeIndex(), 
					game.getGhostCurrentNodeIndex(ghost), euristic);
		}
		//Si hay un fantasma muy cerca
		else if(distance <= limitGhost) {
			return game.getNextMoveAwayFromTarget(game.getPacmanCurrentNodeIndex(), game.getGhostCurrentNodeIndex(ghost), euristic);			
		}
		else {
			//Prioriza ir a por una powerpill cercana y si no hay ninguna
			//va a por las pills mas cercanas
			
			double pillDist = Double.MAX_VALUE;
			int pillIndex = 0;
			for(int index : game.getActivePowerPillsIndices()) {
				double d = game.getDistance(game.getPacmanCurrentNodeIndex(), index, euristic);
				if(pillDist > d) {
					pillDist = d;
					pillIndex = index;
				}
			}
			if(pillDist <= limitPowerPill) {
				return game.getNextMoveTowardsTarget(game.getPacmanCurrentNodeIndex(), 
						pillIndex, euristic);
			}
			
			pillDist = Double.MAX_VALUE;
			for(int index : game.getActivePillsIndices()) {
				double d = game.getDistance(game.getPacmanCurrentNodeIndex(), index, euristic);
				if(pillDist > d) {
					pillDist = d;
					pillIndex = index;
				}
			}
			
			return game.getNextMoveTowardsTarget(game.getPacmanCurrentNodeIndex(), 
					pillIndex, euristic);
				
		}
	}
}
