package es.ucm.fdi.ici.c2021.practica5.grupo03.msPacMan.CBRengine;

import es.ucm.fdi.gaia.jcolibri.cbrcore.Attribute;

import es.ucm.fdi.gaia.jcolibri.cbrcore.CaseComponent;

public class MsPacManResult implements CaseComponent, Cloneable {

	Integer id;
	Double score;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

	@Override
	public Attribute getIdAttribute() {
		return new Attribute("id", MsPacManResult.class);
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException{  
		return super.clone();  
	}

	@Override
	public String toString() {
		return "MsPacManResult [id=" + id + ", score=" + score + "]";
	} 
	
	

}
