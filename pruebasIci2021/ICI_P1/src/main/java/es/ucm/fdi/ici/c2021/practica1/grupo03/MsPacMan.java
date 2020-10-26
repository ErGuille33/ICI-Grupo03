package es.ucm.fdi.ici.c2021.practica1.grupo03;

import pacman.game.Game;


import java.util.ArrayList;
import java.util.AbstractCollection;
import java.util.PriorityQueue;

import pacman.controllers.PacmanController;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;

public final class MsPacMan extends PacmanController{
	int limitGhost = 15;
	int limitPowerPill = 30;
	double focusRadius = 3000;
	double maxPathDistance = 50;
	double eatDistance=40;
	GHOST ghost = GHOST.BLINKY;
	GHOST frontGhost = GHOST.BLINKY;
	DM euristic = DM.PATH;
	MOVE nextMove = MOVE.NEUTRAL; 
	int ghostsIndex[] = new int[4];
	MOVE ghostsMoves[] = new MOVE[4];
	
	
	public int getClosestPossiblePill(Game game, ArrayList<MOVE> moves, int pacManNode) {
		double pillDist=Double.MAX_VALUE;
		int closestPill=-1;
		for(int pill: game.getActivePillsIndices()) {
			double dist=game.getDistance(pacManNode, pill, euristic);
			
			if(moves.contains(game.getNextMoveTowardsTarget(pacManNode, pill, euristic)) && 
					game.getDistance(pacManNode, pill, euristic)<pillDist) {
				pillDist=dist;
				closestPill=pill;
			}
		}
		return closestPill;
	}
	
	@Override
	public MOVE getMove(Game game, long timeDue) {
		
		int pacManNode = game.getPacmanCurrentNodeIndex(); 
		
		MOVE[] moves = game.getPossibleMoves(pacManNode, game.getPacmanLastMoveMade());
		
		if(game.isJunction(pacManNode))
			System.out.println("");
		
		ArrayList<MOVE>possibleMoves=new ArrayList<MOVE>();
		
		for(MOVE m: moves) {
			possibleMoves.add(m);
		}
		
		ArrayList<GHOST> inRangeToEat= new ArrayList<GHOST>();
		
		for(GHOST ghostType : GHOST.values()) {
				
			int ghostNode = game.getGhostCurrentNodeIndex(ghostType);
				
			double d = game.getDistance(game.getPacmanCurrentNodeIndex(), game.getGhostCurrentNodeIndex(ghostType), euristic);
				
			if(!game.isGhostEdible(ghostType) && d < focusRadius) {
				if(game.getShortestPathDistance(game.getPacmanCurrentNodeIndex(), game.getGhostCurrentNodeIndex(ghostType))
						<= maxPathDistance){
					if(game.getGhostLastMoveMade(ghostType) == game.getNextMoveTowardsTarget(ghostNode, pacManNode, euristic)) {
						possibleMoves.remove(game.getNextMoveTowardsTarget(pacManNode, ghostNode, euristic));
					}
				}
			}
			else if(game.isGhostEdible(ghostType) && d <= eatDistance) {
				inRangeToEat.add(ghostType);
			}
		}
		
		if(game.isJunction(pacManNode))
			System.out.println(possibleMoves);
		
		int pill = getClosestPossiblePill(game, possibleMoves, pacManNode);
		
		boolean eatGhost=false;
		
		MOVE moveToGhost=MOVE.NEUTRAL;
		
		for(GHOST g: inRangeToEat) {
			moveToGhost=game.getNextMoveTowardsTarget(pacManNode, game.getGhostCurrentNodeIndex(g), euristic);
			if(possibleMoves.contains(moveToGhost)) {
				eatGhost=true;
				break;
			}
		}
		
		if(possibleMoves.isEmpty())
			nextMove=MOVE.NEUTRAL;
		
		else if(eatGhost) 
			nextMove=moveToGhost;
		
		else if(pill !=-1)
			nextMove=game.getNextMoveTowardsTarget(pacManNode, pill, euristic);
		
		else {
			int siz = possibleMoves.size();
				
			int mv = (int)Math.floor(Math.random()*siz);
				
			nextMove=possibleMoves.get(mv);
		}
		
		return nextMove;
	}
}
