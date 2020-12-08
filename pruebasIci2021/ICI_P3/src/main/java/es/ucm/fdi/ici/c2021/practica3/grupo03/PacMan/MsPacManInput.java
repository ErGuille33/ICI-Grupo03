package es.ucm.fdi.ici.c2021.practica3.grupo03.PacMan;

import java.util.Collection;
import java.util.Vector;

import es.ucm.fdi.ici.rules.Input;
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

			} 

			}
			i++;
		}


	public boolean isEdibleGhost() {
		
		for (int i = 0; i < 4; i++) {
			if (isEdibleGhost[i]) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public Collection<String> getFacts() {
		Vector<String> facts = new Vector<String>();
		facts.add(String.format("(PacMan (edibleGhost %s))", this.isEdibleGhost));
		return facts;
	}
	


}