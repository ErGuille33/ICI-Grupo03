package es.ucm.fdi.ici.c2021.practica3.grupo03;

import java.util.HashMap;

import es.ucm.fdi.ici.c2021.practica3.grupo03.PacMan.*;
import es.ucm.fdi.ici.c2021.practica3.grupo03.PacMan.Actions.*;
import es.ucm.fdi.ici.rules.Action;
import es.ucm.fdi.ici.rules.Input;
import es.ucm.fdi.ici.rules.RuleEngine;
import es.ucm.fdi.ici.rules.observers.ConsoleRuleEngineObserver;
import pacman.controllers.*;
import pacman.game.Game;
import pacman.game.Constants.DM;
import pacman.game.Constants.MOVE;

public class MsPacMan extends PacmanController {
private static final String RULES_PATH = "es/ucm/fdi/ici/c2021/practica3/grupo03/";
	
	HashMap<String,Action> map;
	
	RuleEngine pacManRuleEngine;
	
	DM euristic = DM.EUCLID;
	public MsPacMan() {
		
		map = new HashMap<String,Action>();
		//Fill Actions
		Action MsPacManChaseGhost = new MsPacManChaseGhost();
		Action MsPacManChasePill = new MsPacManChasePill();
	
		
		map.put("MsPacManChaseGhost", MsPacManChaseGhost);
		map.put("MsPacManChasePill", MsPacManChasePill);

		String rulesFile = String.format("%s/%srules.clp", RULES_PATH, "mspacman");
		pacManRuleEngine = new RuleEngine("MSPACMAN",rulesFile,map);
		
		//add observer only to BLINKY
		ConsoleRuleEngineObserver observer = new ConsoleRuleEngineObserver("MSPACMAN", true);
		pacManRuleEngine.addObserver(observer);
		
	}

	@Override
	public MOVE getMove(Game game, long timeDue) {
		
		//Process input
		Input input = new MsPacManInput(game);
		//load facts
		//reset the rule engines
		
		pacManRuleEngine.reset();
		pacManRuleEngine.assertFacts(input.getFacts());
	
		MOVE move = pacManRuleEngine.run(game);
		
		return move;
	}


}
