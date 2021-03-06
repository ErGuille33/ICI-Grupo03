package es.ucm.fdi.ici.c2021.practica5.grupo03.msPacMan.CBRengine;

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
import pacman.game.Constants.DM;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class MsPacManCBRengine implements StandardCBRApplication {

	private String specificFile;
	private String casebaseFile;
	
	
	MOVE move;
	Game game;
	IndexDist indD;
	private MsPacManStorageManager storageManager;
	private MsPacManStorageManager storageManagerBase;

	private Random rnd = new Random();
	
	CustomPlainTextConnector connectorSpecific;
	CustomPlainTextConnector connectorBase;
	CBRCaseBase specificCases;
	CBRCaseBase caseBase;
	NNConfig simConfig;
	
	
	
	final static String CONNECTOR_FILE_PATH = "es/ucm/fdi/ici/c2021/practica5/grupo03/msPacMan/CBRengine/plaintextconfig.xml"; //Cuidado!! poner el grupo aquí

	/**
	 * Simple extension to allow custom case base files. It also creates a new empty file if it does not exist.
	 */
	public class CustomPlainTextConnector extends PlainTextConnector {
		public void setCaseBaseFile(String specificFile) {
			super.PROP_FILEPATH = specificFile;
			try {
		         File file = new File(specificFile);
		         System.out.println(file.getAbsolutePath());
		         if(!file.exists())
		        	 file.createNewFile();
		      } catch(Exception e) {
		         e.printStackTrace();
		      }
		}
	}
	
	
	public MsPacManCBRengine( MsPacManStorageManager storageManager,  MsPacManStorageManager baseStorageManager)
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
		
		indD = new IndexDist(30);
		simConfig = new NNConfig();
		simConfig.setDescriptionSimFunction(new globalEqualFun());
		
		simConfig.addMapping(new Attribute("lastDir",MsPacManDescription.class), new Equal());
		simConfig.addMapping(new Attribute("nLevel",MsPacManDescription.class), new Equal());
		
		simConfig.addMapping(new Attribute("distanceInky",MsPacManDescription.class), new Interval(300));
		simConfig.addMapping(new Attribute("distancePinky",MsPacManDescription.class), new Interval(300));
		simConfig.addMapping(new Attribute("distanceSue",MsPacManDescription.class), new Interval(300));
		simConfig.addMapping(new Attribute("distanceBlinky",MsPacManDescription.class), new Interval(300));
		
		simConfig.addMapping(new Attribute("timeEdibleInky",MsPacManDescription.class), new Interval(200));
		simConfig.addMapping(new Attribute("timeEdiblePinky",MsPacManDescription.class), new Interval(200));
		simConfig.addMapping(new Attribute("timeEdibleSue",MsPacManDescription.class), new Interval(200));
		simConfig.addMapping(new Attribute("timeEdibleBlinky",MsPacManDescription.class), new Interval(200));
		
		simConfig.addMapping(new Attribute("chasedByInky",MsPacManDescription.class), new Interval(4));
		simConfig.addMapping(new Attribute("chasedByPinky",MsPacManDescription.class), new Interval(4));
		simConfig.addMapping(new Attribute("chasedBySue",MsPacManDescription.class), new Interval(4));
		simConfig.addMapping(new Attribute("chasedByBlinky",MsPacManDescription.class), new Interval(4));
		
		simConfig.addMapping(new Attribute("distanceInterUp",MsPacManDescription.class), new Interval(300));
		simConfig.addMapping(new Attribute("distanceInterDown",MsPacManDescription.class), new Interval(300));
		simConfig.addMapping(new Attribute("distanceInterLeft",MsPacManDescription.class), new Interval(300));
		simConfig.addMapping(new Attribute("distanceInterRight",MsPacManDescription.class), new Interval(300));
		
		simConfig.addMapping(new Attribute("numPillsUp",MsPacManDescription.class), new Interval(50));
		simConfig.addMapping(new Attribute("numPillsDown",MsPacManDescription.class), new Interval(50));
		simConfig.addMapping(new Attribute("numPillsLeft",MsPacManDescription.class), new Interval(50));
		simConfig.addMapping(new Attribute("numPillsRight",MsPacManDescription.class), new Interval(50));
		
		simConfig.addMapping(new Attribute("indexP",MsPacManDescription.class), indD);
		simConfig.addMapping(new Attribute("indexI",MsPacManDescription.class), indD);
		simConfig.addMapping(new Attribute("indexS",MsPacManDescription.class), indD);
		simConfig.addMapping(new Attribute("indexB",MsPacManDescription.class), indD);
		
		simConfig.addMapping(new Attribute("pacManIndex",MsPacManDescription.class), indD);	

		
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
		boolean m = false;
        if(specificCases.getCases().isEmpty() && caseBase.getCases().isEmpty()) {
            this.move = getPosibleRandomMove();
            m = true;
        }
        
        if (!specificCases.getCases().isEmpty() && !m){
            m = casosBase(query, specificCases, 0.7f, 0.6f, 5, 0.4f, 0.6f);            
        }
        if(!caseBase.getCases().isEmpty() && !m) {
            m = casosBase(query, caseBase, 0.6f, 0.6f, 10,  0.4f, 0.6f);
        }
        if(!m)
            this.move = getPosibleRandomMove();
		CBRCase newCase = createNewCase(query, specificCases, this.storageManager);
		this.storageManager.storeCase(newCase);
		
		newCase = createNewCase(query, caseBase, this.storageManagerBase);
		this.storageManagerBase.storeCase(newCase);
		
	}
	
	private MOVE getPosibleRandomMove() {
		MOVE[] m = game.getPossibleMoves(game.getPacmanCurrentNodeIndex(), game.getPacmanLastMoveMade());
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
	private boolean casosBase(CBRQuery query, CBRCaseBase cbrC, float sim, float res, int NN, float p1, float p2){
		//Compute NN
		Collection<RetrievalResult> eval = customNN(cbrC.getCases(), query);		
		
		MsPacManResult result = null;
		MsPacManSolution solution = null;
		double similarity;
		double valor = 0, aux = 0;
		CBRCase bestCase = null;
		
		Collection<RetrievalResult> nCases = SelectCases.selectTopKRR(eval, NN);
		
		//Buscamos dentro de los NN mas similares, cual es el mejor posible
		for(int i = 0; i < NN; i ++) {
			RetrievalResult first = nCases.iterator().next();
			CBRCase simCase = first.get_case();
			similarity = first.getEval();
			result = (MsPacManResult) simCase.getResult();
			if(similarity < sim || result.score < res)
				continue;
	
			solution = (MsPacManSolution) simCase.getSolution();
			aux = similarity*p1 + result.score*p1;
			if(valor < aux) {
				valor = aux;
				bestCase = simCase;
			}
			
		}		
		
		if(bestCase == null) {
			move = getPosibleRandomMove();
			return false;
		}
		else {
			solution = (MsPacManSolution) bestCase.getSolution();
			move = MOVE.values()[solution.getMove()];
			return true;
		}
			
		//Now compute a solution for the query	
		
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

		MsPacManDescription _query = (MsPacManDescription)description;

		MsPacManDescription _case = (MsPacManDescription)description2;

		double simil = 1;
		double maxDist = 300;
		int maxTime = 200;
		
		
		simil *= MOVE.values()[_query.getLastDir()].opposite().equals(MOVE.values()[_case.getLastDir()]) ? 0.0 : 1.0;
		
		simil *= _query.getNLevel().equals(_case.getNLevel()) ? 1.0 : 0.0;
		
		if(simil == 0)
			return 0.0;

		simil *= 1 - Math.abs(_query.getDistanceInky()-_case.getDistanceInky())/maxDist;
		
		simil += 1 -Math.abs(_query.getDistancePinky()-_case.getDistancePinky())/maxDist;
		
		simil += 1 -Math.abs(_query.getDistanceBlinky()-_case.getDistanceBlinky())/maxDist;
		
		simil += 1 -Math.abs(_query.getDistanceSue()-_case.getDistanceSue())/maxDist;
		
		simil += Math.abs(_query.getDistanceInterUp()-_case.getDistanceInterUp())/50;
		
		simil += 1 -Math.abs(_query.getDistanceInterDown()-_case.getDistanceInterDown())/50;
		
		simil += 1 -Math.abs(_query.getDistanceInterLeft()-_case.getDistanceInterLeft())/50;
		
		simil += 1 -Math.abs(_query.getDistanceInterRight()-_case.getDistanceInterRight())/50;
		
		simil += 1 -Math.abs(_query.getTimeEdibleInky()-_case.getTimeEdibleInky())/maxTime;
		
		simil += 1 -Math.abs(_query.getTimeEdiblePinky()-_case.getTimeEdiblePinky())/maxTime;
		
		simil += 1 -Math.abs(_query.getTimeEdibleBlinky()-_case.getTimeEdibleBlinky())/maxTime;
		
		simil += 1 -Math.abs(_query.getTimeEdibleSue()-_case.getTimeEdibleSue())/maxTime;
		
		simil += 1 - Math.abs(_query.getChasedByInky()-_case.getChasedByInky());
		
		simil += 1 -Math.abs(_query.getChasedByPinky()-_case.getChasedByPinky());
		
		simil += 1 -Math.abs(_query.getChasedByBlinky()-_case.getChasedByBlinky());
		
		simil += 1 -Math.abs(_query.getChasedBySue()-_case.getChasedBySue());
		
		simil += Math.abs(_query.getNumPillsUp()-_case.getNumPillsUp())/10;
		
		simil += Math.abs(_query.getNumPillsDown()-_case.getNumPillsDown())/10;
		
		simil += Math.abs(_query.getNumPillsLeft()-_case.getNumPillsLeft())/10;
		
		simil += Math.abs(_query.getNumPillsRight()-_case.getNumPillsRight())/10;
		
		simil += game.getDistance(_query.getIndexP(), _case.getIndexP(), DM.PATH)<30?game.getDistance(_query.getIndexP(), _case.getIndexP(), DM.PATH)/30:0;
		
		simil += game.getDistance(_query.getIndexI(), _case.getIndexI(), DM.PATH)<30?game.getDistance(_query.getIndexI(), _case.getIndexI(), DM.PATH)/30:0;
		
		simil += game.getDistance(_query.getIndexS(), _case.getIndexS(), DM.PATH)<30?game.getDistance(_query.getIndexS(), _case.getIndexS(), DM.PATH)/30:0;
		
		simil += game.getDistance(_query.getIndexB(), _case.getIndexB(), DM.PATH)<30 ? game.getDistance(_query.getIndexB(), _case.getIndexB(), DM.PATH)/30:0;

		simil += game.getDistance(_query.getPacManIndex(), _case.getPacManIndex(), DM.PATH)<30?game.getDistance(_query.getPacManIndex(), _case.getPacManIndex(), DM.PATH)/30:0;
		
		_query.toString();

		return simil/25.0;

		}

	/**
	 * Creates a new case using the query as description, 
	 * storing the action into the solution and 
	 * setting the proper id number
	 */
	private CBRCase createNewCase(CBRQuery query, CBRCaseBase c, MsPacManStorageManager st) {
		CBRCase newCase = new CBRCase();
		MsPacManDescription newDescription = (MsPacManDescription) query.getDescription();
		MsPacManResult newResult = new MsPacManResult();
		MsPacManSolution newSolution = new MsPacManSolution();
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
		this.specificCases.close();
		this.caseBase.close();
	}

}
