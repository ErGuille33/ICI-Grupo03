package es.ucm.fdi.ici.c2021.practica1.grupo03;

import es.ucm.fdi.ici.fsm.FSM;
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
	private int count = 0;
	private int count1 = 0;
	private int nextJuntion = -1;
	private MOVE lm = MOVE.NEUTRAL;
	
	//Cuando los fantasmas son comestibles, intentan ir hacia las power pills. Si esta muy cerca MsPacMan
	//simplenete intentan alejarse lo maximo posible
	private void RunAway(GHOST ghostType, Game game, DM euristic) {
		double limit = 20;
		double d = game.getShortestPathDistance(game.getPacmanCurrentNodeIndex(), game.getGhostCurrentNodeIndex(ghostType));
		double f = Double.MAX_VALUE;
		GHOST newG = ghostType;
		for(GHOST g : GHOST.values()) {
			double distance = game.getShortestPathDistance(game.getGhostCurrentNodeIndex(g), game.getGhostCurrentNodeIndex(ghostType));
			if(f > distance && g != ghostType) {
				f = distance;
				newG = g;
			}
		}
		if(d < limit) {
			moves.put(ghostType, game.getNextMoveAwayFromTarget(game.getGhostCurrentNodeIndex(ghostType), game.getGhostCurrentNodeIndex(newG), euristic));
		}
		else if(f < limit) {
			moves.put(ghostType, game.getNextMoveAwayFromTarget(game.getGhostCurrentNodeIndex(ghostType), game.getPacmanCurrentNodeIndex(), euristic));
		}
		else {		
			moves.put(ghostType, game.getNextMoveTowardsTarget(game.getGhostCurrentNodeIndex(ghostType),
					game.getPowerPillIndices()[ghostType.ordinal()], game.getGhostLastMoveMade(ghostType), euristic));
		}
	}
	
	//Busca el camino mas corto hacia MsPacMan y va a por el
	private void AgressiveMove(GHOST ghostType, Game game, DM euristic) {
		
		if (game.doesGhostRequireAction(ghostType)) {
			moves.put(ghostType, game.getNextMoveTowardsTarget(game.getGhostCurrentNodeIndex(ghostType), game.getPacmanCurrentNodeIndex(),game.getGhostLastMoveMade(ghostType), euristic));
		}
	}
	
	private void GoToTheNextJunction(GHOST ghostType, Game game, DM euristic) {
		int nodeIndex = game.getPacmanCurrentNodeIndex();
		boolean fuera = false;
		
		while(!game.isJunction(nodeIndex) && !fuera) {
			if((game.getNeighbouringNodes(nodeIndex, game.getPacmanLastMoveMade())) != null)
				nodeIndex = (game.getNeighbouringNodes(nodeIndex, game.getPacmanLastMoveMade()))[0];
			else
				fuera = true;
			
		}
		moves.put(ghostType, game.getApproximateNextMoveTowardsTarget(game.getGhostCurrentNodeIndex(ghostType), nodeIndex, game.getGhostLastMoveMade(ghostType), euristic));
		if(game.getGhostCurrentNodeIndex(ghostType) == nodeIndex) {
			AgressiveMove(ghostType, game, euristic);
		}
	}
	
	//Comprueba hacia donde se movera MsPacMan un numero de bloques despues. Depnediendo del fantasma
	//va hacia la primera desviacion que hay o hacia la segunda
	private void TacticalBehaviour(GHOST ghostType, Game game, DM euristic, int puesto, int bloques) {
		int nodeIndex = game.getPacmanCurrentNodeIndex();
		MOVE[] m;
		if(game.getGhostCurrentNodeIndex(ghostType) == followIndex[puesto]) {
			AgressiveMove(ghostType, game, euristic);
		} else if(count <= 0) {
			for(int i = 0; i < bloques; i++) {
				m = game.getPossibleMoves(nodeIndex, game.getPacmanLastMoveMade());
				if(m.length != 0)
					followIndex[puesto] = game.getNeighbour(nodeIndex, m[puesto%m.length]);
			}
			count = bloques;
			moves.put(ghostType, game.getNextMoveTowardsTarget(game.getGhostCurrentNodeIndex(ghostType), followIndex[puesto], euristic));
			//moves.put(ghostType, game.getNextMoveTowardsTarget(game.getGhostCurrentNodeIndex(ghostType), followIndex[puesto], euristic));
		}else {
			moves.put(ghostType, game.getNextMoveTowardsTarget(game.getGhostCurrentNodeIndex(ghostType), followIndex[puesto], euristic));
		}
		count--;
	}
	
	private void MiddlePath(GHOST ghostType, Game game, DM euristic) {
		int path[] = game.getShortestPath(game.getPacmanCurrentNodeIndex(), game.getGhostCurrentNodeIndex(ghostType), game.getPacmanLastMoveMade());
		
		int n = path.length/2;
		
		moves.put(ghostType, game.getNextMoveTowardsTarget(game.getGhostCurrentNodeIndex(ghostType), path[n], euristic));
	}
	
	//Añadir comportamiento de que cuando MsPacMan esta justo delante ir recto a por ella
	private void SemirandomBehaviour(GHOST ghostType, Game game, DM euristic) {		
		if(rnd.nextInt(101) <= 99) {
			/*moves.put(ghostType, game.getNextMoveTowardsTarget(game.getGhostCurrentNodeIndex(ghostType),
					game.getPacmanCurrentNodeIndex(), euristic));*/
			GoToTheNextJunction(ghostType, game, euristic);
		}
		//O se mueve de forma aleatoria
		else {
			moves.put(ghostType, allMoves[rnd.nextInt(allMoves.length)]);
		}
	}
	
	//Si se esta acercando a MsPacMan usa el comportamiento agresivo. Si no, se mueve practicamente de forma aleatoria
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
		int limitPowerPill = 0; //21
		moves.clear();
		
		//Todos los fantasmas intentan alejarse de MsPacMan si se encuentra cerca de la powerPill
		double pillDist = Double.MAX_VALUE;
		for(int index : game.getActivePowerPillsIndices()) {
			double d = game.getShortestPathDistance(game.getPacmanCurrentNodeIndex(), index, game.getPacmanLastMoveMade());
			if(pillDist > d) {
				pillDist = d;
			}
		}
		
		if(game.isGhostEdible(GHOST.BLINKY) || pillDist <= limitPowerPill) {
			RunAway(GHOST.BLINKY, game, euristic);
		}
		else {
			if (game.doesGhostRequireAction(GHOST.BLINKY))
				MiddlePath(GHOST.BLINKY, game, euristic);
		}
		
		if(game.isGhostEdible(GHOST.INKY) || pillDist <= limitPowerPill) {
			RunAway(GHOST.INKY, game, euristic);
		}
		else {
			if (game.doesGhostRequireAction(GHOST.INKY))
				MiddlePath(GHOST.INKY, game, euristic);
			//TacticalBehaviour(GHOST.INKY, game, euristic, 0, 2);
		}
		
		if(game.isGhostEdible(GHOST.PINKY) || pillDist <= limitPowerPill) {
			RunAway(GHOST.PINKY, game, euristic);
		}
		else {
			if (game.doesGhostRequireAction(GHOST.PINKY))
				MiddlePath(GHOST.PINKY, game, euristic);//, 0, 5);
		}
		
		if(game.isGhostEdible(GHOST.SUE) || pillDist <= limitPowerPill) {
			RunAway(GHOST.SUE, game, euristic);
		}
		else {
			if (game.doesGhostRequireAction(GHOST.SUE))
				MiddlePath(GHOST.SUE, game, euristic);
		}
		
		return moves;
	}
}
