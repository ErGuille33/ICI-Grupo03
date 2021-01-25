package es.ucm.fdi.ici.c2021.practica5.grupo03.msPacMan;


import es.ucm.fdi.gaia.jcolibri.cbrcore.CBRQuery;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import es.ucm.fdi.gaia.jcolibri.cbrcore.CBRQuery;
import es.ucm.fdi.ici.c2021.practica5.grupo03.msPacMan.CBRengine.MsPacManDescription;
import pacman.game.Game;

public class MsPacManInput implements Input {

	Integer nearestGhost;
	
	Double distanceBlinky;
	Double distanceInky;
	Double distancePinky;
	Double distanceSue;
	
	Double distanceInterUp = 0.0;
	Double distanceInterDown = 0.0; 
	Double distanceInterLeft = 0.0;
	Double distanceInterRight= 0.0;
	
	Integer timeEdibleInky;
	Integer timeEdiblePinky;
	Integer timeEdibleSue;
	Integer timeEdibleBlinky;
	
	Integer chasedByInky = 0;
	Integer chasedByPinky= 0;
	Integer chasedBySue= 0;
	Integer chasedByBlinky= 0;
	
	Integer numPillsUp;
	Integer numPillsDown; //Hasta la siguiente intersección
	Integer numPillsLeft;
	Integer numPillsRight;
	
	Integer nLevel;
	Integer lastDir;
	
	Integer indexI;  //Indice del nodo de posicion de cada fantasma
	Integer indexS;
	Integer indexB;
	Integer indexP;
	
	Integer pacManIndex;
	Integer pillsEated;
	Integer ghostEaten;
	Integer lifes;

	

	
	@Override
	public void parseInput(Game game) {
		computeDistanceGhost(game);
		computeDistanceInter(game);
		computeTimeEdibleGhost(game);
		computeChasedByGhost(game);
		
		indexI = game.getGhostCurrentNodeIndex(GHOST.INKY);
		indexP = game.getGhostCurrentNodeIndex(GHOST.PINKY);
		indexS = game.getGhostCurrentNodeIndex(GHOST.SUE);
		indexB = game.getGhostCurrentNodeIndex(GHOST.BLINKY);
		
		pacManIndex = game.getPacmanCurrentNodeIndex();
		ghostEaten = game.getNumGhostsEaten();
		pillsEated = game.getNumberOfPills() - game.getNumberOfActivePills();
		lifes = game.getPacmanNumberOfLivesRemaining();
		
		nLevel = game.getCurrentLevel();
		lastDir = game.getPacmanLastMoveMade().ordinal();
	}

	@Override
	public CBRQuery getQuery() {
		MsPacManDescription description = new MsPacManDescription();
		description.setDistanceBlinky(distanceBlinky);
		description.setDistanceInky(distanceInky);
		description.setDistancePinky(distancePinky);
		description.setDistanceSue(distanceSue);
		
		description.setDistanceInterUp(distanceInterUp);
		description.setDistanceInterDown(distanceInterDown);
		description.setDistanceInterLeft(distanceInterLeft);
		description.setDistanceInterRight(distanceInterRight);
		
		description.setChasedByBlinky(chasedByBlinky);
		description.setChasedByInky(chasedByInky);
		description.setChasedByPinky(chasedByPinky);
		description.setChasedBySue(chasedBySue);
		
		description.setTimeEdibleBlinky(timeEdibleBlinky);
		description.setTimeEdibleInky(timeEdibleInky);
		description.setTimeEdiblePinky(timeEdiblePinky);
		description.setTimeEdibleSue(timeEdibleSue);
		
		description.setNumPillsUp(numPillsUp);
		description.setNumPillsDown(numPillsDown);
		description.setNumPillsLeft(numPillsLeft);
		description.setNumPillsRight(numPillsRight);
		
		description.setIndexB(indexB);
		description.setIndexP(indexP);
		description.setIndexI(indexI);
		description.setIndexS(indexS);
		
		description.setPacManIndex(pacManIndex);
		description.setPillsEated(pillsEated);
		
		description.setLastDir(lastDir);
		description.setGhostEaten(ghostEaten);
		description.setNLevel(nLevel);
		
		description.setLifes(lifes);
				
		CBRQuery query = new CBRQuery();
		query.setDescription(description);
		return query;
	}
	
	private void computeDistanceGhost(Game game) {
		distanceBlinky = game.getDistance(game.getPacmanCurrentNodeIndex(), game.getGhostCurrentNodeIndex(GHOST.BLINKY), DM.PATH);
		distancePinky = game.getDistance(game.getPacmanCurrentNodeIndex(), game.getGhostCurrentNodeIndex(GHOST.PINKY), DM.PATH);
		distanceInky = game.getDistance(game.getPacmanCurrentNodeIndex(), game.getGhostCurrentNodeIndex(GHOST.INKY), DM.PATH);
		distanceSue = game.getDistance(game.getPacmanCurrentNodeIndex(), game.getGhostCurrentNodeIndex(GHOST.SUE), DM.PATH);
	}
	
	private void computeTimeEdibleGhost(Game game) {
		 timeEdibleInky = game.getGhostEdibleTime(GHOST.INKY);
		 timeEdiblePinky = game.getGhostEdibleTime(GHOST.PINKY);
		 timeEdibleSue = game.getGhostEdibleTime(GHOST.SUE);
		 timeEdibleBlinky = game.getGhostEdibleTime(GHOST.BLINKY);
	}
	
