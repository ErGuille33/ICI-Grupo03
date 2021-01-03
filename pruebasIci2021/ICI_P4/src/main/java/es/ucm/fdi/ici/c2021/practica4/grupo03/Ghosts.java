package es.ucm.fdi.ici.c2021.practica4.grupo03;

import java.util.EnumMap;
import java.util.Random;

import es.ucm.fdi.ici.c2021.practica4.grupo03.ghosts.GhostsActionSelector;
import es.ucm.fdi.ici.c2021.practica4.grupo03.ghosts.GhostsInput;
import es.ucm.fdi.ici.fuzzy.ActionSelector;
import es.ucm.fdi.ici.fuzzy.FuzzyEngine;
import es.ucm.fdi.ici.fuzzy.observers.ConsoleFuzzyEngineObserver;
import pacman.controllers.GhostController;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class Ghosts extends GhostController{

	private EnumMap<GHOST, MOVE> moves = new EnumMap<GHOST, MOVE>(GHOST.class);
    private MOVE[] allMoves = MOVE.values();
    private Random rnd = new Random();

    
    private static final String RULES_PATH = "src/main/java/es/ucm/fdi/ici/c2021/practica4/grupo03/ghosts/";
	FuzzyEngine[] fuzzyEngine = {null, null, null, null};
	GhostsInput[] input = {null, null, null, null};
	
	public Ghosts()
	{
		for (GHOST ghostType : GHOST.values()) {
			ActionSelector actionSelector = new GhostsActionSelector(ghostType);
			int n = ghostType.ordinal();
			input[n] = new GhostsInput();
			fuzzyEngine[n] = new FuzzyEngine("Ghosts",RULES_PATH+"ghosts.fcl","FuzzyGhosts",actionSelector);
			ConsoleFuzzyEngineObserver observer = new ConsoleFuzzyEngineObserver("Ghosts","GhostsRules");
			fuzzyEngine[n].addObserver(observer);
		}
	}
    @Override
    public EnumMap<GHOST, MOVE> getMove(Game game, long timeDue) {
        moves.clear();
        for (GHOST ghostType : GHOST.values()) {
        	game = game.copy(ghostType);
        	int n = ghostType.ordinal();
            if (game.doesGhostRequireAction(ghostType)) {
                input[n].parseInput(game);
        		moves.put(ghostType, fuzzyEngine[n].run(input[n].getFuzzyValues(),game));
            }
        }
        return moves;
    }

}