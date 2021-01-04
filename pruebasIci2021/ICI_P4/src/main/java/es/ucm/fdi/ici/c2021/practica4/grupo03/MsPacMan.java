package es.ucm.fdi.ici.c2021.practica4.grupo03;
import java.util.EnumMap;
import java.util.Random;

import es.ucm.fdi.ici.c2021.practica4.grupo03.ghosts.GhostsActionSelector;
import es.ucm.fdi.ici.c2021.practica4.grupo03.ghosts.GhostsInput;
import es.ucm.fdi.ici.fuzzy.ActionSelector;
import es.ucm.fdi.ici.fuzzy.FuzzyEngine;
import es.ucm.fdi.ici.fuzzy.observers.ConsoleFuzzyEngineObserver;
import pacman.controllers.PacmanController;
import pacman.game.Game;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;

public class MsPacMan extends PacmanController{
	private MOVE moves = MOVE.NEUTRAL;
    private MOVE[] allMoves = MOVE.values();
    private Random rnd = new Random();

    
    private static final String RULES_PATH = "src/main/java/es/ucm/fdi/ici/practica4/demofuzzy/";
	FuzzyEngine fuzzyEngine;
	GhostsInput input ;
	
	public MsPacMan()
	{
		ActionSelector actionSelector = new GhostsActionSelector();
		input = new GhostsInput();
		 
		ConsoleFuzzyEngineObserver observer = new ConsoleFuzzyEngineObserver("MsPacMan","MsPacManRules");
		fuzzyEngine = new FuzzyEngine("MsPacMan",RULES_PATH+"mspacman.fcl","FuzzyMsPacMan",actionSelector);
		fuzzyEngine.addObserver(observer);
	}
    @Override
    public MOVE getMove(Game game, long timeDue) {
        for (GHOST ghostType : GHOST.values()) {
            if (game.doesGhostRequireAction(ghostType)) {
                moves.put(ghostType, allMoves[rnd.nextInt(allMoves.length)]);
            }
        }
        return moves;
    }

}
