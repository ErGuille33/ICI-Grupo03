package es.ucm.fdi.ici.c2021.practica4.grupo03.pacman;
import java.util.HashMap;

import es.ucm.fdi.ici.fuzzy.Input;
import pacman.game.Game;
import pacman.game.Constants.DM;

public class PacmanInput implements Input {
	public void parseInput(Game game) {	
		
	}
	
	@Override
	public HashMap<String, Double> getFuzzyValues() {
		HashMap<String,Double> vars = new HashMap<String,Double>();
		vars.put("PACMANdistance",   distance);
		vars.put("PACMANconfidence", confidence);			
		return vars;
	}
}
