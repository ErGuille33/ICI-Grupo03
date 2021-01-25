package es.ucm.fdi.ici.c2021.practica5.grupo03.msPacMan.CBRengine;

import es.ucm.fdi.gaia.jcolibri.cbrcore.Attribute;
import es.ucm.fdi.gaia.jcolibri.cbrcore.CaseComponent;

public class MsPacManDescription implements CaseComponent {

	Integer id;
	
	double distanceInky;
	double distancePinky;
	double distanceSue;
	double distanceBlinky;
	
	double timeEdibleInky;
	double timeEdiblePinky;
	double timeEdibleSue;
	double timeEdibleBlinky;
	
	int chasedByInky;
	int chasedByPinky;
	int chasedBySue;
	int chasedByBlinky;
	
	double distanceInterUp;
	double distanceInterDown; 
	double distanceInterLeft;
	double distanceInterRight;
	
	int numPillsUp;
	int numPillsDown; //Hasta la siguiente intersección
	int numPillsLeft;
	int numPillsRight;
	
	int indexP;
	int indexI;  //Indice del nodo de posicion de cada fantasma
	int indexS;
	int indexB;
	
	int pacManIndex;

	int lifes;
	int pillsEated;
	
	int lastDir;
	int ghostEaten;
	int nLevel;
	
	
	public int getGhostEaten() {
		return ghostEaten;
	}


	public void setGhostEaten(int ghostEaten) {
		this.ghostEaten = ghostEaten;
	}


	public int getPillsEated() {
		return pillsEated;
	}


	public void setPillsEated(int pillsEated) {
		this.pillsEated = pillsEated;
	}



	

	public int getLifes() {
		return lifes;
	}


	public void setLifes(int lifes) {
		this.lifes = lifes;
	}


