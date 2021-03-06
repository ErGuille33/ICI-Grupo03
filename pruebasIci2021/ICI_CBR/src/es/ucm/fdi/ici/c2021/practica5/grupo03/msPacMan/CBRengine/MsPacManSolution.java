package es.ucm.fdi.ici.c2021.practica5.grupo03.msPacMan.CBRengine;

import es.ucm.fdi.gaia.jcolibri.cbrcore.Attribute;
import es.ucm.fdi.gaia.jcolibri.cbrcore.CaseComponent;

public class MsPacManSolution implements CaseComponent, Cloneable {
	Integer id;
	Integer move;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getMove() {
		return move;
	}
	public void setMove(Integer move) {
		this.move = move;
	}
	@Override
	public Attribute getIdAttribute() {
		return new Attribute("id", MsPacManSolution.class);
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException{  
		return super.clone();  
	}
	@Override
	public String toString() {
		return "MsPacManSolution [id=" + id + ", action=" + move + "]";
	}  
	
	
	
}
