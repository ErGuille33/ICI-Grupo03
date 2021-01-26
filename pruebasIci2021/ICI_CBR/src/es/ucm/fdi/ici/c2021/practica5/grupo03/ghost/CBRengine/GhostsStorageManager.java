package es.ucm.fdi.ici.c2021.practica5.grupo03.ghost.CBRengine;

import java.util.Vector;

import es.ucm.fdi.gaia.jcolibri.cbrcore.CBRCase;
import es.ucm.fdi.gaia.jcolibri.cbrcore.CBRCaseBase;
import es.ucm.fdi.gaia.jcolibri.method.retain.StoreCasesMethod;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Game;

public class GhostsStorageManager {

	Game game;
	GHOST ghost;
	CBRCaseBase caseBase;
	Vector<CBRCase> buffer;

	private final static int TIME_WINDOW = 3;

	public GhostsStorageManager() {
		this.buffer = new Vector<CBRCase>();
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public void setGhost(GHOST ghost) {
		this.ghost = ghost;
	}

	public void setCaseBase(CBRCaseBase caseBase) {
		this.caseBase = caseBase;
	}

	public void storeCase(CBRCase newCase) {
		this.buffer.add(newCase);

		// Check buffer for old cases to store
		if (this.buffer.size() > TIME_WINDOW) {
			CBRCase bCase = this.buffer.remove(0);
			reviseCase(bCase);
		}
	}

	private void reviseCase(CBRCase bCase) {
		GhostsDescription description = (GhostsDescription) bCase.getDescription();
		DM euristic = DM.PATH;
		// SI pacman esta muerto
		int lastLives = description.getLifes();
		int newLives = game.getPacmanNumberOfLivesRemaining();
		int dead = (lastLives > newLives) ? 1 : 0;

		// DIstancia a power pill más cercana
		int[] activePowerPills = game.getActivePowerPillsIndices();
		double distancePowerPill = 300;
		double auxDistance = 0;
		for (int i = 0; i < activePowerPills.length; i++) {
			auxDistance = game.getDistance(game.getPacmanCurrentNodeIndex(), activePowerPills[i], euristic);
			if (auxDistance < distancePowerPill) {
				distancePowerPill = auxDistance;
			}
		}
		// Si pacman se comio una power pilll desde la ultima intersección
		int lastNumPowerPills = description.getActivePowerPills();
		int newNumPowerPills = game.getNumberOfActivePills();
		int comioPowerPill = (newNumPowerPills < lastNumPowerPills) ? 0 : 1;

		// TiempoComestible de cada fantasma
		double timeEdibleP = game.getGhostEdibleTime(GHOST.PINKY);
		double timeEdibleI = game.getGhostEdibleTime(GHOST.INKY);
		double timeEdibleB = game.getGhostEdibleTime(GHOST.BLINKY);
		double timeEdibleS = game.getGhostEdibleTime(GHOST.SUE);

		GhostsResult result = (GhostsResult) bCase.getResult();
		//EL peso cambiará dependiendo de qué fantasma sea
		double weightP=  .1;
		double weightB = .1;
		double weightI= .1;
		double weightS= .1;
		int eaten = game.wasGhostEaten(ghost)?0:1;
		
		switch (ghost) {
		case INKY:
			weightI = .5;
			break;
		case BLINKY:
			weightB = .5;
			break;
		case SUE:
			weightS = .5;
			break;
		case PINKY:
			weightP = .5;
			break;
		default:
			break;
		}
		
		

		double resultValue = Math.max(dead,
				((((distancePowerPill / 300) * .1) + (comioPowerPill * 0.1)
						+ Math.abs((((1 - (timeEdibleP / 200)) - game.getDistance(game.getPacmanCurrentNodeIndex(),
								game.getGhostCurrentNodeIndex(GHOST.PINKY), euristic) / 300)) * weightP)
						+ Math.abs((((1 - (timeEdibleI / 200)) - game.getDistance(game.getPacmanCurrentNodeIndex(),
								game.getGhostCurrentNodeIndex(GHOST.INKY), euristic) / 300)) * weightI)
						+ Math.abs((((1 - (timeEdibleB / 200)) - game.getDistance(game.getPacmanCurrentNodeIndex(),
								game.getGhostCurrentNodeIndex(GHOST.BLINKY), euristic) / 300)) * weightB)
						+ Math.abs((((1 - (timeEdibleS / 200)) - game.getDistance(game.getPacmanCurrentNodeIndex(),
								game.getGhostCurrentNodeIndex(GHOST.SUE), euristic) / 300)) * weightS))))*eaten;

		result.setScore(resultValue);
		// Store the old case right now into the case base
		// Alternatively we could store all them when game finishes in close() method
		if(resultValue > 0.7)
			StoreCasesMethod.storeCase(this.caseBase, bCase);

	}

	public void close() {
		for (CBRCase oldCase : this.buffer)
			reviseCase(oldCase);
		this.buffer.removeAllElements();
	}

	public int getPendingCases() {
		return this.buffer.size();
	}
}
