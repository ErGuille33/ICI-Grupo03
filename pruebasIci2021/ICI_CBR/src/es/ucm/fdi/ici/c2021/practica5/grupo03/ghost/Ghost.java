package es.ucm.fdi.ici.c2021.practica5.grupo03.ghost;

import java.util.EnumMap;

import es.ucm.fdi.gaia.jcolibri.exception.ExecutionException;
import es.ucm.fdi.ici.c2021.practica5.grupo03.ghost.CBRengine.GhostsCBRengine;
import es.ucm.fdi.ici.c2021.practica5.grupo03.ghost.CBRengine.GhostsStorageManager;


import pacman.controllers.GhostController;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class Ghost extends GhostController {

	GhostsInput input;
	GhostsCBRengine cbrEngine;
	GhostsStorageManager storageManager;
	
	final static String FILE_PATH = "cbrdata/grupo03/Ghost/%s.csv"; //Cuidado!! poner el grupo aquí
	
	public Ghost()
	{
		this.input = new GhostsInput();

		this.storageManager = new GhostsStorageManager();
		
		cbrEngine = new GhostsCBRengine( storageManager);
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
	public EnumMap<GHOST, MOVE> getMove(Game game, long timeDue) {
		
		//This implementation only computes a new action when MsPacMan is in a junction. 
		//This is relevant for the case storage policy
		EnumMap<GHOST,MOVE> result = new EnumMap<GHOST,MOVE>(GHOST.class);		
		if(!game.isJunction(game.getPacmanCurrentNodeIndex()))
		{
			for(GHOST ghost: GHOST.values())
			{
				MOVE move = MOVE.NEUTRAL;
				result.put(ghost, move);
			}
			return result;
		}
			
		try {
			for(GHOST ghost: GHOST.values())
			{
				input.setGhost(ghost);
				input.parseInput(game);
				
				storageManager.setGhost(ghost);
				storageManager.setGame(game);
				
				cbrEngine.setGhost(ghost);
				cbrEngine.setGame(game);
				cbrEngine.cycle(input.getQuery());
				result.put(ghost, cbrEngine.getSolution());
			}
			
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		for(GHOST ghost: GHOST.values())
		{
			MOVE move = MOVE.NEUTRAL;
			result.put(ghost, move);
		}
		return result;
	}

}
