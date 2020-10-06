package es.ucm.fdi.ici.c2021.practica0.grupo03;

import pacman.Executor;
import pacman.controllers.GhostController;
import pacman.controllers.PacmanController;

public class EjecutorTest {
	
	public static void main(String[] args) {
		Executor executor = new Executor.Builder()
				.setTickLimit(4000)
				.setVisual(true)
				.setScaleFactor(3.0)
				.build();
		//PacmanController pacMan = new PacManRandom();
		//PacmanController pacMan = new HumanController(new KeyBoardInput());
		PacmanController pacMan = new MsPacManRunAway();
		//GhostController ghosts = new GhostRandom();
		GhostController ghosts = new GhostAggresive();
		
		System.out.println(
				executor.runGame(pacMan, ghosts, 50)
				);
	}
}