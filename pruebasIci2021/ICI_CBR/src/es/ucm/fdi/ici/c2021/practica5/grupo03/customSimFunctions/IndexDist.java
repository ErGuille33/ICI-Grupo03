package es.ucm.fdi.ici.c2021.practica5.grupo03.customSimFunctions;

import es.ucm.fdi.gaia.jcolibri.exception.NoApplicableSimilarityFunctionException;
import es.ucm.fdi.gaia.jcolibri.method.retrieve.NNretrieval.similarity.LocalSimilarityFunction;
import pacman.game.Constants.DM;
import pacman.game.Game;

public class IndexDist implements LocalSimilarityFunction{
	
	/** Interval */
	double _interval;
	
	Game _game;

	/**
	 * Constructor.
	 */
	public IndexDist(double interval) {
		this._interval = interval;
	}
	
	public void setGame(Game game) {
		this._game = game;
	}

	/**
	 * Applies the similarity function.
	 * 
	 * @param o1
	 *            Number
	 * @param o2
	 *            Number
	 * @return result of apply the similarity function.
	 */
	public double compute(Object o1, Object o2) throws NoApplicableSimilarityFunctionException {
		if ((o1 == null) || (o2 == null))
			return 0;
		if (!(o1 instanceof Number))
			throw new NoApplicableSimilarityFunctionException(this.getClass(), o1.getClass());
		if (!(o2 instanceof Number))
			throw new NoApplicableSimilarityFunctionException(this.getClass(), o2.getClass());


		Number i1 = (Number) o1;
		Number i2 = (Number) o2;
		
		int v1 = i1.intValue();
		int v2 = i2.intValue();
		
		double dist = _game.getDistance(v1, v2, DM.PATH);
		
		double r = 1 - (dist / _interval);
		if(r < 0)
			r = 0;
		return r;
	}
	
	/** Applicable to Integer */
	public boolean isApplicable(Object o1, Object o2)
	{
		if((o1==null)&&(o2==null))
			return true;
		else if(o1==null)
			return o2 instanceof Number;
		else if(o2==null)
			return o1 instanceof Number;
		else
			return (o1 instanceof Number)&&(o2 instanceof Number);
	}
	
}
