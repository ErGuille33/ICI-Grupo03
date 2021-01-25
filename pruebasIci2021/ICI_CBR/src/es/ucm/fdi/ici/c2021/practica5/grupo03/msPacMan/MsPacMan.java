package es.ucm.fdi.ici.c2021.practica5.grupo03.msPacMan;

import java.util.ArrayList;

import java.util.List;

import es.ucm.fdi.gaia.jcolibri.exception.ExecutionException;
import es.ucm.fdi.ici.c2021.practica5.grupo03.msPacMan.CBRengine.MsPacManCBRengine;
import es.ucm.fdi.ici.c2021.practica5.grupo03.msPacMan.CBRengine.MsPacManStorageManager;

import pacman.controllers.PacmanController;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class MsPacMan extends PacmanController {

	MsPacManInput input;
	MsPacManCBRengine cbrEngine;
	MsPacManStorageManager storageManager;
	
	final static String FILE_PATH = "cbrdata/grupo03/MsPacMan/%s.csv"; //Cuidado!! poner el grupo aquí
	
	public MsPacMan()
	{
		this.input = new MsPacManInput();

		this.storageManager = new MsPacManStorageManager();
		
		cbrEngine = new MsPacManCBRengine( storageManager);
	}
	
	@Override
	public void preCompute(String opponent) {
		cbrEngine.setCaseBaseFile(String.format(FILE_PATH, opponent));
		try {
			cbrEngine.configure();
			cbrEngine.preCycle();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}
	
	
	@Override
	public void postCompute() {
		try {
			cbrEngine.postCycle();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}
	
	
	@Override
	public MOVE getMove(Game game, long timeDue) {
		
		//This implementation only computes a new action when MsPacMan is in a junction. 
		//This is relevant for the case storage policy
		if(!game.isJunction(game.getPacmanCurrentNodeIndex()))
			return MOVE.NEUTRAL;
		
		
		try {
			input.parseInput(game);
			storageManager.setGame(game);
			cbrEngine.setGame(game);
			cbrEngine.cycle(input.getQuery());
			MOVE move = cbrEngine.getSolution();
			return move;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return MOVE.NEUTRAL;
	}

}
