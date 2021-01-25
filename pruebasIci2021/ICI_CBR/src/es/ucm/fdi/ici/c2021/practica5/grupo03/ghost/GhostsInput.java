package es.ucm.fdi.ici.c2021.practica5.grupo03.ghost;


import es.ucm.fdi.gaia.jcolibri.cbrcore.CBRQuery;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import es.ucm.fdi.gaia.jcolibri.cbrcore.CBRQuery;
import es.ucm.fdi.ici.c2021.practica5.grupo03.ghost.CBRengine.GhostsDescription;
import es.ucm.fdi.ici.c2021.practica5.grupo03.msPacMan.CBRengine.MsPacManDescription;
import pacman.game.Game;

public class GhostsInput implements Input {

	Double distanceToPacManB;
	Double distanceToPacManP;
	Double distanceToPacManI;
	Double distanceToPacManS;
	
	Double distancePacManToPowerPill;
	
	Integer eatableTime;
	
	Integer indexP;
	Integer indexB;
	Integer indexI;
	Integer indexS;
	
	Integer indexPacMan;
	
	Integer nLevel;
	Integer lastDir;
	
	//Atributos para el resultado
	Integer lifes;
	Integer activePowerPills;
	
	GHOST ghost;
	
	@Override
	public void parseInput(Game game) {

		computeDistancePacManToPowerPill(game);
		
		distanceToPacManP = game.getDistance(game.getGhostCurrentNodeIndex(GHOST.PINKY), game.getPacmanCurrentNodeIndex(), DM.PATH);
		distanceToPacManI = game.getDistance(game.getGhostCurrentNodeIndex(GHOST.INKY), game.getPacmanCurrentNodeIndex(), DM.PATH);
		distanceToPacManB = game.getDistance(game.getGhostCurrentNodeIndex(GHOST.BLINKY), game.getPacmanCurrentNodeIndex(), DM.PATH);
		distanceToPacManS = game.getDistance(game.getGhostCurrentNodeIndex(GHOST.SUE), game.getPacmanCurrentNodeIndex(), DM.PATH);
		
		eatableTime = game.getGhostEdibleTime(ghost);
		
		indexI = game.getGhostCurrentNodeIndex(GHOST.INKY);
		indexP = game.getGhostCurrentNodeIndex(GHOST.PINKY);
		indexS = game.getGhostCurrentNodeIndex(GHOST.SUE);
		indexB = game.getGhostCurrentNodeIndex(GHOST.BLINKY);
		
		indexPacMan = game.getPacmanCurrentNodeIndex();
		
		activePowerPills = game.getNumberOfActivePills();
		lifes = game.getPacmanNumberOfLivesRemaining();
		
		nLevel = game.getCurrentLevel();
		lastDir = game.getPacmanLastMoveMade().ordinal();
	}

	public void setGhost(GHOST ghost) {
		this.ghost = ghost;
	}
	
	public void computeDistancePacManToPowerPill(Game game) {
		int[] activePills = game.getActivePowerPillsIndices();
		double auxDistance = 0;
		distancePacManToPowerPill= 0.0;
		for(int i = 0; i < activePills.length; i++) {
			auxDistance = game.getDistance(game.getPacmanCurrentNodeIndex(), activePills[i], DM.PATH);
			if(auxDistance > distancePacManToPowerPill) {
				distancePacManToPowerPill = auxDistance;
			}
		}
	}
	
	@Override
	public CBRQuery getQuery() {
		GhostsDescription description = new GhostsDescription();
		
		description.setDistanceToPacManS(distanceToPacManS);
		description.setDistanceToPacManP(distanceToPacManP);
		description.setDistanceToPacManI(distanceToPacManI);
		description.setDistanceToPacManB(distanceToPacManB);
		
		description.setIndexPacMan(indexPacMan);
		
		description.setDistancePacManToPowerPill(distancePacManToPowerPill);
		
		description.setEatableTime(eatableTime);
		
		description.setIndexB(indexB);
		description.setIndexP(indexP);
		description.setIndexI(indexI);
		description.setIndexS(indexS);

		
		description.setLastDir(lastDir);
		description.setNLevel(nLevel);
		description.setLifes(lifes);
		description.setActivePowerPills(activePowerPills);
				
		CBRQuery query = new CBRQuery();
		query.setDescription(description);
		return query;
	}
	
	
}