	private void computeChasedByGhost(Game game) {
		MOVE moveGhost = MOVE.NEUTRAL;
		moveGhost = game.getGhostLastMoveMade(GHOST.BLINKY);
		chasedByBlinky = (moveGhost == game.getNextMoveTowardsTarget(game.getGhostCurrentNodeIndex(GHOST.BLINKY), game.getPacmanCurrentNodeIndex(), DM.PATH))?1:0;
		
		moveGhost = game.getGhostLastMoveMade(GHOST.INKY);
		chasedByInky = (moveGhost == game.getNextMoveTowardsTarget(game.getGhostCurrentNodeIndex(GHOST.INKY), game.getPacmanCurrentNodeIndex(), DM.PATH))?1:0;
		
		moveGhost = game.getGhostLastMoveMade(GHOST.PINKY);
		chasedByPinky = (moveGhost == game.getNextMoveTowardsTarget(game.getGhostCurrentNodeIndex(GHOST.PINKY), game.getPacmanCurrentNodeIndex(), DM.PATH))?1:0;

		moveGhost = game.getGhostLastMoveMade(GHOST.SUE);
		chasedBySue = (moveGhost == game.getNextMoveTowardsTarget(game.getGhostCurrentNodeIndex(GHOST.SUE), game.getPacmanCurrentNodeIndex(), DM.PATH))?1:0;

	}
	
	private void computeDistanceInter(Game game) {
		int count = 0;
		int pacManIndex = game.getPacmanCurrentNodeIndex();
		int nodeIndex = game.getPacmanCurrentNodeIndex();
		int auxIndex;
		int numPills = 0;
		
		MOVE m = MOVE.UP;
		if (game.getNeighbouringNodes(nodeIndex, MOVE.UP)!=null)
			nodeIndex = game.getNeighbouringNodes(nodeIndex, MOVE.UP)[0];	
		boolean fuera = false;
		while(!game.isJunction(nodeIndex) && !fuera) {
			if((game.getNeighbouringNodes(nodeIndex, m)) != null) {
                auxIndex = (game.getNeighbouringNodes(nodeIndex, m))[0];                
                m = game.getNextMoveTowardsTarget(nodeIndex, auxIndex, m, DM.PATH);
                nodeIndex = auxIndex;
                if(game.getPillIndex(auxIndex) != -1) {
                	numPills++;
                }
            }
            else
                fuera = true;
        }
		distanceInterUp =	game.getDistance(nodeIndex, pacManIndex, DM.PATH);
		numPillsUp = numPills;
		
		numPills = 0;
		m = MOVE.DOWN;
		if (game.getNeighbouringNodes(nodeIndex, MOVE.DOWN)!=null)
			nodeIndex = game.getNeighbouringNodes(nodeIndex, MOVE.DOWN)[0];	
		fuera = false;
		while(!game.isJunction(nodeIndex) && !fuera) {
			if((game.getNeighbouringNodes(nodeIndex, m)) != null) {
                auxIndex = (game.getNeighbouringNodes(nodeIndex, m))[0];                
                m = game.getNextMoveTowardsTarget(nodeIndex, auxIndex, m, DM.PATH);
                nodeIndex = auxIndex;
                if(game.getPillIndex(auxIndex) != -1) {
                	numPills++;
                }
            }
            else
                fuera = true;
        }
		distanceInterDown =	game.getDistance(nodeIndex, pacManIndex, DM.PATH);
		numPillsDown = numPills;
		
		numPills = 0;
		m = MOVE.LEFT;
		if (game.getNeighbouringNodes(nodeIndex, MOVE.LEFT)!=null)
			nodeIndex = game.getNeighbouringNodes(nodeIndex, MOVE.LEFT)[0];	
		fuera = false;
		while(!game.isJunction(nodeIndex) && !fuera) {
			if((game.getNeighbouringNodes(nodeIndex, m)) != null) {
                auxIndex = (game.getNeighbouringNodes(nodeIndex, m))[0];                
                m = game.getNextMoveTowardsTarget(nodeIndex, auxIndex, m, DM.PATH);
                nodeIndex = auxIndex;
                if(game.getPillIndex(auxIndex) != -1) {
                	numPills++;
                }
            }
            else
                fuera = true;
        }
		distanceInterLeft =	game.getDistance(nodeIndex, pacManIndex, DM.PATH);
		numPillsLeft = numPills;
		
		numPills = 0;
		m = MOVE.RIGHT;
		if (game.getNeighbouringNodes(nodeIndex, MOVE.RIGHT)!=null)
			nodeIndex = game.getNeighbouringNodes(nodeIndex, MOVE.RIGHT)[0];	
		fuera = false;
		while(!game.isJunction(nodeIndex) && !fuera) {
			if((game.getNeighbouringNodes(nodeIndex, m)) != null) {
                auxIndex = (game.getNeighbouringNodes(nodeIndex, m))[0];                
                m = game.getNextMoveTowardsTarget(nodeIndex, auxIndex, m, DM.PATH);
                nodeIndex = auxIndex;
                if(game.getPillIndex(auxIndex) != -1) {
                	numPills++;
                }
            }
            else
                fuera = true;
        }
		distanceInterRight = game.getDistance(nodeIndex, pacManIndex, DM.PATH);
		numPillsRight = numPills;
		
		
	
	}
	
}
