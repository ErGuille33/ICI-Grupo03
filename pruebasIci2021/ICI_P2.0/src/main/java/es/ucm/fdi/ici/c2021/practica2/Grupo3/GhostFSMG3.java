package es.ucm.fdi.ici.c2021.practica2.Grupo3;

import java.util.EnumMap;

import es.ucm.fdi.ici.c2021.practica2.Grupo3.actions.MiddlePath;
import es.ucm.fdi.ici.c2021.practica2.Grupo3.actions.RunAway;
import es.ucm.fdi.ici.fsm.FSM;
import es.ucm.fdi.ici.fsm.SimpleState;
import es.ucm.fdi.ici.practica2.demofsm.ghosts.GhostsInput;
import es.ucm.fdi.ici.practica2.demofsm.ghosts.actions.ChaseAction;
import es.ucm.fdi.ici.practica2.demofsm.ghosts.actions.RunAwayAction;
import es.ucm.fdi.ici.practica2.demofsm.ghosts.transitions.GhostsEdibleTransition;
import es.ucm.fdi.ici.practica2.demofsm.ghosts.transitions.GhostsNotEdibleAndPacManFarPPill;
import es.ucm.fdi.ici.practica2.demofsm.ghosts.transitions.PacManNearPPillTransition;
import pacman.controllers.GhostController;
import pacman.game.Game;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;

public class GhostFSMG3 extends GhostController {

	EnumMap<GHOST,FSM> fsms;
	
	DM euristic = DM.MANHATTAN;
	public GhostFSMG3()
	{
		fsms = new EnumMap<GHOST,FSM>(GHOST.class);
		for(GHOST ghost: GHOST.values()) {
			FSM fsm = new FSM(ghost.name());
			//fsm.addObserver(new ConsoleFSMObserver(ghost.name()));
			//GraphFSMObserver graphObserver = new GraphFSMObserver(ghost.name());
			//fsm.addObserver(graphObserver);

			
			SimpleState middlePath = new SimpleState("middlePath", new MiddlePath(ghost, euristic));
			SimpleState runAway = new SimpleState("runAway", new RunAway(ghost, euristic));
			
			GhostsEdibleTransition edible = new GhostsEdibleTransition(ghost);
			PacManNearPPillTransition near = new PacManNearPPillTransition();
			GhostsNotEdibleAndPacManFarPPill toChaseTransition = new GhostsNotEdibleAndPacManFarPPill(ghost);
			
			fsm.add(chase, edible, runAway);
			fsm.add(chase, near, runAway);
			fsm.add(runAway, toChaseTransition, chase);
			
			fsm.ready(chase);
			
			//graphObserver.showInFrame();
			
			fsms.put(ghost, fsm);
		}
	}
	
	public void preCompute(String opponent) {
    	for(FSM fsm: fsms.values())
    		fsm.reset();
    }
	
	@Override
	public EnumMap<GHOST, MOVE> getMove(Game game, long timeDue) {
		EnumMap<GHOST,MOVE> result = new EnumMap<GHOST,MOVE>(GHOST.class);
		
		GhostsInput in = new GhostsInput(game);
		
		for(GHOST ghost: GHOST.values())
		{
			FSM fsm = fsms.get(ghost);
			MOVE move = fsm.run(in);
			result.put(ghost, move);
		}
		
		return result;
		
	
		
	}

}