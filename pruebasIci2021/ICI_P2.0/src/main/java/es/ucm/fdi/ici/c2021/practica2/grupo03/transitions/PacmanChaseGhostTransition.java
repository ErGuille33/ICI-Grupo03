package es.ucm.fdi.ici.c2021.practica2.grupo03.transitions;

import es.ucm.fdi.ici.c2021.practica2.grupo03.pacman.*;
import es.ucm.fdi.ici.fsm.Input;
import es.ucm.fdi.ici.fsm.Transition;
import pacman.game.Game;

public class PacmanChaseGhostTransition implements Transition {
	public PacmanChaseGhostTransition() {
		super();
	}

	@Override
	public boolean evaluate(Input in) {
		MsPacManInput input = (MsPacManInput) in;
		for (int i = 0; i < 4; i++) {
			if (input.isEdibleGhost()[i]) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String toString() {
		return "Chasing Ghost";
	}
}
