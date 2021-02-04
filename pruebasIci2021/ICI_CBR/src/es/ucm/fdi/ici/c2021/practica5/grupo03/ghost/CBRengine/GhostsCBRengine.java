package es.ucm.fdi.ici.c2021.practica5.grupo03.ghost.CBRengine;

import java.io.File;

import java.util.Random;
import java.util.stream.Collectors;
import java.util.Collection;
import java.util.List;

import es.ucm.fdi.gaia.jcolibri.cbraplications.StandardCBRApplication;
import es.ucm.fdi.gaia.jcolibri.cbrcore.Attribute;
import es.ucm.fdi.gaia.jcolibri.cbrcore.CBRCase;
import es.ucm.fdi.gaia.jcolibri.cbrcore.CBRCaseBase;
import es.ucm.fdi.gaia.jcolibri.cbrcore.CBRQuery;
import es.ucm.fdi.gaia.jcolibri.cbrcore.CaseComponent;
import es.ucm.fdi.gaia.jcolibri.connector.PlainTextConnector;
import es.ucm.fdi.gaia.jcolibri.exception.ExecutionException;
import es.ucm.fdi.gaia.jcolibri.method.retrieve.RetrievalResult;
import es.ucm.fdi.gaia.jcolibri.method.retrieve.NNretrieval.NNConfig;

