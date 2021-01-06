package es.ucm.fdi.ici.c2021.practica4.grupo03.pacman;
import java.util.HashMap;

import es.ucm.fdi.ici.c2021.practica4.grupo03.pacman.actions.*;
import es.ucm.fdi.ici.fuzzy.Action;
import es.ucm.fdi.ici.fuzzy.ActionSelector;
import pacman.game.Constants.DM;

public class PacmanActionSelector implements ActionSelector {
	double chasePillLimit=20;
	
	public Action selectAction(HashMap<String, Double> fuzzyOutput) {
			Double pacManAction = fuzzyOutput.get("pacManAction");
			if(pacManAction > this.chasePillLimit )
				return new chaseGhost();
			else
				return new chasePill();
		}
}