	@Override
	public Attribute getIdAttribute() {
		return new Attribute("id", MsPacManDescription.class);
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public double getDistanceInky() {
		return distanceInky;
	}


	public void setDistanceInky(double distanceInky) {
		this.distanceInky = distanceInky;
	}


	public double getDistancePinky() {
		return distancePinky;
	}


	public void setDistancePinky(double distancePinky) {
		this.distancePinky = distancePinky;
	}


	public double getDistanceSue() {
		return distanceSue;
	}


	public void setDistanceSue(double distanceSue) {
		this.distanceSue = distanceSue;
	}


	public double getDistanceBlinky() {
		return distanceBlinky;
	}


	public void setDistanceBlinky(double distanceBlinky) {
		this.distanceBlinky = distanceBlinky;
	}


	public double getTimeEdibleInky() {
		return timeEdibleInky;
	}


	public void setTimeEdibleInky(double timeEdibleInky) {
		this.timeEdibleInky = timeEdibleInky;
	}


	public double getTimeEdiblePinky() {
		return timeEdiblePinky;
	}


	public void setTimeEdiblePinky(double timeEdiblePinky) {
		this.timeEdiblePinky = timeEdiblePinky;
	}


	public double getTimeEdibleSue() {
		return timeEdibleSue;
	}


	public void setTimeEdibleSue(double timeEdibleSue) {
		this.timeEdibleSue = timeEdibleSue;
	}


	public double getTimeEdibleBlinky() {
		return timeEdibleBlinky;
	}


	public void setTimeEdibleBlinky(double timeEdibleBlinky) {
		this.timeEdibleBlinky = timeEdibleBlinky;
	}


	public int isChasedByInky() {
		return chasedByInky;
	}


	public void setChasedByInky(int chasedByInky) {
		this.chasedByInky = chasedByInky;
	}


	public int isChasedByPinky() {
		return chasedByPinky;
	}


	public void setChasedByPinky(int chasedByPinky) {
		this.chasedByPinky = chasedByPinky;
	}


	public int isChasedBySue() {
		return chasedBySue;
	}


	public void setChasedBySue(int chasedBySue) {
		this.chasedBySue = chasedBySue;
	}


	public int isChasedByBlinky() {
		return chasedByBlinky;
	}


	public void setChasedByBlinky(int chasedByBlinky) {
		this.chasedByBlinky = chasedByBlinky;
	}


	public double getDistanceInterUp() {
		return distanceInterUp;
	}


	public void setDistanceInterUp(double distanceInterUp) {
		this.distanceInterUp = distanceInterUp;
	}


	public double getDistanceInterDown() {
		return distanceInterDown;
	}


	public void setDistanceInterDown(double distanceInterDown) {
		this.distanceInterDown = distanceInterDown;
	}


	public double getDistanceInterLeft() {
		return distanceInterLeft;
	}


	public void setDistanceInterLeft(double distanceInterLeft) {
		this.distanceInterLeft = distanceInterLeft;
	}


	public double getDistanceInterRight() {
		return distanceInterRight;
	}


	public void setDistanceInterRight(double distanceInterRight) {
		this.distanceInterRight = distanceInterRight;
	}


	public int getNumPillsUp() {
		return numPillsUp;
	}


	public void setNumPillsUp(int numPillsUp) {
		this.numPillsUp = numPillsUp;
	}


	public int getNumPillsDown() {
		return numPillsDown;
	}


	public void setNumPillsDown(int numPillsDown) {
		this.numPillsDown = numPillsDown;
	}


	public int getNumPillsLeft() {
		return numPillsLeft;
	}


	public void setNumPillsLeft(int numPillsLeft) {
		this.numPillsLeft = numPillsLeft;
	}


	public int getNumPillsRight() {
		return numPillsRight;
	}


	public void setNumPillsRight(int numPillsRight) {
		this.numPillsRight = numPillsRight;
	}


	public int getLastDir() {
		return lastDir;
	}


	public void setLastDir(int lastDir) {
		this.lastDir = lastDir;
	}


	public int getIndexP() {
		return indexP;
	}


	public void setIndexP(int indexP) {
		this.indexP = indexP;
	}


	public int getIndexI() {
		return indexI;
	}


	public void setIndexI(int indexI) {
		this.indexI = indexI;
	}


	public int getIndexS() {
		return indexS;
	}


	public void setIndexS(int indexS) {
		this.indexS = indexS;
	}


	public int getIndexB() {
		return indexB;
	}


	public void setIndexB(int indexB) {
		this.indexB = indexB;
	}


	public int getPacManIndex() {
		return pacManIndex;
	}


	public void setPacManIndex(int pacManIndex) {
		this.pacManIndex = pacManIndex;
	}


	public int getNLevel() {
		return nLevel;
	}


	public void setNLevel(int nLevel) {
		this.nLevel = nLevel;
	}


	@Override
	public String toString() {
		return "MsPacManDescription [id=" + id + ", distanceInky=" + distanceInky + ", distancePinky=" + distancePinky
				+ ", distanceSue=" + distanceSue + ", distanceBlinky=" + distanceBlinky + ", timeEdibleInky="
				+ timeEdibleInky + ", timeEdiblePinky=" + timeEdiblePinky + ", timeEdibleSue=" + timeEdibleSue
				+ ", timeEdibleBlinky=" + timeEdibleBlinky + ", chasedByInky=" + chasedByInky + ", chasedByPinky="
				+ chasedByPinky + ", chasedBySue=" + chasedBySue + ", chasedByBlinky=" + chasedByBlinky
				+ ", distanceInterUp=" + distanceInterUp + ", distanceInterDown=" + distanceInterDown
				+ ", distanceInterLeft=" + distanceInterLeft + ", distanceInterRight=" + distanceInterRight
				+ ", numPillsUp=" + numPillsUp + ", numPillsDown=" + numPillsDown + ", numPillsLeft=" + numPillsLeft
				+ ", numPillsRight=" + numPillsRight + ", lastDir=" + lastDir + ", indexP=" + indexP + ", indexI="
				+ indexI + ", indexS=" + indexS + ", indexB=" + indexB + ", pacManIndex=" + pacManIndex + ", nLevel="
				+ nLevel + "]";
	}

	
	
	

}
