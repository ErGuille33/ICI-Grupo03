package es.ucm.fdi.ici.c2021.practica2.grupo03;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import es.ucm.fdi.ici.fsm.FSM;
import es.ucm.fdi.ici.fsm.Input;
import es.ucm.fdi.ici.fsm.SimpleState;
import es.ucm.fdi.ici.fsm.Transition;
import es.ucm.fdi.ici.fsm.observers.GraphFSMObserver;

import pacman.controllers.PacmanController;
import pacman.game.Game;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import es.ucm.fdi.ici.c2021.practica2.grupo03.actions.*;
import es.ucm.fdi.ici.c2021.practica2.grupo03.pacman.*;
import es.ucm.fdi.ici.c2021.practica2.grupo03.transitions.*;

public class MsPacMan extends PacmanController {

	FSM fsm;

	DM euristic = DM.MANHATTAN;

	public MsPacMan() {

		fsm = new FSM("MsPacMan");

		//GraphFSMObserver observer = new GraphFSMObserver(fsm.toString());
		//fsm.addObserver(observer);

		SimpleState chasePillState = new SimpleState("Follow Pill", new MsPacManChasePill());
		SimpleState chaseGhostState = new SimpleState("Chase Ghost", new MsPacManChaseGhost());

		PacmanChaseGhostTransition tran1 = new PacmanChaseGhostTransition();
		PacmanChasePillTransition tran2 = new PacmanChasePillTransition();

		fsm.add(chasePillState, tran1, chaseGhostState);
		fsm.add(chaseGhostState, tran2, chasePillState);

		fsm.ready(chasePillState);

		/*JFrame frame = new JFrame();
		JPanel main = new JPanel();
		main.setLayout(new BorderLayout());
		main.add(observer.getAsPanel(true, null), BorderLayout.CENTER);
		frame.getContentPane().add(main);
		frame.pack();
		frame.setVisible(true);*/
	}

	public void preCompute(String opponent) {
		fsm.reset();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pacman.controllers.Controller#getMove(pacman.game.Game, long)
	 */
	@Override
	public MOVE getMove(Game game, long timeDue) {
		Input in = new MsPacManInput(game);

		return fsm.run(in);
	}

}
