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
	private int[] followIndex = {0, 0};
	private double distanciaAnterior = Double.MAX_VALUE;
	
	private void RunAway(GHOST ghostType, Game game, DM euristic) {
		double limit = 20;
		double d = game.getDistance(game.getPacmanCurrentNodeIndex(), game.getGhostCurrentNodeIndex(ghostType), euristic);
		if(d > limit) {
			moves.put(ghostType, game.getNextMoveTowardsTarget(game.getGhostCurrentNodeIndex(ghostType),
					game.getPowerPillIndices()[ghostType.ordinal()], game.getGhostLastMoveMade(ghostType), euristic));
		}
		else {
			moves.put(ghostType, game.getNextMoveAwayFromTarget(game.getGhostCurrentNodeIndex(ghostType), game.getPacmanCurrentNodeIndex(), euristic));
		}
	}
	
	private void AgressiveMove(GHOST ghostType, Game game, DM euristic) {
		
		if (game.doesGhostRequireAction(ghostType)) {							
			moves.put(ghostType, game.getApproximateNextMoveTowardsTarget(game.getGhostCurrentNodeIndex(ghostType),
					game.getPacmanCurrentNodeIndex(), game.getGhostLastMoveMade(ghostType), euristic));
		}
	}
	
	private void TacticalBehaviour(GHOST ghostType, Game game, DM euristic, int puesto, int bloques) {
		int nodeIndex = game.getPacmanCurrentNodeIndex();
		MOVE[] m;
		for(int i = 0; i < bloques; i++) {
			m = game.getPossibleMoves(nodeIndex, game.getPacmanLastMoveMade());
			if(m.length != 0)
				followIndex[puesto] = game.getNeighbour(nodeIndex, m[puesto%m.length]);
		}
		moves.put(ghostType, game.getNextMoveTowardsTarget(game.getGhostCurrentNodeIndex(ghostType), followIndex[puesto], euristic));
	}
	
	//Añadir comportamiento de que cuando MsPacMan esta justo delante ir recto a por ella
	private void SemirandomBehaviour(GHOST ghostType, Game game, DM euristic) {		
		if(rnd.nextInt(101) <= 40) {
			moves.put(ghostType, game.getNextMoveTowardsTarget(game.getGhostCurrentNodeIndex(ghostType),
					game.getPacmanCurrentNodeIndex(), euristic));
		}
		//O se mueve de forma aleatoria
		else {
			moves.put(ghostType, allMoves[rnd.nextInt(allMoves.length)]);
		}
	}
	
	private void SemiAgrssiveBehaviour(GHOST ghostType, Game game, DM euristic) {
		double d = game.getDistance(game.getPacmanCurrentNodeIndex(), game.getGhostCurrentNodeIndex(ghostType), euristic);
		if(distanciaAnterior < d) {
			AgressiveMove(ghostType, game, euristic);
		}else {
			SemirandomBehaviour(ghostType, game, euristic);
		}
		distanciaAnterior = d;
	}
	
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
		
		if(game.isGhostEdible(GHOST.BLINKY) || pillDist <= limitPowerPill) {
			RunAway(GHOST.BLINKY, game, euristic);
		}
		else {
			AgressiveMove(GHOST.BLINKY, game, euristic);
		}
		
		if(game.isGhostEdible(GHOST.INKY) || pillDist <= limitPowerPill) {
			RunAway(GHOST.INKY, game, euristic);
		}
		else {
			TacticalBehaviour(GHOST.INKY, game, euristic, 0, 2);
		}
		
		if(game.isGhostEdible(GHOST.PINKY) || pillDist <= limitPowerPill) {
			RunAway(GHOST.PINKY, game, euristic);
		}
		else {
			TacticalBehaviour(GHOST.PINKY, game, euristic, 1, 2);
		}
		
		if(game.isGhostEdible(GHOST.SUE) || pillDist <= limitPowerPill) {
			RunAway(GHOST.SUE, game, euristic);
		}
		else {
			SemiAgrssiveBehaviour(GHOST.SUE, game, euristic);
		}
		
		
		
		
		
		/*for (GHOST ghostType : GHOST.values()) {
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
		}*/
		return moves;
	}
}
