package es.ucm.fdi.ici.c2021.practica2.Grupo3.actions;

import java.util.ArrayList;

import es.ucm.fdi.ici.fsm.Action;
import pacman.game.Game;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;

public class MsPacManChaseGhost implements Action {

	DM euristic = DM.PATH;
	double maxPathDistance = 50;
	double eatDistance = 40;

	public MsPacManChaseGhost() {

	}

	@Override
	public MOVE execute(Game game) {

		int pacManNode = game.getPacmanCurrentNodeIndex();

		MOVE[] moves = game.getPossibleMoves(pacManNode, game.getPacmanLastMoveMade());

		ArrayList<MOVE> possibleMoves = new ArrayList<MOVE>();
		// Guarda los movimientos posibles
		for (MOVE m : moves) {
			possibleMoves.add(m);
		}
		ArrayList<GHOST> inRangeToEat = new ArrayList<GHOST>();
		for (GHOST ghostType : GHOST.values()) {

			int ghostNode = game.getGhostCurrentNodeIndex(ghostType);

			double d = game.getDistance(game.getPacmanCurrentNodeIndex(), game.getGhostCurrentNodeIndex(ghostType),
					euristic);
			// Si estan dentro del radio, no son comestibles.
			if (!game.isGhostEdible(ghostType)) {
				// Si el estan dentro del radio maximo siguiendo el camino
				if (game.getShortestPathDistance(game.getPacmanCurrentNodeIndex(),
						game.getGhostCurrentNodeIndex(ghostType)) <= maxPathDistance) {
					// Borran los posibles movimientos que le dirijan hacia un fantasma demasiado
					// cercano que vaya hacia él
					if (game.getGhostLastMoveMade(ghostType) == game.getNextMoveTowardsTarget(ghostNode, pacManNode,
							euristic)) {
						possibleMoves.remove(game.getNextMoveTowardsTarget(pacManNode, ghostNode, euristic));
					}
				}
			} // Si es comestible y está cerca.
			else if (game.isGhostEdible(ghostType) && d <= eatDistance) {
				inRangeToEat.add(ghostType);
			}
		}

		// Mira si los comestibles estan en un rango de movimientos posible
		for (GHOST g : inRangeToEat) {
			MOVE auxMove = game.getNextMoveTowardsTarget(pacManNode, game.getGhostCurrentNodeIndex(g), euristic);
			if (possibleMoves.contains(auxMove)) {
				return auxMove;
			}
		}
		
		int siz = possibleMoves.size();
		
		int mv = (int)Math.floor(Math.random()*siz);
			
		return possibleMoves.get(mv);

	}
}
