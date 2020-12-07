package es.ucm.fdi.ici.c2021.practica2.grupo03.actions;

import java.util.ArrayList;

import es.ucm.fdi.ici.c2021.practica2.grupo03.actions.RemoveMovements;
import es.ucm.fdi.ici.fsm.Action;
import pacman.game.Game;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;

public class MsPacManChaseGhost implements Action {

	DM euristic = DM.PATH;
	double maxPathDistance = 50;
	double eatDistance = 30;
	

	public MsPacManChaseGhost() {

	}

	@Override
	public MOVE execute(Game game) {
		maxPathDistance = 50;
		eatDistance = 110;

		int pacManNode = game.getPacmanCurrentNodeIndex();
		
		ArrayList<MOVE> possibleMoves = new ArrayList<MOVE>();
		
		//Quitamos los movimientos que pongan en problemas a PacMan
		RemoveMovements rm = new RemoveMovements();
		possibleMoves = rm.getPossibleMoves(game);
		
		ArrayList<GHOST> inRangeToEat = new ArrayList<GHOST>();
		for (GHOST ghostType : GHOST.values()) {
			
			double d = game.getDistance(game.getPacmanCurrentNodeIndex(), game.getGhostCurrentNodeIndex(ghostType),
					euristic);

			if (game.isGhostEdible(ghostType) && d <= eatDistance) {
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

		int mv = (int) Math.floor(Math.random() * siz);
		
		if(siz < 1) {
			return MOVE.NEUTRAL;
		}
		return possibleMoves.get(mv);

	}
}
