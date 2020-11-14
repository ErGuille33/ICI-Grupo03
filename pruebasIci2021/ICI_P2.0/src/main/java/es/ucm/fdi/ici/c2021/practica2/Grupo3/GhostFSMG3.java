package es.ucm.fdi.ici.c2021.practica2.Grupo3;

import java.awt.BorderLayout;
import java.util.EnumMap;

import javax.swing.JFrame;
import javax.swing.JPanel;

import es.ucm.fdi.ici.c2021.practica2.Grupo3.actions.MiddlePath;
import es.ucm.fdi.ici.c2021.practica2.Grupo3.actions.RunAway;
import es.ucm.fdi.ici.c2021.practica2.Grupo3.transitions.GhostEdibleTransition;
import es.ucm.fdi.ici.c2021.practica2.Grupo3.transitions.GhostNotEdibleTransition;
import es.ucm.fdi.ici.fsm.FSM;
import es.ucm.fdi.ici.fsm.SimpleState;
import es.ucm.fdi.ici.fsm.observers.ConsoleFSMObserver;
import es.ucm.fdi.ici.fsm.observers.GraphFSMObserver;
import es.ucm.fdi.ici.practica2.demofsm.ghosts.GhostsInput;
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
			GraphFSMObserver graphObserver = new GraphFSMObserver(ghost.name());
			fsm.addObserver(graphObserver);

			
			SimpleState middlePath = new SimpleState("middlePath", new MiddlePath(ghost, euristic));
			SimpleState runAway = new SimpleState("runAway", new RunAway(ghost, euristic));
			
			GhostEdibleTransition edible = new GhostEdibleTransition(ghost);
			GhostNotEdibleTransition notEdible = new GhostNotEdibleTransition(ghost);
			
			fsm.add(middlePath, edible, runAway);
			fsm.add(runAway, notEdible, middlePath);
			
			fsm.ready(middlePath);
			
			//graphObserver.showInFrame();
			JFrame frame = new JFrame();
	    	JPanel main = new JPanel();
	    	main.setLayout(new BorderLayout());
	    	main.add(graphObserver.getAsPanel(true, null), BorderLayout.CENTER);
	    	frame.getContentPane().add(main);
	    	frame.pack();
	    	frame.setVisible(true);
			
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