import es.ucm.fdi.gaia.jcolibri.method.retrieve.NNretrieval.similarity.local.Equal;
import es.ucm.fdi.gaia.jcolibri.method.retrieve.NNretrieval.similarity.local.Interval;
import es.ucm.fdi.gaia.jcolibri.method.retrieve.selection.SelectCases;
import es.ucm.fdi.gaia.jcolibri.util.FileIO;
import es.ucm.fdi.ici.c2021.practica5.grupo03.customSimFunctions.IndexDist;
import es.ucm.fdi.ici.c2021.practica5.grupo03.customSimFunctions.globalEqualFun;
import es.ucm.fdi.ici.c2021.practica5.grupo03.ghost.CBRengine.CachedLinearCaseBase;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class GhostsCBRengine implements StandardCBRApplication {

	private String specificFile;
	private String casebaseFile;
	int nRandom = 0;
	int nTotal = 0;
	MOVE move;
	GHOST ghost;
	Game game;
	IndexDist indD;
	IndexDist indDGhost;
	private GhostsStorageManager storageManager;
	private GhostsStorageManager storageManagerBase;

	private Random rnd = new Random();
	
	CustomPlainTextConnector connectorSpecific;
	CustomPlainTextConnector connectorBase;
	CachedLinearCaseBase caseBase;
	CachedLinearCaseBase specificCases;
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
	
	
	public GhostsCBRengine( GhostsStorageManager storageManager, GhostsStorageManager baseStorageManager)
	{
		this.storageManager = storageManager;
		this.storageManagerBase = baseStorageManager;
	}
	
	public void setCaseBaseFile(String filepath, String opponent) {
		this.specificFile = String.format(filepath, opponent);
		this.casebaseFile = String.format(filepath, "casosBase");
	}
	
	@Override
	public void configure() throws ExecutionException {
		connectorSpecific = new CustomPlainTextConnector();
		connectorBase = new CustomPlainTextConnector();
		
		specificCases = new CachedLinearCaseBase();
		caseBase = new CachedLinearCaseBase();
		
		connectorBase.initFromXMLfile(FileIO.findFile(CONNECTOR_FILE_PATH));
		connectorBase.setCaseBaseFile(this.casebaseFile);
		this.storageManagerBase.setCaseBase(caseBase);
		
		connectorSpecific.initFromXMLfile(FileIO.findFile(CONNECTOR_FILE_PATH));
		connectorSpecific.setCaseBaseFile(this.specificFile);
		this.storageManager.setCaseBase(specificCases);
		
		indD = new IndexDist(100);
		indDGhost = new IndexDist(20);
		
		simConfig = new NNConfig();
		simConfig.setDescriptionSimFunction(new globalEqualFun());
		
		//simConfig.addMapping(new Attribute("distanceToPacManP",GhostsDescription.class), new Interval(300));
		//simConfig.addMapping(new Attribute("distanceToPacManB",GhostsDescription.class), new Interval(300));
		//simConfig.addMapping(new Attribute("distanceToPacManI",GhostsDescription.class), new Interval(300));
		//simConfig.addMapping(new Attribute("distanceToPacManS",GhostsDescription.class), new Interval(300));
		
		simConfig.addMapping(new Attribute("distancePacManToPowerPill",GhostsDescription.class), new Interval(300));
		
		simConfig.addMapping(new Attribute("eatableTime",GhostsDescription.class), new Interval(200));
		
		simConfig.addMapping(new Attribute("nLevel",GhostsDescription.class), new Equal());
		simConfig.addMapping(new Attribute("lastDir",GhostsDescription.class), new Equal());

		simConfig.addMapping(new Attribute("myIndex",GhostsDescription.class), indDGhost);
		//simConfig.addMapping(new Attribute("indexP",GhostsDescription.class), indD);
		//simConfig.addMapping(new Attribute("indexI",GhostsDescription.class), indD);
		//simConfig.addMapping(new Attribute("indexS",GhostsDescription.class), indD);
		//simConfig.addMapping(new Attribute("indexB",GhostsDescription.class), indD);
		
		simConfig.addMapping(new Attribute("indexPacMan",GhostsDescription.class), indD);
		simConfig.addMapping(new Attribute("pacmanLastDir",GhostsDescription.class), new Equal());

		
	}
	
	public void setGame(Game g) {
		game = g;
	}

	@Override
	public CBRCaseBase preCycle() throws ExecutionException {
		specificCases.init(connectorSpecific);
		caseBase.init(connectorBase);
		return specificCases;
	}

	@Override
	public void cycle(CBRQuery query) throws ExecutionException {
		indD.setGame(game);
		indDGhost.setGame(game);
		boolean m = false;
		if(specificCases.getCases().isEmpty() && caseBase.getCases().isEmpty()) {
			this.move = getPosibleRandomMove();
			m = true;
		}
		
		if (!specificCases.getCases().isEmpty() && !m){
			m = casosBase(query, specificCases, 0.6f, 0.7f, 15, 0.45f, 0.55f);			
		}
		if(!caseBase.getCases().isEmpty() && !m) {
			m = casosBase(query, caseBase, 0.5f, 0.6f, 25,  0.5f, 0.5f);
		}
		if(!m)
			this.move = getPosibleRandomMove();
		
		nTotal++;
		CBRCase newCase = createNewCase(query, specificCases, this.storageManager);
		this.storageManager.storeCase(newCase);
		
		newCase = createNewCase(query, caseBase, this.storageManagerBase);
		this.storageManagerBase.storeCase(newCase);
		
	}
	
	private MOVE getPosibleRandomMove() {
		nRandom++;
		MOVE[] m = game.getPossibleMoves(game.getGhostCurrentNodeIndex(ghost), game.getGhostLastMoveMade(ghost));
		//System.out.println("Random: " + ghost.toString());
		if(m.length == 0)
			return game.getGhostLastMoveMade(ghost);
		return m[rnd.nextInt(m.length)];
	}
	
	/**
	 * Dado un CBR se escogen los NN casos mas relevantes y se calcula cual de ellos va a ser el mejor
	 * a partir de la similitud*p1 + resultado*p2. Si la similitud es menor que sim o el resultado es
	 * menor que res, se desecha la posible solucion
	 * @param cbrC CBR
	 * @param sim grado de similitud
	 * @param res peor resultado pasable
	 * @param NN casos parecidos
	 * @param p1 peso de la similitud
	 * @param p2 peso del resultado
	 */
	private boolean casosBase(CBRQuery query, CachedLinearCaseBase cbrC, float sim, float res, int NN, float p1, float p2){
		//Compute NN
		Collection<RetrievalResult> eval = customNN(cbrC.getCases(), query);		
		
		GhostsResult result = null;
		GhostsSolution solution = null;
		double similarity;
		double valor = 0, aux = 0;
		CBRCase bestCase = null;
		RetrievalResult bestRR = null;
		
		Collection<RetrievalResult> nCases = SelectCases.selectTopKRR(eval, NN);
		
		//Buscamos dentro de los NN mas similares, cual es el mejor posible
		for(int i = 0; i < NN; i ++) {
			RetrievalResult first = nCases.iterator().next();
			CBRCase simCase = first.get_case();
			similarity = first.getEval();
			result = (GhostsResult) simCase.getResult();
			if(similarity < sim || result.score < res)
				continue;
	
			solution = (GhostsSolution) simCase.getSolution();
			aux = similarity*p1 + result.score*p1;
			if(valor < aux) {
				valor = aux;
				bestCase = simCase;
				bestRR = first;
			}
			
		}		
		
		if(bestCase == null) {
			//move = getPosibleRandomMove();
			return false;
		}
		else {
			solution = (GhostsSolution) bestCase.getSolution();
			move = MOVE.values()[solution.getMove()];
			nCases.remove(bestRR);
			removeSimilarCases(nCases, bestCase, NN-1, cbrC);
			return true;
		}
			
		//Now compute a solution for the query	
		
	}
	
	private void removeSimilarCases(Collection<RetrievalResult> nCases, CBRCase bestCase, int NN, CachedLinearCaseBase cbrC) {
		double similarity;
		GhostsSolution solution1 = (GhostsSolution) bestCase.getSolution();
		for(int i = 0; i < NN; i ++) {
			RetrievalResult first = nCases.iterator().next();
			CBRCase simCase = first.get_case();
			similarity = first.getEval();
			GhostsSolution solution2 = (GhostsSolution) simCase.getSolution();
			if(similarity >= 0.95 && solution1.getMove().equals(solution2.getMove())) {
				cbrC.forgetCase(simCase);
			}
		}
	}
	
	public Collection<RetrievalResult> customNN(Collection<CBRCase> cases, CBRQuery query) {

		// Parallel stream

		    List<RetrievalResult> res = cases.parallelStream()

		                .map(c -> new RetrievalResult(c, computeSimilarity(query.getDescription(), c.getDescription())))

		                .collect(Collectors.toList());

		    // Sort the result

		        res.sort(RetrievalResult::compareTo);

		        return res;

	}
	
	private Double computeSimilarity(CaseComponent description, CaseComponent description2) {

		GhostsDescription _query = (GhostsDescription)description;

		GhostsDescription _case = (GhostsDescription)description2;

		double simil = 1;
		double maxDist = 250;
		int maxTime = 200;
		
		simil *= MOVE.values()[_query.getLastDir()].opposite().equals(MOVE.values()[_case.getLastDir()]) ? 0.0 : 1.0;
		simil *= MOVE.values()[_query.getPacmanLastDir()].opposite().equals(MOVE.values()[_case.getPacmanLastDir()]) ? 0.0 : 1.0;		
		simil *= _query.getNLevel().equals(_case.getNLevel()) ? 1.0 : 0.0;
		simil *= _query.getMyIndex().equals(_case.getMyIndex())?1:0;
		simil *= _query.getIndexPacMan().equals(_case.getIndexPacMan())?1:0;
		
		if(simil == 0)
			return 0.0;

		//simil *= 1-Math.abs(_query.getDistanceToPacManI()-_case.getDistanceToPacManI())/maxDist;
		
		//simil += 1-Math.abs(_query.getDistanceToPacManP()-_case.getDistanceToPacManP())/maxDist;
		
		//simil += 1-Math.abs(_query.getDistanceToPacManB()-_case.getDistanceToPacManB())/maxDist;
		
		//simil += 1-Math.abs(_query.getDistanceToPacManS()-_case.getDistanceToPacManS())/maxDist;
		
		simil *= 1-Math.abs(_query.getDistancePacManToPowerPill()-_case.getDistancePacManToPowerPill())/maxDist;
		
		simil += 1-Math.abs(_query.getEatableTime()-_case.getEatableTime())/maxTime;
		
		//simil += game.getDistance(_query.getMyIndex(), _case.getMyIndex(), DM.EUCLID)<5?game.getDistance(_query.getMyIndex(), _case.getMyIndex(), DM.EUCLID)/5:0;
		
		//simil += game.getDistance(_query.getIndexP(), _case.getIndexP(), DM.PATH)<20?game.getDistance(_query.getIndexP(), _case.getIndexP(), DM.PATH)/20:0;
		
		//simil += game.getDistance(_query.getIndexI(), _case.getIndexI(), DM.PATH)<20?game.getDistance(_query.getIndexI(), _case.getIndexI(), DM.PATH)/20:0;
		
		//simil += game.getDistance(_query.getIndexS(), _case.getIndexS(), DM.PATH)<20?game.getDistance(_query.getIndexS(), _case.getIndexS(), DM.PATH)/20:0;
		
		//simil += game.getDistance(_query.getIndexB(), _case.getIndexB(), DM.PATH)<20?game.getDistance(_query.getIndexB(), _case.getIndexB(), DM.PATH)/20:0;

		//simil += game.getDistance(_query.getIndexPacMan(), _case.getIndexPacMan(), DM.PATH)<15?game.getDistance(_query.getIndexPacMan(), _case.getIndexPacMan(), DM.PATH)/15:0;

		return simil/2.0;

		}

	/**
	 * Creates a new case using the query as description, 
	 * storing the action into the solution and 
	 * setting the proper id number
	 */
	private CBRCase createNewCase(CBRQuery query, CBRCaseBase c, GhostsStorageManager st) {
		CBRCase newCase = new CBRCase();
		GhostsDescription newDescription = (GhostsDescription) query.getDescription();
		GhostsResult newResult = new GhostsResult();
		GhostsSolution newSolution = new GhostsSolution();
		int newId = c.getCases().size();
		newId+= st.getPendingCases();
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
		this.storageManagerBase.close();
		this.caseBase.close();
		this.specificCases.close();
		
		System.out.println("Total random: " + nRandom/(float)nTotal);
	}

}
