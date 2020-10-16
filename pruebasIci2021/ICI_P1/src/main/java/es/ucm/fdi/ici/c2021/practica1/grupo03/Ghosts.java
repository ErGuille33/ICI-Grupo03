package es.ucm.fdi.ici.c2021.practica1.grupo03;

import java.util.EnumMap;
import java.util.Random;

import pacman.controllers.GhostController;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public final class Ghosts extends GhostController {
	private EnumMap<GHOST, MOVE> moves = new EnumMap<GHOST, MOVE>(GHOST.class);
	private MOVE[] allMoves = MOVE.values();
	private Random rnd = new Random();
	
	@Override
	public EnumMap<GHOST, MOVE> getMove(Game game, long timeDue) {
		DM euristic = DM.EUCLID;
		int limitPowerPill = 20;
		moves.clear();
		
		double pillDist = Double.MAX_VALUE;
		for(int index : game.getActivePowerPillsIndices()) {
			double d = game.getDistance(game.getPacmanCurrentNodeIndex(), index, euristic);
			if(pillDist > d) {
				pillDist = d;
			}
		}
		
		for (GHOST ghostType : GHOST.values()) {
			if (game.doesGhostRequireAction(ghostType)) {
				
				//Se mueve lejos de MsPacMan si el fantasma puede ser comido o la distancia a una pill es pequeña
				if(game.isGhostEdible(ghostType) || pillDist <= limitPowerPill) {
					moves.put(ghostType, game.getNextMoveAwayFromTarget(game.getGhostCurrentNodeIndex(ghostType), game.getPacmanCurrentNodeIndex(), euristic));
				}
				//Va directo a MsPacMan con probabilidad 90%
				else if(rnd.nextInt(101) <= 90) {
					moves.put(ghostType, game.getNextMoveTowardsTarget(game.getGhostCurrentNodeIndex(ghostType),
							game.getPacmanCurrentNodeIndex(), euristic));
				}
				//O se mueve de forma aleatoria
				else {
					moves.put(ghostType, allMoves[rnd.nextInt(allMoves.length)]);
				}
			}
		}
		return moves;
	}
}
