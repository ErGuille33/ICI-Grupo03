package es.ucm.fdi.ici.c2021.practica1.grupo03;

import pacman.game.Game;

import java.util.Arrays;

import pacman.controllers.PacmanController;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;

public final class MsPacMan extends PacmanController{
	int limitGhost = 20;
	int limitPowerPill = 30;
	double distance = Double.MAX_VALUE;
	GHOST ghost = GHOST.BLINKY;
	GHOST frontGhost = GHOST.BLINKY;
	DM euristic = DM.EUCLID;
	MOVE nextMove = MOVE.NEUTRAL; 
	
	//Este método indica si existe un fantasma que vaya de frente a pacman, teniendo en cuenta paredes
	public boolean isGhostComingFromUp(Game game ,GHOST ghostType) {
		if(game.getNodeXCood(game.getPacmanCurrentNodeIndex()) == game.getNodeXCood(game.getGhostCurrentNodeIndex(ghostType)) &&
				game.getNodeYCood(game.getPacmanCurrentNodeIndex()) > game.getNodeYCood(game.getGhostCurrentNodeIndex(ghostType)) && 
				game.getManhattanDistance(game.getPacmanCurrentNodeIndex(),game.getGhostCurrentNodeIndex(ghostType)) == 
				game.getShortestPathDistance(game.getPacmanCurrentNodeIndex(),game.getGhostCurrentNodeIndex(ghostType), game.getPacmanLastMoveMade())) 
				return true;
		else return false;
	}
	
	public boolean isGhostComingFromDown(Game game ,GHOST ghostType) {
		if(game.getNodeXCood(game.getPacmanCurrentNodeIndex()) == game.getNodeXCood(game.getGhostCurrentNodeIndex(ghostType)) &&
				game.getNodeYCood(game.getPacmanCurrentNodeIndex()) < game.getNodeYCood(game.getGhostCurrentNodeIndex(ghostType)) && 
				game.getManhattanDistance(game.getPacmanCurrentNodeIndex(),game.getGhostCurrentNodeIndex(ghostType)) == 
				game.getShortestPathDistance(game.getPacmanCurrentNodeIndex(),game.getGhostCurrentNodeIndex(ghostType), game.getPacmanLastMoveMade())) 
				return true;
		else return false;
	}
	
	public boolean isGhostComingFromLeft(Game game ,GHOST ghostType) {
		if(game.getNodeXCood(game.getPacmanCurrentNodeIndex()) > game.getNodeXCood(game.getGhostCurrentNodeIndex(ghostType)) &&
				game.getNodeYCood(game.getPacmanCurrentNodeIndex()) == game.getNodeYCood(game.getGhostCurrentNodeIndex(ghostType)) && 
				game.getManhattanDistance(game.getPacmanCurrentNodeIndex(),game.getGhostCurrentNodeIndex(ghostType)) == 
				game.getShortestPathDistance(game.getPacmanCurrentNodeIndex(),game.getGhostCurrentNodeIndex(ghostType), game.getPacmanLastMoveMade())) 
				return true;
		else return false;
	}
	

	public boolean isGhostComingFromRight(Game game ,GHOST ghostType) {
		if(game.getNodeXCood(game.getPacmanCurrentNodeIndex()) < game.getNodeXCood(game.getGhostCurrentNodeIndex(ghostType)) &&
				game.getNodeYCood(game.getPacmanCurrentNodeIndex()) == game.getNodeYCood(game.getGhostCurrentNodeIndex(ghostType)) && 
				game.getManhattanDistance(game.getPacmanCurrentNodeIndex(),game.getGhostCurrentNodeIndex(ghostType)) == 
				game.getShortestPathDistance(game.getPacmanCurrentNodeIndex(),game.getGhostCurrentNodeIndex(ghostType), game.getPacmanLastMoveMade()))
				return true;
		else return false;
	}
	
	@Override
	public MOVE getMove(Game game, long timeDue) {
		
		/*
		/*Busca el fantasma mas cercano*/
		int i=0;
		for(GHOST ghostType : GHOST.values()) {
			double d = game.getDistance(game.getPacmanCurrentNodeIndex(), game.getGhostCurrentNodeIndex(ghostType), euristic);
			if(distance > d) {
				distance = d;
				ghost = ghostType;
			}
		}
		//Mira si hay fantasmas que vengan de frente		
		boolean ghostInFront=false;
		for(GHOST ghostType: GHOST.values()) {
			switch(game.getPacmanLastMoveMade()){
			case UP:
				if(game.getGhostLastMoveMade(ghostType)==MOVE.DOWN) {
					if(isGhostComingFromUp(game, ghostType)) {
					ghostInFront=true;
					frontGhost = ghostType;
					System.out.println("Fantasma Arriba");
					}
				}
					break;
			case DOWN:
				if(game.getGhostLastMoveMade(ghostType)==MOVE.UP) {
					if(isGhostComingFromDown(game,ghostType)) {
					ghostInFront=true;
					frontGhost = ghostType;
					System.out.println("Fantasma abajo");
					}
				}
				break;
			case LEFT:
				if(game.getGhostLastMoveMade(ghostType)==MOVE.RIGHT) {
					if(isGhostComingFromLeft(game,ghostType)) {
					ghostInFront=true;
					frontGhost = ghostType;
					System.out.println("Fantasma Izquierda");
					}
				}
				break;
			case RIGHT:
				if(game.getGhostLastMoveMade(ghostType)==MOVE.LEFT) {
					if(isGhostComingFromRight(game, ghostType)) {
					ghostInFront=true;
					frontGhost = ghostType;
					System.out.println("Fantasma Derecha");
					}
				}
				break;
			default:
					break;
			}
		}
		if(ghostInFront && !game.isGhostEdible(ghost)) {
			System.out.println("Cambiamos");
			return game.getNextMoveAwayFromTarget(game.getPacmanCurrentNodeIndex(), game.getGhostCurrentNodeIndex(frontGhost), euristic);	
		}

		//Si hay un fantasma muy cerca
		else if(!game.isGhostEdible(ghost) && distance <= limitGhost) {
				return game.getNextMoveTowardsTarget(game.getPacmanCurrentNodeIndex(), game.getGhostCurrentNodeIndex(ghost), euristic);
			}
			
		
		//Si es comestible
		else if(game.isGhostEdible(ghost)) {
			return game.getNextMoveTowardsTarget(game.getPacmanCurrentNodeIndex(), 
					game.getGhostCurrentNodeIndex(ghost), euristic);
		}
		
		else {
			//Prioriza ir a por una powerpill cercana y si no hay ninguna
			//va a por las pills mas cercanas
			
			double pillDist = Double.MAX_VALUE;
			int pillIndex = 0;
			for(int index : game.getActivePowerPillsIndices()) {
				double d = game.getDistance(game.getPacmanCurrentNodeIndex(), index, euristic);
				if(pillDist > d) {
					pillDist = d;
					pillIndex = index;
				}
			}
			if(pillDist <= limitPowerPill) {
				return game.getNextMoveTowardsTarget(game.getPacmanCurrentNodeIndex(), 
						pillIndex, euristic);
			}
			
			pillDist = Double.MAX_VALUE;
			for(int index : game.getActivePillsIndices()) {
				double d = game.getDistance(game.getPacmanCurrentNodeIndex(), index, euristic);
				if(pillDist > d) {
					pillDist = d;
					pillIndex = index;
				}
			}
			
			return game.getNextMoveTowardsTarget(game.getPacmanCurrentNodeIndex(), 
					pillIndex, euristic);
				
		}
	}
}
