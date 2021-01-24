package es.ucm.fdi.ici.c2021.practica5.grupo03.customSimFunctions;

import es.ucm.fdi.gaia.jcolibri.method.retrieve.NNretrieval.similarity.StandardGlobalSimilarityFunction;

public class globalEqualFun  extends StandardGlobalSimilarityFunction{
	
	public double computeSimilarity(double[] values, double[] weigths, int ivalue)
	{
		double acum = 0;
		double weigthsAcum = 0;
		double valoresGlobales;
		
		valoresGlobales = values[0];
		valoresGlobales *= (1 - values[1]);
		
		for(int i=2; i<ivalue; i++)
		{
			acum += values[i] * weigths[i];
			weigthsAcum += weigths[i];
		}
		return valoresGlobales * (acum/weigthsAcum);
	}

}
