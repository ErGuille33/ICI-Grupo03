package es.ucm.fdi.ici.c2021.practica4.grupo03.ghosts;

import java.util.HashMap;

import es.ucm.fdi.ici.c2021.practica4.grupo03.ghosts.actions.FindPacman;
import es.ucm.fdi.ici.c2021.practica4.grupo03.ghosts.actions.MiddlePath;
import es.ucm.fdi.ici.fuzzy.Action;
import es.ucm.fdi.ici.fuzzy.ActionSelector;
import es.ucm.fdi.ici.practica4.demofuzzy.actions.GoToPPillAction;
import es.ucm.fdi.ici.practica4.demofuzzy.actions.RunAwayAction;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;

public class GhostsActionSelector implements ActionSelector{

	int FIND_PACMAN_LIMIT = 5;
	GHOST ghost;
	
	public GhostsActionSelector(GHOST g){
		ghost = g;
	}
	
	@Override
	public Action selectAction(HashMap<String, Double> fuzzyOutput) {
		Double findPacman = fuzzyOutput.get("findPacman");
		if(findPacman > this.FIND_PACMAN_LIMIT )
			return new FindPacman(ghost, DM.PATH);
		else
			return new MiddlePath(ghost, DM.PATH);
	}

}
