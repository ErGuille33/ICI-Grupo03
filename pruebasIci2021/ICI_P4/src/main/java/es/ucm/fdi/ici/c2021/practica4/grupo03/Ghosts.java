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

    
    private static final String RULES_PATH = "src/main/java/es/ucm/fdi/ici/practica4/demofuzzy/";
	FuzzyEngine fuzzyEngine;
	GhostsInput input ;
	
	public Ghosts()
	{
		ActionSelector actionSelector = new GhostsActionSelector();
		input = new GhostsInput();
		 
		ConsoleFuzzyEngineObserver observer = new ConsoleFuzzyEngineObserver("MsPacMan","MsPacManRules");
		fuzzyEngine = new FuzzyEngine("MsPacMan",RULES_PATH+"mspacman.fcl","FuzzyMsPacMan",actionSelector);
		fuzzyEngine.addObserver(observer);
	}
    @Override
    public EnumMap<GHOST, MOVE> getMove(Game game, long timeDue) {
        moves.clear();
        for (GHOST ghostType : GHOST.values()) {
            if (game.doesGhostRequireAction(ghostType)) {
                moves.put(ghostType, allMoves[rnd.nextInt(allMoves.length)]);
            }
        }
        return moves;
    }

}
