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

		int distancePowerPill = 0;
		double auxDist;
		if (game.getNumberOfActivePowerPills() > 0) {
			// calculasmos Distancia powerPill
			for (int i = 0; i < game.getNumberOfActivePowerPills(); i++) {

				auxDist = game.getDistance(game.getPacmanCurrentNodeIndex(), game.getPowerPillIndices()[i], DM.PATH);
				if (auxDist > distancePowerPill) {
					auxDist = distancePowerPill;
				}
			}

		}

		MsPacManResult result = (MsPacManResult) bCase.getResult();

		double resultValue = dead * (((newPills - lastPills) / 50 * .2) + (newGhostEat - lastGhostEat) / 4 * .3
				+ (description.getTimeEdibleBlinky() / 200
						- description.getDistanceBlinky() / 300) * .1
				+ (description.getTimeEdibleInky() / 200
						- description.getDistanceInky() / 300) * .1
				+ (description.getTimeEdiblePinky() / 200
						- description.getDistancePinky() / 300) * .1
				+ (description.getTimeEdibleSue() / 200
						- description.getDistanceSue() / 300) * .1
				+ 1 - (distancePowerPill / 300 )* 0.1);

		result.setScore(resultValue);
		// Store the old case right now into the case base
		// Alternatively we could store all them when game finishes in close() method
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
