package es.ucm.fdi.ici.c2021.practica1.grupo03;

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
		
		GHOST observableGhost[]=new GHOST[4];
		
		/*Busca el fantasma mas cercano*/
		int i=0;
		for(GHOST ghostType : GHOST.values()) {
			double d = game.getDistance(game.getPacmanCurrentNodeIndex(), game.getGhostCurrentNodeIndex(ghostType), euristic);
			if(distance > d) {
				distance = d;
				ghost = ghostType;
			}
			if(game.isNodeObservable(game.getGhostCurrentNodeIndex(ghostType))) {
				if(i<4) {
					observableGhost[i]=ghostType;
					i++;
				}
			}
		}
				
		//Si hay un fantasma muy cerca
		boolean ghostInFront=false;
		for(GHOST ghostType: observableGhost) {
			switch(game.getPacmanLastMoveMade()){
			case UP:
				if(game.getGhostLastMoveMade(ghostType)==MOVE.DOWN)
					ghostInFront=true;
					break;
			case DOWN:
				if(game.getGhostLastMoveMade(ghostType)==MOVE.UP)
					ghostInFront=true;
				break;
			case LEFT:
				if(game.getGhostLastMoveMade(ghostType)==MOVE.RIGHT)
					ghostInFront=true;
				break;
			case RIGHT:
				if(game.getGhostLastMoveMade(ghostType)==MOVE.LEFT)
					ghostInFront=true;
				break;
			default:
					break;
			}
			
		}
		if(ghostInFront) 
			return game.getNextMoveAwayFromTarget(game.getPacmanCurrentNodeIndex(), game.getGhostCurrentNodeIndex(ghost), euristic);			

		else if(!game.isGhostEdible(ghost) && distance <= limitGhost) {
			return game.getNextMoveAwayFromTarget(game.getPacmanCurrentNodeIndex(), game.getGhostCurrentNodeIndex(ghost), euristic);			
		}
		//Si es comestible
		else if(game.isGhostEdible(ghost)) {
			return game.getNextMoveTowardsTarget(game.getPacmanCurrentNodeIndex(), 
					game.getGhostCurrentNodeIndex(ghost), euristic);
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
