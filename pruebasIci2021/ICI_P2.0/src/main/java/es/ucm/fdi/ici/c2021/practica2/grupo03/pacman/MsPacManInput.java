package es.ucm.fdi.ici.c2021.practica2.grupo03.pacman;

import es.ucm.fdi.ici.fsm.Input;
import pacman.game.Game;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;

public class MsPacManInput extends Input {

	// Blinki, Inky, Pinky ,Sue
	private boolean[] isChasedGhost;
	private boolean[] isEdibleGhost;
	private int maxPathDistance = 50;
	private double dist = 0;

	double eatDistance = 40;
	DM euristic;

	public MsPacManInput(Game game) {
		super(game);
		

	}

	@Override
	public void parseInput() {
		eatDistance = 20;
		maxPathDistance = 50;
		isChasedGhost  = new boolean []  {false,false,false,false};
		isEdibleGhost  = new boolean []  {false,false,false,false};
		euristic = DM.PATH;
		int i = 0;
		
		for (GHOST g : GHOST.values()) {
			isChasedGhost[i] = false;
			isEdibleGhost[i] = false;
			// SI el fantasma esta en distancia y es comestible
			if (game.isGhostEdible(g)) {
				dist = game.getDistance(game.getPacmanCurrentNodeIndex(), game.getGhostCurrentNodeIndex(g),
						euristic);
				if (dist <= eatDistance) {
					isEdibleGhost[i] = true;
				}

			} else {
				// Si el fantasma se dirije en la misma dirección
				if (game.getGhostLastMoveMade(g) == game.getNextMoveTowardsTarget(game.getGhostCurrentNodeIndex(g),
						game.getPacmanCurrentNodeIndex(), euristic)
						&& game.getShortestPathDistance(game.getPacmanCurrentNodeIndex(),
								game.getGhostCurrentNodeIndex(g)) <= maxPathDistance) {
					isChasedGhost[i] = true;
				}

			}
			i++;
		}

	}

	public boolean[] isChasedByGhost() {
		return isChasedGhost;
	}

	public boolean[] isEdibleGhost() {
		return isEdibleGhost;
	}
	


}
