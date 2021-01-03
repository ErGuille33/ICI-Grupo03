package es.ucm.fdi.ici.c2021.practica4.grupo03.ghosts;

import java.util.HashMap;
import java.util.Map;

import es.ucm.fdi.ici.fuzzy.Input;
import pacman.game.Game;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;

public class GhostsInput implements Input{

	double distance = 50;
	double confidence = 100;
	
	@Override
	public void parseInput(Game game) {
		int pos = game.getPacmanCurrentNodeIndex();
		if(pos != -1) {
			distance = game.getDistance(game.getPacmanCurrentNodeIndex(), pos, DM.EUCLID);
			confidence = 100;
		} else if (confidence > 0) {
			confidence-=1;
			distance +=2; 
		}else {
			distance +=.5;
		}
		
		
		
	}

	@Override
	public HashMap<String, Double> getFuzzyValues() {
		HashMap<String,Double> vars = new HashMap<String,Double>();
		vars.put("PACMANdistance",   distance);
		vars.put("PACMANconfidence", confidence);			
		return vars;
	}

}
