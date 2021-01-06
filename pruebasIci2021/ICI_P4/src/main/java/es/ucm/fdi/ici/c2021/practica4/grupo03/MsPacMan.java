package es.ucm.fdi.ici.c2021.practica4.grupo03;

import es.ucm.fdi.ici.c2021.practica4.grupo03.pacman.PacmanActionSelector;
import es.ucm.fdi.ici.c2021.practica4.grupo03.pacman.PacmanInput;
import es.ucm.fdi.ici.fuzzy.ActionSelector;
import es.ucm.fdi.ici.fuzzy.FuzzyEngine;
import es.ucm.fdi.ici.fuzzy.observers.ConsoleFuzzyEngineObserver;
import pacman.controllers.PacmanController;
import pacman.game.Game;
import pacman.game.Constants.MOVE;

public class MsPacMan extends PacmanController{
    private static final String RULES_PATH = "src/main/java/es/ucm/fdi/ici/c2021/practica4/grupo03/pacman/";
	FuzzyEngine fuzzyEngine;
	PacmanInput input ;
	
	public MsPacMan()
	{
		ActionSelector actionSelector = new PacmanActionSelector();
		input = new PacmanInput();
		 
		ConsoleFuzzyEngineObserver observer = new ConsoleFuzzyEngineObserver("MsPacMan","PacmanRules");
		fuzzyEngine = new FuzzyEngine("MsPacMan",RULES_PATH+"pacman.fcl","FuzzyPacman",actionSelector);
		fuzzyEngine.addObserver(observer);
	}
    @Override
    public MOVE getMove(Game game, long timeDue) {
		input.parseInput(game);
		return fuzzyEngine.run(input.getFuzzyValues(),game);
    }

}
