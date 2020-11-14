package es.ucm.fdi.ici.c2021.practica2.Grupo3.transitions;

import es.ucm.fdi.ici.fsm.Input;
import es.ucm.fdi.ici.fsm.Transition;
import es.ucm.fdi.ici.practica2.demofsm.ghosts.GhostsInput;
import pacman.game.Constants.GHOST;

public class GhostNotEdibleTransition implements Transition  {

	GHOST ghost;
	public GhostNotEdibleTransition(GHOST ghost) {
		super();
		this.ghost = ghost;
	}


	@Override
	public boolean evaluate(Input in) {
		GhostsInput input = (GhostsInput)in;
		switch(ghost) {
			case BLINKY:
				return !input.isBLINKYedible();
			case INKY:
				return !input.isINKYedible();
			case PINKY:
				return !input.isPINKYedible();
			case SUE:
				return !input.isSUEedible();
			default:
				return false;
		}
	}



	@Override
	public String toString() {
		return "Ghost is not edible";
	}

	
	
}