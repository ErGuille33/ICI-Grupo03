package es.ucm.fdi.ici.c2021.practica2.grupo03.ghosts;

import es.ucm.fdi.ici.fsm.Input;
import pacman.game.Game;
import pacman.game.Constants.GHOST;

public class GhostInput extends Input {

	private boolean BLINKYedible;
	private boolean INKYedible;
	private boolean PINKYedible;
	private boolean SUEedible;
	
	public GhostInput(Game game) {
		super(game);
	}

	@Override
	public void parseInput() {
		this.BLINKYedible = game.isGhostEdible(GHOST.BLINKY);
		this.INKYedible = game.isGhostEdible(GHOST.INKY);
		this.PINKYedible = game.isGhostEdible(GHOST.PINKY);
		this.SUEedible = game.isGhostEdible(GHOST.SUE);
	
	}

	public boolean isBLINKYedible() {
		return BLINKYedible;
	}

	public boolean isINKYedible() {
		return INKYedible;
	}

	public boolean isPINKYedible() {
		return PINKYedible;
	}

	public boolean isSUEedible() {
		return SUEedible;
	}


	
	
}