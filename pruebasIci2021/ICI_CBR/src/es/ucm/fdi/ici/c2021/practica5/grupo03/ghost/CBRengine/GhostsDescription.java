package es.ucm.fdi.ici.c2021.practica5.grupo03.ghost.CBRengine;

import es.ucm.fdi.gaia.jcolibri.cbrcore.Attribute;
import es.ucm.fdi.gaia.jcolibri.cbrcore.CaseComponent;

public class GhostsDescription implements CaseComponent {

	Integer id;

	Double distanceToPacManP;
	Double distanceToPacManB;
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
	

	public Integer getActivePowerPills() {
		return activePowerPills;
	}

	public void setActivePowerPills(Integer activePowerPills) {
		this.activePowerPills = activePowerPills;
	}

	public Integer getLifes() {
		return lifes;
	}

	public void setLifes(Integer lifes) {
		this.lifes = lifes;
	}

	public double getDistancePacManToPowerPill() {
		return distancePacManToPowerPill;
	}

	public void setDistancePacManToPowerPill(double distancePacManToPowerPill) {
		this.distancePacManToPowerPill = distancePacManToPowerPill;
	}

	public Integer getEatableTime() {
		return eatableTime;
	}

	public void setEatableTime(Integer eatableTime) {
		this.eatableTime = eatableTime;
	}

	public Integer getIndexP() {
		return indexP;
	}

	public void setIndexP(Integer indexP) {
		this.indexP = indexP;
	}

	public Integer getIndexB() {
		return indexB;
	}

	public void setIndexB(Integer indexB) {
		this.indexB = indexB;
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

	public Integer getIndexPacMan() {
		return indexPacMan;
	}

	public void setIndexPacMan(Integer indexPacMan) {
		this.indexPacMan = indexPacMan;
	}

	public Integer getNLevel() {
		return nLevel;
	}

	public void setNLevel(Integer nLevel) {
		this.nLevel = nLevel;
	}

	public Integer getLastDir() {
		return lastDir;
	}

	public void setLastDir(Integer lastDir) {
		this.lastDir = lastDir;
	}

	@Override
	public Attribute getIdAttribute() {
		return new Attribute("id", GhostsDescription.class);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public double getDistanceToPacManP() {
		return distanceToPacManP;
	}

	public void setDistanceToPacManP(double distanceToPacManP) {
		this.distanceToPacManP = distanceToPacManP;
	}

	public double getDistanceToPacManB() {
		return distanceToPacManB;
	}

	public void setDistanceToPacManB(double distanceToPacManB) {
		this.distanceToPacManB = distanceToPacManB;
	}

	public double getDistanceToPacManI() {
		return distanceToPacManI;
	}

	public void setDistanceToPacManI(double distanceToPacManI) {
		this.distanceToPacManI = distanceToPacManI;
	}

	public double getDistanceToPacManS() {
		return distanceToPacManS;
	}

	public void setDistanceToPacManS(double distanceToPacManS) {
		this.distanceToPacManS = distanceToPacManS;
	}

	@Override
	public String toString() {
		return "GhostsDescription [id=" + id + ", distanceToPacManP=" + distanceToPacManP + ", distanceToPacManB="
				+ distanceToPacManB + ", distanceToPacManI=" + distanceToPacManI + ", distanceToPacManS="
				+ distanceToPacManS + ", distancePacManToPowerPill=" + distancePacManToPowerPill + ", eatableTime="
				+ eatableTime + ", indexP=" + indexP + ", indexB=" + indexB + ", indexI=" + indexI + ", indexS="
				+ indexS + ", indexPacMan=" + indexPacMan + ", nLevel=" + nLevel + ", lastDir=" + lastDir + ", lifes="
				+ lifes + ", activePowerPills=" + activePowerPills + "]";
	}



}
