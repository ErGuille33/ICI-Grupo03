package es.ucm.fdi.ici.c2021.practica3.grupo03;

import java.util.EnumMap;
import java.util.HashMap;

import es.ucm.fdi.ici.c2021.practica3.grupo03.ghosts.actions.MiddlePath;
import es.ucm.fdi.ici.c2021.practica3.grupo03.ghosts.actions.RunAway;
import es.ucm.fdi.ici.practica3.demorules.ghosts.GhostsInput;
import es.ucm.fdi.ici.practica3.demorules.ghosts.actions.ChaseAction;
import es.ucm.fdi.ici.practica3.demorules.ghosts.actions.RunAwayAction;
import es.ucm.fdi.ici.rules.Action;
import es.ucm.fdi.ici.rules.Input;
import es.ucm.fdi.ici.rules.RuleEngine;
import es.ucm.fdi.ici.rules.observers.ConsoleRuleEngineObserver;
import pacman.controllers.GhostController;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class Ghosts  extends GhostController  {

	private static final String RULES_PATH = "es/ucm/fdi/ici/c2021/practica3/grupo03/";
	
	HashMap<String,Action> map;
	
	EnumMap<GHOST,RuleEngine> ghostRuleEngines;
	
	DM euristic = DM.EUCLID;
	public Ghosts() {
		
		map = new HashMap<String,Action>();
		//Fill Actions
		Action BLINKYmiddlePath = new MiddlePath(GHOST.BLINKY, euristic);
		Action INKYmiddlePath = new MiddlePath(GHOST.INKY, euristic);
		Action PINKYmiddlePath = new MiddlePath(GHOST.PINKY, euristic);
		Action SUEmiddlePath = new MiddlePath(GHOST.SUE, euristic);
		Action BLINKYrunsAway = new RunAway(GHOST.BLINKY, euristic);
		Action INKYrunsAway = new RunAway(GHOST.INKY, euristic);
		Action PINKYrunsAway = new RunAway(GHOST.PINKY, euristic);
		Action SUErunsAway = new RunAway(GHOST.SUE, euristic);
		
		map.put("BLINKYmiddlePath", BLINKYmiddlePath);
		map.put("INKYmiddlePath", INKYmiddlePath);
		map.put("PINKYmiddlePath", PINKYmiddlePath);
		map.put("SUEmiddlePath", SUEmiddlePath);	
		map.put("BLINKYrunsAway", BLINKYrunsAway);
		map.put("INKYrunsAway", INKYrunsAway);
		map.put("PINKYrunsAway", PINKYrunsAway);
		map.put("SUErunsAway", SUErunsAway);
		
		ghostRuleEngines = new EnumMap<GHOST,RuleEngine>(GHOST.class);
		for(GHOST ghost: GHOST.values())
		{
			String rulesFile = String.format("%s/%srules.clp", RULES_PATH, ghost.name().toLowerCase());
			RuleEngine engine  = new RuleEngine(ghost.name(),rulesFile, map);
			ghostRuleEngines.put(ghost, engine);
			
			//add observer to every Ghost
			//ConsoleRuleEngineObserver observer = new ConsoleRuleEngineObserver(ghost.name(), true);
			//engine.addObserver(observer);
		}
		
		//add observer only to BLINKY
		ConsoleRuleEngineObserver observer = new ConsoleRuleEngineObserver(GHOST.BLINKY.name(), true);
		ghostRuleEngines.get(GHOST.BLINKY).addObserver(observer);
		
	}

	@Override
	public EnumMap<GHOST, MOVE> getMove(Game game, long timeDue) {
		
		//Process input
		Input input = new GhostsInput(game);
		//load facts
		//reset the rule engines
		for(RuleEngine engine: ghostRuleEngines.values()) {
			engine.reset();
			engine.assertFacts(input.getFacts());
		}
		
		EnumMap<GHOST,MOVE> result = new EnumMap<GHOST,MOVE>(GHOST.class);		
		for(GHOST ghost: GHOST.values())
		{
			RuleEngine engine = ghostRuleEngines.get(ghost);
			MOVE move = engine.run(game);
			result.put(ghost, move);
		}
		
		return result;
	}

}
