package es.ucm.fdi.ici.c2021.practica2.Grupo3.transitions;

import es.ucm.fdi.ici.fsm.Input;
import es.ucm.fdi.ici.fsm.Transition;
import es.ucm.fdi.ici.c2021.practica2.Grupo3.pacman.*;

public class PacmanChasePillTransition implements Transition {

	public PacmanChasePillTransition() {
		super();
	}

	@Override
	public boolean evaluate(Input in) {
		MsPacManInput input = (MsPacManInput) in;
		for (int i = 0; i < 4; i++) {
			if (input.isEdibleGhost()[i]) {
				return false;
			}
		}
		return true;

	}

	@Override
	public String toString() {
		return "Chasing Pills";
	}

}
