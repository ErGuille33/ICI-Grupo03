package es.ucm.fdi.ici.c2021.practica0.grupo03.Practica0;

import java.util.Random;

import pacman.controllers.PacmanController;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public final class PacManRandom extends PacmanController {
	private Random rnd = new Random();
	private MOVE[] allMoves = MOVE.values();
	
	@Override
	public MOVE getMove(Game game, long timeDue) {
		return allMoves[rnd.nextInt(allMoves.length)];
	}
}
