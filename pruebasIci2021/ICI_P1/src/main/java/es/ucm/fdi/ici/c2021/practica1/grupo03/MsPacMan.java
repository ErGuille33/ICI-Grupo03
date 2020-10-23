package es.ucm.fdi.ici.c2021.practica1.grupo03;

import pacman.game.Game;

import java.util.ArrayList;

import pacman.controllers.PacmanController;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;

public final class MsPacMan extends PacmanController{
	int limitGhost = 15;
	int limitPowerPill = 30;
	double distance = Double.MAX_VALUE;
	GHOST ghost = GHOST.BLINKY;
	GHOST frontGhost = GHOST.BLINKY;
	DM euristic = DM.EUCLID;
	MOVE nextMove = MOVE.NEUTRAL; 
	int ghostsIndex[] = new int[4];
	MOVE ghostsMoves[] = new MOVE[4];
	
	//devuelve la siguiente intersección que se encuentra un agente que está en currentNode en dirección direction
	public int[] nextJunction(Game game, int currentNode, MOVE direction) {
		int[]junctions=new int [10];
		for(int i=0; i<junctions.length; i++) {
			junctions[i]=-1;
		}
		int x=0;
		for(int i=0; i<game.getJunctionIndices().length; i++) {
			if(direction == MOVE.RIGHT) {
				if(game.getNodeYCood(currentNode)==game.getNodeYCood(game.getJunctionIndices()[i])) {
					if(game.getNodeXCood(currentNode)<game.getNodeXCood(game.getJunctionIndices()[i])) {
							junctions[x]=game.getJunctionIndices()[i];
							x++;
						}
					}
				}
			else if(direction == MOVE.LEFT) {
				if(game.getNodeYCood(currentNode)==game.getNodeYCood(game.getJunctionIndices()[i])) {
					if(game.getNodeXCood(currentNode)>game.getNodeXCood(game.getJunctionIndices()[i])) {
							junctions[x]=game.getJunctionIndices()[i];
							x++;
						}
					}
			}
			else if(direction == MOVE.UP) {
				if(game.getNodeXCood(currentNode)==game.getNodeXCood(game.getJunctionIndices()[i])) {
					if(game.getNodeYCood(currentNode)>game.getNodeYCood(game.getJunctionIndices()[i])) {
							junctions[x]=game.getJunctionIndices()[i];
							x++;
						}
					}
			}
			else {
				if(game.getNodeXCood(currentNode)==game.getNodeXCood(game.getJunctionIndices()[i])) {
					if(game.getNodeYCood(currentNode)<game.getNodeYCood(game.getJunctionIndices()[i])) {
							junctions[x]=game.getJunctionIndices()[i];
							x++;
						}
					}
			}
		}
		return junctions;
	}
	public int getClosestJunction(Game game, int[]junctions, int node) {
		int distance=-1;
		int junc=-1;
		for(int i=0; i<junctions.length; i++) {
			if(junctions[i]!=-1) {
				double dist=game.getManhattanDistance(node, junctions[i]);
				if(distance==-1 || dist<distance) {
					junc=junctions[i];
				}
			}
		}
		return junc;
	}
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
		boolean ghostInFront=false;
		distance = Double.MAX_VALUE;
		frontGhost = null;
		/*Bucle donde recorre los fantasmas, y recoge información sobre ellos*/
		for(GHOST ghostType : GHOST.values()) {
			/*COge el fantasma mas cercano*/
			double d = game.getDistance(game.getPacmanCurrentNodeIndex(), game.getGhostCurrentNodeIndex(ghostType), euristic);
			if(distance > d) {
				distance = d;
				ghost = ghostType;
			}
			/*Comprueba si existe un fantasma en su misma calle que se dirija hacia ti */
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
				ghostInFront=false;
				frontGhost = null;
				break;
			}
			/*Recoge las posiciones y direcciones de fantasmas*/
			ghostsIndex[i] = game.getGhostCurrentNodeIndex(ghostType);
			ghostsMoves[i] = game.getGhostLastMoveMade(ghostType);
			i++;
		}
		boolean ghostOnJunction=false;
		
		ArrayList<GHOST> ghostJunction=new ArrayList<GHOST>();
		
		int junctionsPacman[]=nextJunction(game, game.getPacmanCurrentNodeIndex(), game.getPacmanLastMoveMade());
		
		for(GHOST ghostType : GHOST.values()) {
			if(!game.isGhostEdible(ghostType)) {
				int junction=getClosestJunction(game, nextJunction(game, game.getGhostCurrentNodeIndex(ghostType), game.getGhostLastMoveMade(ghostType)), game.getGhostCurrentNodeIndex(ghostType));
				
				for(int s=0; s<junctionsPacman.length; s++) {
					if(junctionsPacman[s]==junction && junction !=-1) {
						ghostOnJunction=true;
						ghostJunction.add(ghostType);
					}
				}
			}
		}
		//Mira si hay fantasmas que vengan de frente. Si los hay, huye.		
		if(ghostInFront && !game.isGhostEdible(frontGhost)) {
			if(game.isJunction(game.getPacmanCurrentNodeIndex())) {
				System.out.println("Cambiamos");
				if(game.getPossibleMoves(game.getPacmanCurrentNodeIndex(), game.getPacmanLastMoveMade())[0] == game.getPacmanLastMoveMade())
				nextMove = game.getPossibleMoves(game.getPacmanCurrentNodeIndex(), game.getPacmanLastMoveMade())[1];
				else 
					nextMove = game.getPossibleMoves(game.getPacmanCurrentNodeIndex(), game.getPacmanLastMoveMade())[0];
			} else {
			System.out.println("No Cambiamos");
			nextMove = game.getNextMoveAwayFromTarget(game.getPacmanCurrentNodeIndex(), game.getGhostCurrentNodeIndex(frontGhost), euristic);	
			}
		}
	
		//Si hay un fantasma muy cerca
		else if(!game.isGhostEdible(ghost) && distance <= limitGhost) {
			System.out.println("Huimos mi gente");
			
			ArrayList<MOVE>possibleMoves=new ArrayList<MOVE>();
			possibleMoves.add(MOVE.UP);
			possibleMoves.add(MOVE.DOWN);
			possibleMoves.add(MOVE.RIGHT);
			possibleMoves.add(MOVE.LEFT);
			
			 if(ghostOnJunction) {
					for(GHOST g: ghostJunction) {
						switch(game.getGhostLastMoveMade(g)) {
						case UP:
							possibleMoves.remove(MOVE.DOWN);
							break;
						case DOWN:
							possibleMoves.remove(MOVE.UP);
							break;
						case LEFT:
							possibleMoves.remove(MOVE.RIGHT);
							break;
						case RIGHT:
							possibleMoves.remove(MOVE.LEFT);
							break;
						default:
							break;
						}
					}
			 }
			
			switch(game.getGhostLastMoveMade(ghost)) {
			case UP:
				possibleMoves.remove(MOVE.DOWN);
				break;
			case DOWN:
				possibleMoves.remove(MOVE.UP);
				break;
			case LEFT:
				possibleMoves.remove(MOVE.RIGHT);
				break;
			case RIGHT:
				possibleMoves.remove(MOVE.LEFT);
				break;
			default:
				break;
			}
			
			for(int p: game.getActivePillsIndices()) {
				MOVE m=game.getNextMoveTowardsTarget(game.getPacmanCurrentNodeIndex(), 
						p, euristic);
				if(possibleMoves.contains(m)) {
					nextMove=m;
					System.out.println("pastishuicion");
					break;
				}
			}
			
		}
		
		else if(ghostOnJunction) {
			
			ArrayList<MOVE>possibleMoves=new ArrayList<MOVE>();
			possibleMoves.add(MOVE.UP);
			possibleMoves.add(MOVE.DOWN);
			possibleMoves.add(MOVE.RIGHT);
			possibleMoves.add(MOVE.LEFT);
			for(GHOST g: ghostJunction) {
				switch(game.getGhostLastMoveMade(g)) {
				case UP:
					possibleMoves.remove(MOVE.DOWN);
					break;
				case DOWN:
					possibleMoves.remove(MOVE.UP);
					break;
				case LEFT:
					possibleMoves.remove(MOVE.RIGHT);
					break;
				case RIGHT:
					possibleMoves.remove(MOVE.LEFT);
					break;
				default:
					break;
				}
			}
			nextMove=MOVE.NEUTRAL;
			for(MOVE m :game.getPossibleMoves(game.getPacmanCurrentNodeIndex())){
				if(possibleMoves.contains(m))
					nextMove=m;
			}
		}
		
		//Si es comestible
		else if(game.isGhostEdible(ghost) && distance <= limitGhost) {
			System.out.println("Perseguimos");
			nextMove = game.getNextMoveTowardsTarget(game.getPacmanCurrentNodeIndex(), 
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
				System.out.println("MegaPAstis");
				nextMove = game.getNextMoveTowardsTarget(game.getPacmanCurrentNodeIndex(), 
						pillIndex, euristic);
			}
			else {
			pillDist = Double.MAX_VALUE;
			for(int index : game.getActivePillsIndices()) {
				double d = game.getDistance(game.getPacmanCurrentNodeIndex(), index, euristic);
				if(pillDist > d) {
					pillDist = d;
					pillIndex = index;
				}
			}
			System.out.println("PAstis");
			nextMove = game.getNextMoveTowardsTarget(game.getPacmanCurrentNodeIndex(), 
					pillIndex, euristic);
			}
				
		}
		return nextMove;
	}
}
