package es.ucm.fdi.ici.c2021.practica5.grupo03.msPacMan.CBRengine;

import es.ucm.fdi.gaia.jcolibri.cbrcore.Attribute;
import es.ucm.fdi.gaia.jcolibri.cbrcore.CaseComponent;

public class MsPacManDescription implements CaseComponent {

	Integer id;

	Double distanceInky;
	Double distancePinky;
	Double distanceSue;
	Double distanceBlinky;

	Integer timeEdibleInky;
	Integer timeEdiblePinky;
	Integer timeEdibleSue;
	Integer timeEdibleBlinky;

	Integer chasedByInky;
	Integer chasedByPinky;
	Integer chasedBySue;
	Integer chasedByBlinky;

	Double distanceInterUp;
	Double distanceInterDown;
	Double distanceInterLeft;
	Double distanceInterRight;

	Integer numPillsUp;
	Integer numPillsDown; // Hasta la siguiente intersección
	Integer numPillsLeft;
	Integer numPillsRight;

	Integer indexP;
	Integer indexI; // Indice del nodo de posicion de cada fantasma
	Integer indexS;
	Integer indexB;

	Integer pacManIndex;

	Integer lifes;
	Integer pillsEated;

	Integer lastDir;
	Integer ghostEaten;
	Integer nLevel;

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

	public Double getDistanceInky() {
		return distanceInky;
	}

	public void setDistanceInky(Double distanceInky) {
		this.distanceInky = distanceInky;
	}

	public Double getDistancePinky() {
		return distancePinky;
	}

	public void setDistancePinky(Double distancePinky) {
		this.distancePinky = distancePinky;
	}

	public Double getDistanceSue() {
		return distanceSue;
	}

	public void setDistanceSue(Double distanceSue) {
		this.distanceSue = distanceSue;
	}

	public Double getDistanceBlinky() {
		return distanceBlinky;
	}

	public void setDistanceBlinky(Double distanceBlinky) {
		this.distanceBlinky = distanceBlinky;
	}

	public Integer getTimeEdibleInky() {
		return timeEdibleInky;
	}

	public void setTimeEdibleInky(Integer timeEdibleInky) {
		this.timeEdibleInky = timeEdibleInky;
	}

	public Integer getTimeEdiblePinky() {
		return timeEdiblePinky;
	}

	public void setTimeEdiblePinky(Integer timeEdiblePinky) {
		this.timeEdiblePinky = timeEdiblePinky;
	}

	public Integer getTimeEdibleSue() {
		return timeEdibleSue;
	}

	public void setTimeEdibleSue(Integer timeEdibleSue) {
		this.timeEdibleSue = timeEdibleSue;
	}

	public Integer getTimeEdibleBlinky() {
		return timeEdibleBlinky;
	}

	public void setTimeEdibleBlinky(Integer timeEdibleBlinky) {
		this.timeEdibleBlinky = timeEdibleBlinky;
	}

	public Integer getChasedByInky() {
		return chasedByInky;
	}

	public void setChasedByInky(Integer chasedByInky) {
		this.chasedByInky = chasedByInky;
	}

	public Integer getChasedByPinky() {
		return chasedByPinky;
	}

	public void setChasedByPinky(Integer chasedByPinky) {
		this.chasedByPinky = chasedByPinky;
	}

	public Integer getChasedBySue() {
		return chasedBySue;
	}

	public void setChasedBySue(Integer chasedBySue) {
		this.chasedBySue = chasedBySue;
	}

	public Integer getChasedByBlinky() {
		return chasedByBlinky;
	}

	public void setChasedByBlinky(Integer chasedByBlinky) {
		this.chasedByBlinky = chasedByBlinky;
	}

	public Double getDistanceInterUp() {
		return distanceInterUp;
	}

	public void setDistanceInterUp(Double distanceInterUp) {
		this.distanceInterUp = distanceInterUp;
	}

	public Double getDistanceInterDown() {
		return distanceInterDown;
	}

	public void setDistanceInterDown(Double distanceInterDown) {
		this.distanceInterDown = distanceInterDown;
	}

	public Double getDistanceInterLeft() {
		return distanceInterLeft;
	}

	public void setDistanceInterLeft(Double distanceInterLeft) {
		this.distanceInterLeft = distanceInterLeft;
	}

	public Double getDistanceInterRight() {
		return distanceInterRight;
	}

	public void setDistanceInterRight(Double distanceInterRight) {
		this.distanceInterRight = distanceInterRight;
	}

	public Integer getNumPillsUp() {
		return numPillsUp;
	}

	public void setNumPillsUp(Integer numPillsUp) {
		this.numPillsUp = numPillsUp;
	}

	public Integer getNumPillsDown() {
		return numPillsDown;
	}

	public void setNumPillsDown(Integer numPillsDown) {
		this.numPillsDown = numPillsDown;
	}

	public Integer getNumPillsLeft() {
		return numPillsLeft;
	}

	public void setNumPillsLeft(Integer numPillsLeft) {
		this.numPillsLeft = numPillsLeft;
	}

	public Integer getNumPillsRight() {
		return numPillsRight;
	}

	public void setNumPillsRight(Integer numPillsRight) {
		this.numPillsRight = numPillsRight;
	}

	public Integer getIndexP() {
		return indexP;
	}

	public void setIndexP(Integer indexP) {
		this.indexP = indexP;
	}

	public Integer getIndexI() {
		return indexI;
	}

	public void setIndexI(Integer indexI) {
		this.indexI = indexI;
	}

	public Integer getIndexS() {
		return indexS;
	}

	public void setIndexS(Integer indexS) {
		this.indexS = indexS;
	}

	public Integer getIndexB() {
		return indexB;
	}

	public void setIndexB(Integer indexB) {
		this.indexB = indexB;
	}

	public Integer getPacManIndex() {
		return pacManIndex;
	}

	public void setPacManIndex(Integer pacManIndex) {
		this.pacManIndex = pacManIndex;
	}

	public Integer getLifes() {
		return lifes;
	}

	public void setLifes(Integer lifes) {
		this.lifes = lifes;
	}

	public Integer getPillsEated() {
		return pillsEated;
	}

	public void setPillsEated(Integer pillsEated) {
		this.pillsEated = pillsEated;
	}

	public Integer getLastDir() {
		return lastDir;
	}

	public void setLastDir(Integer lastDir) {
		this.lastDir = lastDir;
	}

	public Integer getGhostEaten() {
		return ghostEaten;
	}

	public void setGhostEaten(Integer ghostEaten) {
		this.ghostEaten = ghostEaten;
	}

	public Integer getNLevel() {
		return nLevel;
	}

	public void setNLevel(Integer nLevel) {
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
