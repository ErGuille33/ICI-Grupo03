package es.ucm.fdi.ici.c2021.practica5.grupo03.msPacMan.CBRengine;

import java.util.Vector;

import es.ucm.fdi.gaia.jcolibri.cbrcore.CBRCase;
import es.ucm.fdi.gaia.jcolibri.cbrcore.CBRCaseBase;
import es.ucm.fdi.gaia.jcolibri.method.retain.StoreCasesMethod;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Game;

public class MsPacManStorageManager {

	Game game;
	CBRCaseBase caseBase;
	Vector<CBRCase> buffer;

	private final static int TIME_WINDOW = 3;

	public MsPacManStorageManager() {
		this.buffer = new Vector<CBRCase>();
	}

	public void setGame(Game game) {
		this.game = game;
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
		MsPacManDescription description = (MsPacManDescription) bCase.getDescription();

		int lastLives = description.getLifes();
		int newLives = game.getPacmanNumberOfLivesRemaining();

		int lastPills = description.getPillsEated();
		int newPills = game.getNumberOfPills() - game.getNumberOfActivePills();

		int newGhostEat = game.getNumGhostsEaten();
		int lastGhostEat = description.getGhostEaten();

		int dead;

		if (lastLives > newLives)
			dead = 0;

		else
			dead = 1;

		double distancePowerPill = 300.0;
		double auxDist;
		if (game.getNumberOfActivePowerPills() > 0) {
			// calculasmos Distancia powerPill
			for (int i = 0; i < game.getNumberOfActivePowerPills(); i++) {

				auxDist = game.getDistance(game.getPacmanCurrentNodeIndex(), game.getPowerPillIndices()[i], DM.EUCLID);
				if (auxDist < distancePowerPill) {				
					distancePowerPill = auxDist;
				}
			}

		}
		
		double pesoPWPill = 0.15;
		double pesoPill = 0.35;
		double pesoGhosts = 0.4 / 4;
		double pesoEatGhost = 0.1;
		if(game.getNumberOfActivePowerPills() == 0) {
			pesoPill += pesoPWPill;
			pesoPWPill = 0;
		}
		int maxT = 0;
		int auxT;
		for(GHOST g : GHOST.values()) {
			auxT = game.getGhostEdibleTime(g);
			if(maxT < auxT)
				maxT = auxT;
		}
		if(maxT > 20) {
			pesoPill += pesoPWPill/2.0;
			pesoEatGhost += pesoPWPill/2.0;
			pesoPWPill = 0.0;
		}
			

		MsPacManResult result = (MsPacManResult) bCase.getResult();

		double resultValue = dead * ((((newPills - lastPills) / 10) * pesoPill) + ((newGhostEat - lastGhostEat) / 4) * pesoEatGhost + Math
				.abs(((game.getGhostEdibleTime(GHOST.BLINKY) / 100) - game.getDistance(game.getPacmanCurrentNodeIndex(),
						game.getGhostCurrentNodeIndex(GHOST.BLINKY), DM.EUCLID) / 300))
				* pesoGhosts + Math
				.abs(((game.getGhostEdibleTime(GHOST.INKY) / 100) - game.getDistance(game.getPacmanCurrentNodeIndex(),
						game.getGhostCurrentNodeIndex(GHOST.INKY), DM.EUCLID) / 300))
				* pesoGhosts+ Math
				.abs(((game.getGhostEdibleTime(GHOST.PINKY) / 100) - game.getDistance(game.getPacmanCurrentNodeIndex(),
						game.getGhostCurrentNodeIndex(GHOST.PINKY), DM.EUCLID) / 300))
				* pesoGhosts+ Math
				.abs(((game.getGhostEdibleTime(GHOST.SUE) / 100) - game.getDistance(game.getPacmanCurrentNodeIndex(),
						game.getGhostCurrentNodeIndex(GHOST.SUE), DM.EUCLID) / 300))
				* pesoGhosts - (distancePowerPill / 150) * pesoPWPill);

		result.setScore(resultValue);
		// Store the old case right now into the case base
		// Alternatively we could store all them when game finishes in close() method
		if(resultValue > 0.5)
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
