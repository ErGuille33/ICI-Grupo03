package es.ucm.fdi.ici.c2021.practica5.grupo03.ghost.CBRengine;

import java.io.File;

import java.util.Random;
import java.util.Collection;

import es.ucm.fdi.gaia.jcolibri.cbraplications.StandardCBRApplication;
import es.ucm.fdi.gaia.jcolibri.cbrcore.Attribute;
import es.ucm.fdi.gaia.jcolibri.cbrcore.CBRCase;
import es.ucm.fdi.gaia.jcolibri.cbrcore.CBRCaseBase;
import es.ucm.fdi.gaia.jcolibri.cbrcore.CBRQuery;
import es.ucm.fdi.gaia.jcolibri.connector.PlainTextConnector;
import es.ucm.fdi.gaia.jcolibri.exception.ExecutionException;
import es.ucm.fdi.gaia.jcolibri.method.retrieve.RetrievalResult;
import es.ucm.fdi.gaia.jcolibri.method.retrieve.NNretrieval.NNConfig;
import es.ucm.fdi.gaia.jcolibri.method.retrieve.NNretrieval.NNScoringMethod;

import es.ucm.fdi.gaia.jcolibri.method.retrieve.NNretrieval.similarity.local.Equal;
import es.ucm.fdi.gaia.jcolibri.method.retrieve.NNretrieval.similarity.local.Interval;
import es.ucm.fdi.gaia.jcolibri.method.retrieve.selection.SelectCases;
import es.ucm.fdi.gaia.jcolibri.util.FileIO;
import es.ucm.fdi.ici.c2021.practica5.grupo03.customSimFunctions.IndexDist;
import es.ucm.fdi.ici.c2021.practica5.grupo03.customSimFunctions.globalEqualFun;

import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class GhostsCBRengine implements StandardCBRApplication {

	private String casebaseFile;
	MOVE move;
	GHOST ghost;
	Game game;
	IndexDist indD;
	private GhostsStorageManager storageManager; //

	private Random rnd = new Random();
	
	CustomPlainTextConnector connector;
	CBRCaseBase caseBase;
	NNConfig simConfig;
	
	
	
	final static String CONNECTOR_FILE_PATH = "es/ucm/fdi/ici/c2021/practica5/grupo03/ghost/CBRengine/plaintextconfig.xml"; //Cuidado!! poner el grupo aquí

	/**
	 * Simple extension to allow custom case base files. It also creates a new empty file if it does not exist.
	 */
	public class CustomPlainTextConnector extends PlainTextConnector {
		public void setCaseBaseFile(String casebaseFile) {
			super.PROP_FILEPATH = casebaseFile;
			try {
		         File file = new File(casebaseFile);
		         System.out.println(file.getAbsolutePath());
		         if(!file.exists())
		        	 file.createNewFile();
		      } catch(Exception e) {
		         e.printStackTrace();
		      }
		}
	}
	
	public void setGhost(GHOST ghost) {
		this.ghost = ghost;
	}
	
	
	public GhostsCBRengine( GhostsStorageManager storageManager)
	{
		this.storageManager = storageManager;
	}
	
	public void setCaseBaseFile(String casebaseFile) {
		this.casebaseFile = casebaseFile;
	}
	
	@Override
	public void configure() throws ExecutionException {
		connector = new CustomPlainTextConnector();
		caseBase = new CachedLinearCaseBase();
		
		connector.initFromXMLfile(FileIO.findFile(CONNECTOR_FILE_PATH));
		connector.setCaseBaseFile(this.casebaseFile);
		this.storageManager.setCaseBase(caseBase);
		
		indD = new IndexDist(100);
		simConfig = new NNConfig();
		simConfig.setDescriptionSimFunction(new globalEqualFun());
		
		simConfig.addMapping(new Attribute("distanceToPacManP",GhostsDescription.class), new Interval(300));
		simConfig.addMapping(new Attribute("distanceToPacManB",GhostsDescription.class), new Interval(300));
		simConfig.addMapping(new Attribute("distanceToPacManI",GhostsDescription.class), new Interval(300));
		simConfig.addMapping(new Attribute("distanceToPacManS",GhostsDescription.class), new Interval(300));
		
		simConfig.addMapping(new Attribute("distancePacManToPowerPill",GhostsDescription.class), new Interval(300));
		
		simConfig.addMapping(new Attribute("eatableTime",GhostsDescription.class), new Interval(200));
		
		simConfig.addMapping(new Attribute("nLevel",GhostsDescription.class), new Equal());
		simConfig.addMapping(new Attribute("lastDir",GhostsDescription.class), new Equal());

		simConfig.addMapping(new Attribute("indexP",GhostsDescription.class), indD);
		simConfig.addMapping(new Attribute("indexI",GhostsDescription.class), indD);
		simConfig.addMapping(new Attribute("indexS",GhostsDescription.class), indD);
		simConfig.addMapping(new Attribute("indexB",GhostsDescription.class), indD);
		
		simConfig.addMapping(new Attribute("indexPacMan",GhostsDescription.class), indD);	

		
	}
	
	public void setGame(Game g) {
		game = g;
	}

	@Override
	public CBRCaseBase preCycle() throws ExecutionException {
		caseBase.init(connector);
		return caseBase;
	}

	@Override
	public void cycle(CBRQuery query) throws ExecutionException {
		if(caseBase.getCases().isEmpty()) {
			this.move = MOVE.values()[rnd.nextInt(MOVE.values().length)];;
		}else {
			//Seteamos el game para la distancia entre indices
			indD.setGame(game);
			
			//Compute NN
			Collection<RetrievalResult> eval = NNScoringMethod.evaluateSimilarity(caseBase.getCases(), query, simConfig);
			
			// This simple implementation only uses 1NN
			// Consider using kNNs with majority voting
			RetrievalResult first = SelectCases.selectTopKRR(eval, 1).iterator().next();
			CBRCase mostSimilarCase = first.get_case();
			double similarity = first.getEval();
	
			GhostsResult result = (GhostsResult) mostSimilarCase.getResult();
			GhostsSolution solution = (GhostsSolution) mostSimilarCase.getSolution();
			
			//Now compute a solution for the query
			
			move = MOVE.values()[solution.getMove()];
			
			if(similarity<0.7) //Sorry not enough similarity, ask actionSelector for an action
				move = MOVE.values()[rnd.nextInt(MOVE.values().length)];
				
			else if(result.getScore()<0) //This was a bad case, ask actionSelector for another one.
				move = MOVE.values()[rnd.nextInt(MOVE.values().length)];
			
		}
		CBRCase newCase = createNewCase(query);
		this.storageManager.storeCase(newCase);
		
	}

	/**
	 * Creates a new case using the query as description, 
	 * storing the action into the solution and 
	 * setting the proper id number
	 */
	private CBRCase createNewCase(CBRQuery query) {
		CBRCase newCase = new CBRCase();
		GhostsDescription newDescription = (GhostsDescription) query.getDescription();
		GhostsResult newResult = new GhostsResult();
		GhostsSolution newSolution = new GhostsSolution();
		int newId = this.caseBase.getCases().size();
		newId+= storageManager.getPendingCases();
		newDescription.setId(newId);
		newResult.setId(newId);
		newSolution.setId(newId);
		newSolution.setMove(this.move.ordinal());
		newCase.setDescription(newDescription);
		newCase.setResult(newResult);
		newCase.setSolution(newSolution);
		return newCase;
	}
	
	public MOVE getSolution() {
		return this.move;
	}

	@Override
	public void postCycle() throws ExecutionException {
		this.storageManager.close();
		this.caseBase.close();
	}

}
