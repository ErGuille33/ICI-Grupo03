package es.ucm.fdi.ici.c2021.practica3.grupo03.pacman;

import java.util.Collection;
import java.util.Vector;

import es.ucm.fdi.ici.rules.Input;
import pacman.game.Game;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;

public class MsPacManInput extends Input {

	// Blinki, Inky, Pinky ,Sue
	private boolean[] isChasedGhost = new boolean[4];
	private boolean[] edibleGhost = new boolean[4];
	private boolean isEdibleGhost = false;
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
		edibleGhost  = new boolean []  {false,false,false,false};
		isChasedGhost  = new boolean []  {false,false,false,false};
		euristic = DM.PATH;
		int i = 0;
		
		for (GHOST g : GHOST.values()) {
			isChasedGhost[i] = false;
			edibleGhost[i] = false;
			// SI el fantasma esta en distancia y es comestible
			if (game.isGhostEdible(g)) {
				dist = game.getDistance(game.getPacmanCurrentNodeIndex(), game.getGhostCurrentNodeIndex(g),
						euristic);
				if (dist <= eatDistance) {
					edibleGhost[i] = true;
				}

			} 

			}
			i++;
			isEdibleGhost =  isEdibleGhost();
		}


	public boolean isEdibleGhost() {
		
		for (int i = 0; i < 4; i++) {
			if (edibleGhost[i]) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public Collection<String> getFacts() {
		Vector<String> facts = new Vector<String>();
		facts.add(String.format("(MSPACMAN (edibleGhost %s))", this.isEdibleGhost));
		return facts;
	}
	


}