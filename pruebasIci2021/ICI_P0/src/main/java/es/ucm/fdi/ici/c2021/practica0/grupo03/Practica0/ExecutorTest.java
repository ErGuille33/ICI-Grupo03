package es.ucm.fdi.ici.c2021.practica0.grupo03.Practica0;

import pacman.Executor;
import pacman.controllers.GhostController;
import pacman.controllers.HumanController;
import pacman.controllers.KeyBoardInput;
import pacman.controllers.PacmanController;

public class ExecutorTest {
	
	public static void main(String[] args) {
		Executor executor = new Executor.Builder()
				.setTickLimit(4000)
				.setVisual(true)
				.setScaleFactor(3.0)
				.build();
		PacmanController pacMan = new MsPacMan(); //new MsPacManRunAway();//new HumanController(new KeyBoardInput());//new PacManRandom();
		GhostController ghosts = new Ghosts();//new GhostsAggresive();
		
		System.out.println(
				executor.runGame(pacMan, ghosts, 50)
		);
		
	}
}
