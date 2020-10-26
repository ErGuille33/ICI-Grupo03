package es.ucm.fdi.ici.c2021.practica1.grupo03;

import pacman.Executor;
import pacman.controllers.GhostController;
import pacman.controllers.PacmanController;
import pacman.controllers.HumanController;
import pacman.controllers.KeyBoardInput;

public class ExecutorTest {

	public static void main(String[] args) {
		Executor executor = new Executor.Builder()
				.setTickLimit(4000)
				.setVisual(true)
				.setScaleFactor(3.0)
				.build();
		
		PacmanController pacMan = new MsPacMan();//new MsPacMan();
		GhostController ghosts = new Ghosts();
		
		System.out.println("P1");
		System.out.println(
				executor.runGame(pacMan, ghosts, 50)
		);
	}

}
